/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.dao.EmployeeRepository;
import ec.com.kruger.vaccination.dao.UserRespository;
import ec.com.kruger.vaccination.dao.VaccinationDetailRepository;
import ec.com.kruger.vaccination.dao.VaccineTypeRepository;
import ec.com.kruger.vaccination.dto.CreateEmployeeRQ;
import ec.com.kruger.vaccination.dto.LoginRequest;
import ec.com.kruger.vaccination.dto.UpdateEmployeeRQ;
import ec.com.kruger.vaccination.dto.VaccinationDetailRQ;
import ec.com.kruger.vaccination.exception.InvalidDataException;
import ec.com.kruger.vaccination.exception.UserNotFoundException;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.model.User;
import ec.com.kruger.vaccination.model.VaccinationDetail;
import ec.com.kruger.vaccination.model.VaccineType;
import ec.com.kruger.vaccination.transform.Encrypt;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bran-
 */
@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private VaccinationDetailRepository vaccinationDetailRepository;

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

    public LoginRequest registerEmployee(CreateEmployeeRQ createEmployeeRequest) throws Exception {
        List<Employee> employees = this.employeeRepository.findByIdentification(createEmployeeRequest.getIdentification());
        if (!employees.isEmpty()) {
            throw new InvalidDataException("Identification already in use");
        }
        employees = this.employeeRepository.findByEmail(createEmployeeRequest.getEmail());
        if (!employees.isEmpty()) {
            throw new InvalidDataException("Email already in use");
        }
        if (!validateEmail(createEmployeeRequest.getEmail()) || !validateIdentification(createEmployeeRequest.getIdentification())
                || !validateNames(createEmployeeRequest.getNames(), createEmployeeRequest.getSurnames())) {
            throw new InvalidDataException("Invalid field information");
        }
        Employee employee = Employee.builder()
                .names(createEmployeeRequest.getNames().toUpperCase())
                .surnames(createEmployeeRequest.getSurnames().toUpperCase())
                .identification(createEmployeeRequest.getIdentification())
                .email(createEmployeeRequest.getEmail())
                .build();
        Employee newEmployee = this.employeeRepository.saveAndFlush(employee);
        String username = createEmployeeRequest.getNames().substring(0, 3) + createEmployeeRequest.getSurnames().substring(0, 3);
        username = username.toLowerCase();
        Optional<User> optionalUser = this.userRespository.findByUsername(username);
        int i = 1;
        String baseUsername = username;
        while (optionalUser.isPresent()) {
            username = baseUsername + Integer.toString(i);
            optionalUser = this.userRespository.findByUsername(username);
            i++;
        }
        String password = username + "123";
        User user = User.builder()
                .username(username)
                .password(Encrypt.encryptPassword(password))
                .employee(newEmployee)
                .role("EMP")
                .build();
        this.userRespository.save(user);

        LoginRequest generaredCredentials = LoginRequest.builder()
                .username(username)
                .password(password)
                .build();
        return generaredCredentials;
    }

    public List<Employee> getAllEmployees(){
        return this.employeeRepository.findAll();
    }
    
    public Employee getEmployeeById(int id) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new UserNotFoundException("Employee don´t found");
        }
        return optionalEmployee.get();
    }

    public void updateEmployeeById(int id, UpdateEmployeeRQ updateEmployeeRQ) {
        log.info("UPD: {}", updateEmployeeRQ);
        if (!updateEmployeeRQ.getVaccinationStatus() && updateEmployeeRQ.getVaccinationDetails().size() > 0 ) {
            throw new InvalidDataException("If you have not been vaccinated you cannot add details");
        }
        if (updateEmployeeRQ.getVaccinationStatus() && updateEmployeeRQ.getVaccinationDetails().isEmpty()) {
            throw new InvalidDataException("You need to add your vaccination details");
        }
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new UserNotFoundException("Employee not found");
        }
        Employee employee = optionalEmployee.get();
        employee.setNames(updateEmployeeRQ.getNames());
        employee.setSurnames(updateEmployeeRQ.getSurnames());
        employee.setBirthday(updateEmployeeRQ.getBirthday());
        employee.setAddress(updateEmployeeRQ.getAddress());
        employee.setPhone(updateEmployeeRQ.getPhone());
        employee.setVaccinationStatus(updateEmployeeRQ.getVaccinationStatus());

        this.employeeRepository.save(employee);

        if (updateEmployeeRQ.getVaccinationDetails().size() > 0 && updateEmployeeRQ.getVaccinationStatus()) {
            for (VaccinationDetailRQ detailRQ : updateEmployeeRQ.getVaccinationDetails()) {
                Optional<VaccineType> optionalVaccineType = this.vaccineTypeRepository.findById(detailRQ.getVaccineType());
                if (optionalVaccineType.isEmpty()) {
                    throw new InvalidDataException("Type of vaccine not found");
                }
                VaccinationDetail vaccinationDetail = new VaccinationDetail();
                vaccinationDetail.setEmployee(employee);
                vaccinationDetail.setVaccinationDate(detailRQ.getVaccinationDate());
                vaccinationDetail.setVaccinationDose(detailRQ.getVaccinationDose());
                vaccinationDetail.setVaccineType(optionalVaccineType.get());
                this.vaccinationDetailRepository.save(vaccinationDetail);
            }
        }
    }

    public void deleteEmployeeById(int id) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);
        if(optionalEmployee.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        Optional<User> optionalUser = this.userRespository.findByEmployee(optionalEmployee.get());
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        this.userRespository.delete(optionalUser.get());
        this.employeeRepository.delete(optionalEmployee.get());
    }

    public List<Employee> findByVaccinationStatus(boolean vaccinationStatus) {
        return this.employeeRepository.findByVaccinationStatus(vaccinationStatus);
    }

    public List<Employee> findByVaccineType(String vaccineType) {
        Optional<VaccineType> optionalVaccineType = this.vaccineTypeRepository.findByName(vaccineType);
        if (optionalVaccineType.isEmpty()) {
            throw new InvalidDataException("Type of vaccine not found");
        }
        List<VaccinationDetail> details = this.vaccinationDetailRepository.findByVaccineType(optionalVaccineType.get());
        List<Employee> employees = new ArrayList<>();
        for (VaccinationDetail detail : details) {
            if (!employees.contains(detail.getEmployee())) {
                employees.add(detail.getEmployee());
            }
        }
        return employees;
    }

    public List<Employee> findByDates(Date start, Date end) {
        List<VaccinationDetail> details = this.vaccinationDetailRepository.findByVaccinationDateBetween(start, end);
        List<Employee> employees = new ArrayList<>();
        for (VaccinationDetail detail : details) {
            if (!employees.contains(detail.getEmployee())) {
                employees.add(detail.getEmployee());
            }
        }
        return employees;
    }

    private boolean validateIdentification(String identification) {
        if (identification.length() != 10) {
            return false;
        }
        int sum = 0;
        int num = 0;
        int validationNum = 0;
        for (int i = 0; i < 9; i++) {
            if (i % 2 == 0) {
                num = Integer.parseInt(identification.substring(i, i + 1)) * 2;
            } else {
                num = Integer.parseInt(identification.substring(i, i + 1));
            }
            if (num >= 10) {
                num += -9;
            }
            sum += num;
        }
        validationNum = (((sum / 10) + 1) * 10) - sum;
        return validationNum == Integer.parseInt(identification.substring(9));
    }

    private boolean validateEmail(String mail) {
        Pattern pat = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z])+(.[a-z]+))");
        Matcher mather = pat.matcher(mail);
        return mather.find();
    }

    private boolean validateNames(String names, String surnames) {
        Pattern pat = Pattern.compile("([A-Za-zÀ-ÿ\\u00f1\\u00d1]+)( )([A-Za-zÀ-ÿ\\u00f1\\u00d1]+)");
        Matcher matherName = pat.matcher(names);
        Matcher matherSurname = pat.matcher(surnames);
        return matherName.find() && matherSurname.find();
    }

}
