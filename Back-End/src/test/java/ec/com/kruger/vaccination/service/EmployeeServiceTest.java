package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.dao.EmployeeRepository;
import ec.com.kruger.vaccination.dao.UserRespository;
import ec.com.kruger.vaccination.dao.VaccinationDetailRepository;
import ec.com.kruger.vaccination.dao.VaccineTypeRepository;
import ec.com.kruger.vaccination.dto.*;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.model.VaccinationDetail;
import ec.com.kruger.vaccination.model.VaccineType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private UserRespository userRespository;
    @Mock
    private VaccineTypeRepository vaccineTypeRepository;
    @Mock
    private VaccinationDetailRepository vaccinationDetailRepository;

    @InjectMocks
    private EmployeeService employeeService;
    private Employee employee1;
    private Employee employee2;
    private List<Employee> employeeList;
    private CreateEmployeeRQ createEmployeeRQ;
    private UpdateEmployeeRQ updateEmployeeRQ;
    private VaccineType vaccineType;
    private VaccinationDetail vaccinationDetail1;
    private VaccinationDetail vaccinationDetail2;
    private VaccinationDetail vaccinationDetail3;
    private List<VaccinationDetail> vaccinationDetailList;

    @BeforeEach
    void setUp() {
        this.employee1 = Employee.builder()
                .id(1)
                .names("Brandon Aaron")
                .surnames("Romero Cruz")
                .identification("1719870808")
                .email("bran-25@hotmail.com")
                .build();
        this.employee2 = Employee.builder()
                .id(2)
                .names("Jose Ricardo")
                .surnames("Romero Quinaluiza")
                .identification("1710449701")
                .email("jr_romero1@yahoo.com")
                .build();

        this.vaccineType = new VaccineType();
        vaccineType.setId(1);
        vaccineType.setName("Sputnik");

        this.vaccinationDetail1 = new VaccinationDetail();
        vaccinationDetail1.setEmployee(employee1);
        vaccinationDetail1.setVaccinationDate(new Date());
        vaccinationDetail1.setVaccineType(vaccineType);
        vaccinationDetail1.setId(1);
        vaccinationDetail1.setVaccinationDose(2);

        this.vaccinationDetail2 = new VaccinationDetail();
        vaccinationDetail2.setEmployee(employee2);
        vaccinationDetail2.setVaccinationDate(new Date());
        vaccinationDetail2.setVaccineType(vaccineType);
        vaccinationDetail2.setId(1);
        vaccinationDetail2.setVaccinationDose(2);

        this.vaccinationDetail3 = new VaccinationDetail();
        vaccinationDetail3.setEmployee(employee2);
        vaccinationDetail3.setVaccinationDate(new Date());
        vaccinationDetail3.setVaccineType(vaccineType);
        vaccinationDetail3.setId(1);
        vaccinationDetail3.setVaccinationDose(2);

        this.vaccinationDetailList = new ArrayList<>();
        vaccinationDetailList.add(vaccinationDetail1);
        vaccinationDetailList.add(vaccinationDetail2);
        vaccinationDetailList.add(vaccinationDetail3);

        this.updateEmployeeRQ = new UpdateEmployeeRQ();
        this.employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        this.createEmployeeRQ = new CreateEmployeeRQ();
    }

    @Test
    void givenEmployeeRegisterEmployee() {
        try {
            when(employeeRepository.findByIdentification(any())).thenReturn(new ArrayList<>());
            when(employeeRepository.findByEmail(any())).thenReturn(new ArrayList<>());
            when(userRespository.findByUsername(any())).thenReturn(Optional.empty());
            createEmployeeRQ.setEmail("bran-25@hotmail.com");
            createEmployeeRQ.setIdentification("1719870808");
            createEmployeeRQ.setNames("Brandon Aaron");
            createEmployeeRQ.setSurnames("Romero Cruz");
            LoginRequest generatedCredentials = LoginRequest.builder()
                    .username("brarom")
                    .password("brarom123")
                    .build();
            Assertions.assertEquals(generatedCredentials, employeeService.registerEmployee(createEmployeeRQ));
        }catch (Exception e){
            log.error("{}", e.getMessage());
        }
    }

    @Test
    void getAllEmployees() {
        try {
            when(employeeRepository.findAll()).thenReturn(employeeList);
            Assertions.assertEquals(employeeList,employeeService.getAllEmployees());
        } catch (Exception e){
            log.error("{}", e.getMessage());
        }
    }

    @Test
    void givenIdReturnEmployee() {
        try {
            when(employeeRepository.findById(any())).thenReturn(java.util.Optional.of(employee1));
            Assertions.assertEquals(employee1,employeeService.getEmployeeById(1));
        } catch (Exception e){
            log.error("{}", e.getMessage());
        }
    }

    @Test
    void givenUpdateEmployeeRQAndIdUpdateEmployee() {
        try {
            when(vaccineTypeRepository.findById(any())).thenReturn(java.util.Optional.of(vaccineType));
            when(employeeRepository.findById(any())).thenReturn(java.util.Optional.of(employee2));
            updateEmployeeRQ.setEmail("jr_romero1@yahoo.com");
            updateEmployeeRQ.setNames("Jose Ricardo");
            updateEmployeeRQ.setSurnames("Romero Quinaluiza");
            updateEmployeeRQ.setAddress("Quito");
            updateEmployeeRQ.setBirthday(new Date());
            updateEmployeeRQ.setPhone("0960122384");
            updateEmployeeRQ.setVaccinationStatus(true);
            List<VaccinationDetailRQ> vaccinationDetailsRQ = new ArrayList<>();
            VaccinationDetailRQ vaccinationDetailRQ = new VaccinationDetailRQ ();
            vaccinationDetailRQ.setVaccineType(1);
            vaccinationDetailRQ.setVaccinationDate(new Date());
            vaccinationDetailRQ.setVaccinationDose(1);
            vaccinationDetailsRQ.add(vaccinationDetailRQ);
            updateEmployeeRQ.setVaccinationDetails(vaccinationDetailsRQ);
            employeeService.updateEmployeeById(2,updateEmployeeRQ);
            verify(employeeRepository, times(1)).save(employee2);
        } catch (Exception e){
            log.error("{}", e.getMessage());
        }
    }

    @Test
    void givenIdDeleteEmployee() {
        try {
            when(employeeRepository.findById(any())).thenReturn(java.util.Optional.of(employee1));
            employeeService.deleteEmployeeById(1);
            verify(employeeRepository, times(1)).delete(employee1);
        } catch (Exception e){
            log.error("{}", e.getMessage());
        }
    }

    @Test
    void givenVaccinationStatusReturnEmployees() {
        try {
            List<Employee> testEmployees = new ArrayList<>();
            testEmployees.add(employee2);
            when(employeeRepository.findByVaccinationStatus(true)).thenReturn(testEmployees);
            Assertions.assertEquals(testEmployees,employeeService.findByVaccinationStatus(true));
        } catch (Exception e){
            log.error("{}", e.getMessage());
        }
    }

    @Test
    void givenVaccineTypeReturnEmployees() {
        try {
            when(vaccineTypeRepository.findByName(any())).thenReturn(java.util.Optional.of(vaccineType));
            when(vaccinationDetailRepository.findByVaccineType(vaccineType)).thenReturn(vaccinationDetailList);
            Assertions.assertEquals(employeeList,employeeService.findByVaccineType("Sputnik"));
        } catch (Exception e){
            log.error("{}", e.getMessage());
        }
    }

    @Test
    void givenDateRangeVaccinationReturnEmployees() {
        try {
            when(vaccinationDetailRepository.findByVaccinationDateBetween(any(),any())).thenReturn(vaccinationDetailList);
            Assertions.assertEquals(employeeList,employeeService.findByDates(new Date(), new Date()));
        } catch (Exception e){
            log.error("{}", e.getMessage());
        }
    }
}