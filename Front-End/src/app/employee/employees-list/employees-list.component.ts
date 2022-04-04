import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EmployeeService } from 'src/app/services/employee.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  styleUrls: ['./employees-list.component.css']
})
export class EmployeesListComponent implements OnInit {

  constructor(private employeeService: EmployeeService, private route: Router) { }

  employees: any[] = []

  filter = new FormGroup({
    filterType: new FormControl(''),
    status: new FormControl(''),
    start: new FormControl(''),
    end: new FormControl(''),
    type: new FormControl(''),
  });

  ngOnInit(): void {
    this.employeeService.getAllEmployees().then((res: any) => {
      //console.log("RES: ",res)
      this.employees = res;
      //console.log("Empleados: ",this.employees)
    }, err => {
      console.log(err)
    })
    this.filter.controls.filterType.setValue('0')
  }

  filterEmployee() {
    switch (this.filter.controls['filterType'].value) {
      case '0':
        this.employeeService.getAllEmployees().then((res: any) => {
          this.employees = res;
        }, err => {
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: err.error,
            showConfirmButton: false,
            timer: 1500
          })
        })
        break;
      case '1':
        let dateInfo = {
          start: this.filter.controls['start'].value,
          end: this.filter.controls['end'].value
        }
        this.employeeService.getEmployeeByVaccinationDateRange(dateInfo).then((res: any) => {
          this.employees = res;
        }, err =>{
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: err.error,
            showConfirmButton: false,
            timer: 1500
          })
        })
        break;
      case '2':
        this.employeeService.getEmployeesByVaccinationType(this.filter.controls['type'].value).then((res: any) => {
          this.employees = res;
        }, err =>{
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: err.error,
            showConfirmButton: false,
            timer: 1500
          })
        })
        break;
      case '3':
        this.employeeService.getEmployeesByVaccinationStatus((this.filter.controls['status'].value == "0" ? true : false)).then((res: any) => {
          this.employees = res;
        }, err => {
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: err.error,
            showConfirmButton: false,
            timer: 1500
          })
        })
        break;
    }
  }

  addEmployee() {
    this.route.navigate(["home/employee-create"]);
  }

  infoEmployee(id: number) {
    this.route.navigate([`home/employee-info/${id}`]);
  }

  deleteEmployee(id: number) {
    this.employeeService.deleteEmployee(id).then(res => {
      window.location.reload()
    }, err => {
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: err.error,
        showConfirmButton: false,
        timer: 1500
      })
    })
  }

  editEmployee(id: number) {
    this.route.navigate([`home/employee-update/${id}`]);
  }

}
