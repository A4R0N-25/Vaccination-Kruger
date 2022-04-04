import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { EmployeeService } from 'src/app/services/employee.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  styleUrls: ['./employees-list.component.css']
})
export class EmployeesListComponent implements OnInit {

  constructor(private employeeService: EmployeeService,private route: Router) { }

  employees: any[] = []

  ngOnInit(): void {
    this.employeeService.getAllEmployees().then((res:any) =>{
      //console.log("RES: ",res)
      this.employees = res;
      //console.log("Empleados: ",this.employees)
    }, err =>{
      console.log(err)
    })
  }

  addEmployee(){
    this.route.navigate(["home/employee-create"]);
  }

  infoEmployee(id: number){
    this.route.navigate([`home/employee-info/${id}`]);
  }

  deleteEmployee(id: number){
    this.employeeService.deleteEmployee(id).then(res =>{
      window.location.reload()
    }, err =>{
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: 'Something went wrong',
        showConfirmButton: false,
        timer: 1500
      })
    })
  }

  editEmployee(id: number){
    this.route.navigate([`home/employee-update/${id}`]);
  }

}
