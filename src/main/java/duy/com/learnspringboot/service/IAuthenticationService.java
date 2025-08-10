package duy.com.learnspringboot.service;

import com.nimbusds.jose.JOSEException;
import duy.com.learnspringboot.dto.request.authentication.AuthenticationRequest;
import duy.com.learnspringboot.dto.request.authentication.VerifyTokenRequest;
import duy.com.learnspringboot.dto.response.authentication.AuthenticationResponse;
import duy.com.learnspringboot.dto.response.authentication.VerifyTokenResponse;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    VerifyTokenResponse verifyToken(VerifyTokenRequest verifyTokenRequest) throws JOSEException, ParseException;
}
