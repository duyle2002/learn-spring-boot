package duy.com.learnspringboot.service.impl;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import duy.com.learnspringboot.dto.request.authentication.AuthenticationRequest;
import duy.com.learnspringboot.dto.request.authentication.IntrospectTokenRequest;
import duy.com.learnspringboot.dto.request.authentication.LogoutRequest;
import duy.com.learnspringboot.dto.request.authentication.RefreshTokenRequest;
import duy.com.learnspringboot.dto.response.authentication.AuthenticationResponse;
import duy.com.learnspringboot.dto.response.authentication.IntrospectResponse;
import duy.com.learnspringboot.entity.InvalidatedToken;
import duy.com.learnspringboot.entity.RefreshToken;
import duy.com.learnspringboot.entity.User;
import duy.com.learnspringboot.exception.BadRequestException;
import duy.com.learnspringboot.exception.ErrorCode;
import duy.com.learnspringboot.exception.InvalidCredentialsException;
import duy.com.learnspringboot.exception.ResourceNotFoundException;
import duy.com.learnspringboot.repository.InvalidatedTokenRepository;
import duy.com.learnspringboot.repository.UserRepository;
import duy.com.learnspringboot.service.IAuthenticationService;
import duy.com.learnspringboot.service.IRefreshTokenService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final UserRepository userRepository;

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final IRefreshTokenService refreshTokenService;

    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = userRepository
                .findByUsername(authenticationRequest.getUsername().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));

        String userPassword = user.getPassword();

        boolean isAuthenticated = passwordEncoder.matches(authenticationRequest.getPassword(), userPassword);

        if (!isAuthenticated) {
            throw new InvalidCredentialsException(ErrorCode.UNAUTHORIZED);
        }

        String token = generateToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    public IntrospectResponse introspectToken(IntrospectTokenRequest introspectTokenRequest)
            throws JOSEException, ParseException {
        String token = introspectTokenRequest.getAccessToken();

        var isValid = true;
        try {
            verifyToken(token);
        } catch (JOSEException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    @Override
    public void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException {
        String token = logoutRequest.getToken();
        SignedJWT signedJWT = verifyToken(token);
        String jwtTokenId = signedJWT.getJWTClaimsSet().getJWTID();

        InvalidatedToken invalidatedToken = new InvalidatedToken();
        invalidatedToken.setId(UUID.fromString(jwtTokenId));
        invalidatedToken.setExpiryDate(
                signedJWT.getJWTClaimsSet().getExpirationTime().toInstant());

        invalidatedTokenRepository.save(invalidatedToken);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService
                .findActiveRefreshTokenByToken(refreshTokenRequest.getRefreshToken())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (refreshTokenService.isTokenValid(refreshToken)) {
            throw new BadRequestException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        User user = refreshToken.getUser();

        String accessToken = generateToken(user);

        refreshTokenService.revokeRefreshToken(refreshToken.getToken());

        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken.getToken())
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        Instant now = Instant.now();
        Instant expirationTime = now.plusSeconds(accessTokenExpiration);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("learnspringboot")
                .subject(user.getUsername())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expirationTime))
                .claim("scope", buildScope(user))
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(jwtSecret));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        if (CollectionUtils.isEmpty(user.getRoles())) {
            return "";
        }
        return user.getRoles().stream()
                .flatMap(role -> {
                    StringJoiner scopes = new StringJoiner(" ");
                    scopes.add("ROLE_" + role.getName());
                    role.getPermissions().forEach(permission -> scopes.add(permission.getName()));

                    return scopes.toString().lines();
                })
                .collect(Collectors.joining(" "));
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(jwtSecret.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!verified || expiryTime.before(new Date())) {
            throw new InvalidCredentialsException(ErrorCode.UNAUTHORIZED);
        }

        String jwtIDString = signedJWT.getJWTClaimsSet().getJWTID();
        boolean isInvalidatedToken = invalidatedTokenRepository.existsById(UUID.fromString(jwtIDString));
        if (isInvalidatedToken) {
            throw new InvalidCredentialsException(ErrorCode.UNAUTHORIZED);
        }

        return signedJWT;
    }
}
