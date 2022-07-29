package com.disney.auth.controller;

import com.disney.auth.dto.AuthenticationRequest;
import com.disney.auth.dto.AuthenticationResponse;
import com.disney.auth.dto.UserDTO;
import com.disney.auth.service.JwtUtils;
import com.disney.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    private UserDetailsCustomService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> singUp(@RequestBody UserDTO userRequest) throws Exception {
        userDetailsService.save(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> singIn(@RequestBody AuthenticationRequest authRequest) throws Exception {
        return ResponseEntity.ok(userDetailsService.login(authRequest));
    }
}

