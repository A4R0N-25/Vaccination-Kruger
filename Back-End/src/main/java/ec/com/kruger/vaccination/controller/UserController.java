/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.kruger.vaccination.controller;

import ec.com.kruger.vaccination.dto.LoginRequest;
import ec.com.kruger.vaccination.dto.LoginResponse;
import ec.com.kruger.vaccination.exception.InvalidPasswordException;
import ec.com.kruger.vaccination.exception.UserNotFoundException;
import ec.com.kruger.vaccination.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bran-
 */
@RestController
@RequestMapping("/api/v1/login")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = this.userService.login(loginRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (UserNotFoundException | InvalidPasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("{}",e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
