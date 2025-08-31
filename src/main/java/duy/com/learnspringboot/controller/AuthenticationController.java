package duy.com.learnspringboot.controller;

import com.nimbusds.jose.JOSEException;
import duy.com.learnspringboot.dto.request.authentication.AuthenticationRequest;
import duy.com.learnspringboot.dto.request.authentication.LogoutRequest;
import duy.com.learnspringboot.dto.request.authentication.IntrospectTokenRequest;
import duy.com.learnspringboot.dto.request.authentication.RefreshTokenRequest;
import duy.com.learnspringboot.dto.response.ApiResponse;
import duy.com.learnspringboot.dto.response.authentication.AuthenticationResponse;
import duy.com.learnspringboot.dto.response.authentication.IntrospectResponse;
import duy.com.learnspringboot.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        AuthenticationResponse result = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .data(result)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody @Valid IntrospectTokenRequest introspectTokenRequest) throws ParseException, JOSEException {
        IntrospectResponse response = authenticationService.introspectToken(introspectTokenRequest);

        return ApiResponse.<IntrospectResponse>builder()
                .code(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody @Valid LogoutRequest logoutRequest) throws ParseException, JOSEException {
        authenticationService.logout(logoutRequest);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Successfully logged out")
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        var response = authenticationService.refreshToken(refreshTokenRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .data(response)
                .build();
    }
}
