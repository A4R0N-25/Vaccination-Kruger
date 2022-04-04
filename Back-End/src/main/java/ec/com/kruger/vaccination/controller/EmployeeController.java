/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.kruger.vaccination.controller;

import ec.com.kruger.vaccination.dto.CreateEmployeeRQ;
import ec.com.kruger.vaccination.dto.FilterDatesRQ;
import ec.com.kruger.vaccination.dto.LoginRequest;
import ec.com.kruger.vaccination.dto.UpdateEmployeeRQ;
import ec.com.kruger.vaccination.exception.InvalidDataException;
import ec.com.kruger.vaccination.exception.UserNotFoundException;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author bran-
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/employee")
@Slf4j
@Api(tags = "Employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @ApiOperation(value = "Add new employee",
            notes = "Only admin can add a new employee")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok - Successful registration"),
        @ApiResponse(code = 400, message = "Bad Request - Invalid data"),
        @ApiResponse(code = 500, message = "Internal Server Error - Server error during process")})
    @PreAuthorize("hasRole('ADM')")
    public ResponseEntity createEmployee(@RequestBody CreateEmployeeRQ employeeRequest) {
        try {
            LoginRequest generatedCredentials = this.employeeService.registerEmployee(employeeRequest);
            return ResponseEntity.ok(generatedCredentials);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ADM')")
    @ApiOperation(value = "Get all employees",
            notes = "Get all employees")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok - Successful serch"),
        @ApiResponse(code = 500, message = "Internal Server Error - Server error during process")})
    public ResponseEntity getAllEmployees() {
        try {
            List<Employee> employees = this.employeeService.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADM') OR hasRole('EMP')")
    @ApiOperation(value = "Get employee by ID",
            notes = "Get employee information by ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok - Successful serch"),
        @ApiResponse(code = 404, message = "Not Found - User not found"),
        @ApiResponse(code = 500, message = "Internal Server Error - Server error during process")})
    public ResponseEntity getEmployeeById(@PathVariable int id) {
        try {
            Employee employee = this.employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADM') OR hasRole('EMP')")
    @ApiOperation(value = "Update employee by ID",
            notes = "Update employee information by ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok - Successful update"),
        @ApiResponse(code = 400, message = "Bad Request - Invalid data"),
        @ApiResponse(code = 500, message = "Internal Server Error - Server error during process")})
    public ResponseEntity updateEmployeeById(@PathVariable int id, @RequestBody UpdateEmployeeRQ updateEmployeeRQ) {
        try {
            this.employeeService.updateEmployeeById(id, updateEmployeeRQ);
            return ResponseEntity.ok().build();
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            log.info("{}", e.getCause());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADM')")
    @ApiOperation(value = "Delete employee by ID",
            notes = "Delete employee by ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok - Successful update"),
        @ApiResponse(code = 404, message = "Not Found - User not found"),
        @ApiResponse(code = 500, message = "Internal Server Error - Server error during process")})
    public ResponseEntity deleteEmployeeById(@PathVariable int id) {
        try {
            this.employeeService.deleteEmployeeById(id);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/filter/status/{status}")
    @PreAuthorize("hasRole('ADM')")
    @ApiOperation(value = "Filter by vaccination status",
            notes = "Filter employees by vaccination status")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok - Successful search"),
        @ApiResponse(code = 500, message = "Internal Server Error - Server error during process")})
    public ResponseEntity findEmployeesByVaccinationStatus(@PathVariable boolean status) {
        try {
            List<Employee> employees = this.employeeService.findByVaccinationStatus(status);
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/filter/vaccination/dates")
    @PreAuthorize("hasRole('ADM')")
    @ApiOperation(value = "Filter by vaccination date range",
            notes = "Filter employees by vaccination date range")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok - Successful search"),
        @ApiResponse(code = 500, message = "Internal Server Error - Server error during process")})
    public ResponseEntity findEmployeesByVaccinationDates(@RequestBody FilterDatesRQ filterDatesRQ) {
        try {
            List<Employee> employees = this.employeeService.findByDates(filterDatesRQ.getStart(), filterDatesRQ.getEnd());
            return ResponseEntity.ok(employees);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/filter/vaccine/type/{type}")
    @PreAuthorize("hasRole('ADM')")
    @ApiOperation(value = "Filter by type of vaccine",
            notes = "Filter employees by type of vaccine")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Ok - Successful search"),
        @ApiResponse(code = 500, message = "Internal Server Error - Server error during process")})
    public ResponseEntity findEmployeesByVaccineType(@PathVariable String type) {
        try {
            List<Employee> employees = this.employeeService.findByVaccineType(type);
            return ResponseEntity.ok(employees);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
