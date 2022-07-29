package com.disney.auth.service;

import com.disney.auth.dto.AuthenticationRequest;
import com.disney.auth.dto.AuthenticationResponse;
import com.disney.auth.dto.UserDTO;
import com.disney.auth.entity.UserEntity;
import com.disney.auth.repository.UserRepository;
import com.disney.mapper.UserMapper;
import com.disney.service.impl.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.mail.MessagingException;
import java.util.Collections;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Username or password not fount");
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
    }

    public boolean save(UserDTO userDTO) throws MessagingException {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        if (userEntity != null) {
            emailService.sendMail(userMapper.map(userEntity.getUsername()));
        }
        userRepository.save(userEntity);
        return userEntity != null;
    }

    public AuthenticationResponse login(AuthenticationRequest request) throws Exception {
        UserDetails userDetails;
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        userDetails = (UserDetails) auth.getPrincipal();
        return new AuthenticationResponse(jwtUtils.generateToken(userDetails));
    }

}
