import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EmployeeService } from 'src/app/services/employee.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-employee-create',
  templateUrl: './employee-create.component.html',
  styleUrls: ['./employee-create.component.css']
})
export class EmployeeCreateComponent implements OnInit {

  constructor(private employeeService: EmployeeService, private route: Router) { }

  ngOnInit(): void {
  }

  employee = new FormGroup({
    identification: new FormControl('', [Validators.required]),
    names: new FormControl('', [Validators.required]),
    surnames: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.email]) // we can use regular expressions to validate email
  });

  create() {
    let user = {
      identification: this.employee.controls['identification'].value,
      names: this.employee.controls['names'].value,
      surnames: this.employee.controls['surnames'].value,
      email: this.employee.controls['email'].value
    }

    this.employeeService.createNewEmployee(user).then((res: any) => {
      const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 5000,
        timerProgressBar: true,
        didOpen: (toast) => {
          toast.addEventListener('mouseenter', Swal.stopTimer)
          toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
      })
      Toast.fire({
        icon: 'info',
        title: 'Generated Credentials \n -> username: '+res.username+' \n -> password: '+res.password
      })
      this.route.navigate(["home/employee-list"]);
    }, err =>{
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: err.error,
        showConfirmButton: false,
        timer: 1500
      })
    })
  }

}
