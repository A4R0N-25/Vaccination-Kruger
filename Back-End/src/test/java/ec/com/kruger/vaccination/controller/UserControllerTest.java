package ec.com.kruger.vaccination.controller;

import ec.com.kruger.vaccination.dto.CreateEmployeeRQ;
import ec.com.kruger.vaccination.dto.LoginRequest;
import ec.com.kruger.vaccination.dto.LoginResponse;
import ec.com.kruger.vaccination.service.EmployeeService;
import ec.com.kruger.vaccination.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    /*@InjectMocks
    private UserController userController;*/

    @BeforeEach
    void setUp() {
        /*this.userController = new UserController();
        this.userService = new UserService();*/
    }

    @Test
    void login() throws Exception {
        /*createEmployeeRQ.setEmail("bran-25@hotmail.com");
        createEmployeeRQ.setIdentification("1719870808");
        createEmployeeRQ.setNames("Brandon Aaron");
        createEmployeeRQ.setSurnames("Romero Cruz");*/
        //try{
           /* UserController userController = new UserController();
            LoginRequest loginRequest = LoginRequest.builder()
                    .username("brarom")
                    .password("brarom123")
                    .build();
            LoginResponse loginResponse  = LoginResponse.builder()
                    .username("brarom")
                    .role("EMP")
                    .token("testToken")
                    .build();
            ResponseEntity response = ResponseEntity.ok(loginResponse);
            when(userService.login(loginRequest)).thenReturn(loginResponse);
            log.info("RES: {}",userController.login(loginRequest));
            Assertions.assertEquals(response,userController.login(loginRequest));*/
        /*}catch (Exception e){
            log.error("{}",e.getMessage());
            log.error("{}",e.getCause());
        }*/

    }
}