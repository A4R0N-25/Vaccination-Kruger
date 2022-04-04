package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.dao.EmployeeRepository;
import ec.com.kruger.vaccination.dao.UserRespository;
import ec.com.kruger.vaccination.dto.LoginRequest;
import ec.com.kruger.vaccination.dto.LoginResponse;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.model.User;
import ec.com.kruger.vaccination.transform.Encrypt;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRespository userRespository;

    @InjectMocks
    private UserService userService;
    private User user;
    private LoginResponse loginResponse;
    private LoginRequest loginRequest1;
    private LoginRequest loginRequest2;
    private LoginRequest loginRequest3;



    @BeforeEach
    void setUp() throws Exception {
        this.user = User.builder()
                .username("brarom")
                .password(Encrypt.encryptPassword("brarom"))
                .role("ADM")
                .employee(new Employee())
                .build();
        this.loginResponse = LoginResponse.builder()
                .username("brarom")
                .token("testToken")
                .role("ADM")
                .build();
        this.loginRequest1 = LoginRequest.builder()
                .username("brarom")
                .password("brarom")
                .build();
        this.loginRequest2 = LoginRequest.builder()
                .username("aarrom")
                .password("brarom")
                .build();
        this.loginRequest3 = LoginRequest.builder()
                .username("brarom")
                .password("brarom1")
                .build();
    }

    @Test
    void givenCorrectLoginRQReturnLoginRS() {
        try {
            when(userRespository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
            //log.info("info: {}",userService.login(loginRequest1));
            LoginResponse loginResponse1 = userService.login(loginRequest1);
            loginResponse1.setToken("testToken");
            Assertions.assertEquals(loginResponse, loginResponse1);
        }catch (Exception e){
            log.info("Error: {}",e.getMessage());
        }
    }

    @Test
    void givenWrongUserReturnUserNotFound() {
        try {
            when(userRespository.findByUsername(any())).thenReturn(Optional.empty());
            log.info("info: {}",userService.login(loginRequest2));
            Assertions.assertNotEquals(loginResponse, userService.login(loginRequest1));
        }catch (Exception e){
            log.info("Error: {}",e.getMessage());
        }
    }

    @Test
    void givenWrongPasswordReturnBadRequest() {
        try {
            when(userRespository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
            log.info("info: {}",userService.login(loginRequest3));
            Assertions.assertNotEquals(loginResponse, userService.login(loginRequest3));
        }catch (Exception e){
            log.info("Error: {}",e.getMessage());
        }
    }
}