package duy.com.learnspringboot.service;

import duy.com.learnspringboot.dto.request.authentication.AuthenticationRequest;

public interface IAuthenticationService {
    boolean authenticate(AuthenticationRequest authenticationRequest);
}
