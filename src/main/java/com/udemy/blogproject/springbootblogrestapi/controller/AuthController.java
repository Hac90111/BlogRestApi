package com.udemy.blogproject.springbootblogrestapi.controller;

import com.udemy.blogproject.springbootblogrestapi.entity.Role;
import com.udemy.blogproject.springbootblogrestapi.entity.User;
import com.udemy.blogproject.springbootblogrestapi.payload.JwtAuthResponseDto;
import com.udemy.blogproject.springbootblogrestapi.payload.LoginDto;
import com.udemy.blogproject.springbootblogrestapi.payload.SignupDto;
import com.udemy.blogproject.springbootblogrestapi.repository.RoleRepository;
import com.udemy.blogproject.springbootblogrestapi.repository.UserRepository;
import com.udemy.blogproject.springbootblogrestapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenProvider tokenProvider;


    //signin api
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponseDto> authenticateUser(@RequestBody LoginDto loginDto){
      Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token from tokenProvider Class
        String token= tokenProvider.generateToken(authentication);
        return  ResponseEntity.ok(new JwtAuthResponseDto(token));
    }

    //signup api

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto){
        // add check for username exists in DB
        if (userRepository.existsByUserName(signupDto.getUsername())){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if (userRepository.existsByEmail(signupDto.getEmail())){
            return new ResponseEntity<>("Email is already used, try signing in", HttpStatus.BAD_REQUEST);
        }

        // create new User
        User user= new User();
        user.setName(signupDto.getName());
        user.setUserName(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setPassword(encoder.encode(signupDto.getPassword()));
        Role roles= roleRepository.findByRoleName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User registration completed successfully!", HttpStatus.CREATED);
    }
}
