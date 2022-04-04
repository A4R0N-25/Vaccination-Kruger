/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.dao.UserRespository;
import ec.com.kruger.vaccination.dto.LoginRequest;
import ec.com.kruger.vaccination.dto.LoginResponse;
import ec.com.kruger.vaccination.exception.InvalidPasswordException;
import ec.com.kruger.vaccination.exception.UserNotFoundException;
import ec.com.kruger.vaccination.model.User;
import ec.com.kruger.vaccination.transform.Encrypt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author bran-
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRespository userRespository;

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        Optional<User> optionalUser = this.userRespository.findByUsername(loginRequest.getUsername());
        //log.info("Usuario: {}", optionalUser.get());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        log.info("PASS: {}",Encrypt.encryptPassword(loginRequest.getPassword()));
        if (!optionalUser.get().getPassword().equals(Encrypt.encryptPassword(loginRequest.getPassword()))) {
            throw new InvalidPasswordException("The password doesn't match");
        }

        LoginResponse loginResponse = LoginResponse.builder()
                .username(optionalUser.get().getUsername())
                .role(optionalUser.get().getRole())
                .token(getJWTToken(optionalUser.get().getUsername(),optionalUser.get().getRole()))
                .build();
        return loginResponse;
    }

    private String getJWTToken(String username, String role) {
        String secretKey = "Braaron";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_"+role.toUpperCase());

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
