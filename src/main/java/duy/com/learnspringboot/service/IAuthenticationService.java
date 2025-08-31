package duy.com.learnspringboot.service;

import com.nimbusds.jose.JOSEException;
import duy.com.learnspringboot.dto.request.authentication.AuthenticationRequest;
import duy.com.learnspringboot.dto.request.authentication.LogoutRequest;
import duy.com.learnspringboot.dto.request.authentication.IntrospectTokenRequest;
import duy.com.learnspringboot.dto.request.authentication.RefreshTokenRequest;
import duy.com.learnspringboot.dto.response.authentication.AuthenticationResponse;
import duy.com.learnspringboot.dto.response.authentication.IntrospectResponse;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    IntrospectResponse introspectToken(IntrospectTokenRequest introspectTokenRequest) throws JOSEException, ParseException;
    void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
