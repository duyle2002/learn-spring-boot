package duy.com.learnspringboot.configuration;

import com.nimbusds.jose.JOSEException;
import duy.com.learnspringboot.dto.request.authentication.IntrospectTokenRequest;
import duy.com.learnspringboot.service.IAuthenticationService;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomJwtDecoder implements JwtDecoder {
    IAuthenticationService authenticationService;

    @Value("${jwt.secretKey}")
    @NonFinal
    String jwtSecret;

    static final String ALGORITHM = "HmacSHA256";
    static final MacAlgorithm MAC_ALGORITHM = MacAlgorithm.HS512;

    @NonFinal
    volatile NimbusJwtDecoder nimbusJwtDecoder;

    @PostConstruct
    void initializeDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(jwtSecret.getBytes(), ALGORITHM);
        this.nimbusJwtDecoder = NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MAC_ALGORITHM)
                .build();
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var response = authenticationService.introspectToken(new IntrospectTokenRequest(token));

            if (!response.isValid()) {
                throw new JwtException("Invalid token");
            }

            return nimbusJwtDecoder.decode(token);
        } catch (JOSEException | ParseException e) {
            throw new JwtException("Token validation failed: " + e.getMessage());
        }
    }
}
