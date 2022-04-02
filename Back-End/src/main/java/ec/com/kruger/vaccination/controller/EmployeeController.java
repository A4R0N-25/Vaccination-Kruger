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
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bran-
 */
@RestController
@RequestMapping("/api/v1/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    //@PreAuthorize("hasRole('ADM')")
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

    @GetMapping(value = "/{id}")
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
    public ResponseEntity updateEmployeeById(@PathVariable int id, @RequestBody UpdateEmployeeRQ updateEmployeeRQ) {
        try {
            this.employeeService.updateEmployeeById(id, updateEmployeeRQ);
            return ResponseEntity.ok().build();
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/filter/status/{status}")
    public ResponseEntity findEmployeesByVaccinationStatus(@PathVariable boolean status) {
        try {
            List<Employee> employees = this.employeeService.findByVaccinationStatus(status);
            return ResponseEntity.ok(employees);
        } catch (InvalidDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping(value = "/filter/vaccination/dates")
    public ResponseEntity findEmployeesByVaccinationStatus(@RequestBody FilterDatesRQ filterDatesRQ) {
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
