package com.example.demo.service;

import com.example.demo.dto.ResponseVO;
import com.example.demo.entity.Login;
import com.example.demo.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseVO<Login> createLogin(Login login) {
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        Login user = loginRepository.save(login);
        return ResponseVO.<Login>builder()
                .message("User created Successfully")
                .success(true)
                .data(user)
                .build();
    }
}
