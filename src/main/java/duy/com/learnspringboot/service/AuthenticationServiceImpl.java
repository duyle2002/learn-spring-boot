package duy.com.learnspringboot.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import duy.com.learnspringboot.constant.TimeConstant;
import duy.com.learnspringboot.dto.request.authentication.AuthenticationRequest;
import duy.com.learnspringboot.dto.request.authentication.VerifyTokenRequest;
import duy.com.learnspringboot.dto.response.authentication.AuthenticationResponse;
import duy.com.learnspringboot.dto.response.authentication.VerifyTokenResponse;
import duy.com.learnspringboot.entity.User;
import duy.com.learnspringboot.exception.ErrorCode;
import duy.com.learnspringboot.exception.InvalidCredentialsException;
import duy.com.learnspringboot.exception.ResourceNotFoundException;
import duy.com.learnspringboot.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImpl implements IAuthenticationService {
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        User user = userRepository.findByUsername(authenticationRequest.getUsername().toLowerCase())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.USER_NOT_FOUND));

        String userPassword = user.getPassword();

        boolean isAuthenticated = passwordEncoder.matches(authenticationRequest.getPassword(), userPassword);

        if (!isAuthenticated) {
            throw new InvalidCredentialsException(ErrorCode.UNAUTHORIZED);
        }

        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .isAuthenticated(true)
                .accessToken(token)
                .build();
    }

    @Override
    public VerifyTokenResponse verifyToken(VerifyTokenRequest verifyTokenRequest) throws JOSEException, ParseException {
        String token = verifyTokenRequest.getAccessToken();

        JWSVerifier verifier = new MACVerifier(jwtSecret.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return VerifyTokenResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        Instant now = Instant.now();
        Instant expirationTime = now.plus(TimeConstant.ONE_HOUR);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer("learnspringboot")
                .subject(user.getUsername())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expirationTime))
                .claim("scope", buildScope(user))
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
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
