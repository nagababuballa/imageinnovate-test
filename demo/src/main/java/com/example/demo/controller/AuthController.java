package com.example.demo.controller;

import com.example.demo.dto.ResponseVO;
import com.example.demo.entity.Login;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/users")
public class AuthController {

    @Autowired
    AuthService authService;

    public ResponseEntity<ResponseVO<Login>> signup(@RequestBody Login login){
        ResponseVO<Login> response = authService.createLogin(login);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
