package duy.com.learnspringboot.controller;

import com.nimbusds.jose.JOSEException;
import duy.com.learnspringboot.dto.request.authentication.AuthenticationRequest;
import duy.com.learnspringboot.dto.request.authentication.VerifyTokenRequest;
import duy.com.learnspringboot.dto.response.ApiResponse;
import duy.com.learnspringboot.dto.response.authentication.AuthenticationResponse;
import duy.com.learnspringboot.dto.response.authentication.VerifyTokenResponse;
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

    @PostMapping("/verify-token")
    public ApiResponse<VerifyTokenResponse> verifyToken(@RequestBody @Valid VerifyTokenRequest verifyTokenRequest) throws ParseException, JOSEException {
        VerifyTokenResponse response = authenticationService.verifyToken(verifyTokenRequest);

        return ApiResponse.<VerifyTokenResponse>builder()
                .code(HttpStatus.OK.value())
                .data(response)
                .build();
    }

}
