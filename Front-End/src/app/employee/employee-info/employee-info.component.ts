import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from 'src/app/services/employee.service';
import { LoginService } from 'src/app/services/login.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-employee-info',
  templateUrl: './employee-info.component.html',
  styleUrls: ['./employee-info.component.css']
})
export class EmployeeInfoComponent implements OnInit {

  constructor(private route: ActivatedRoute, private employeeService: EmployeeService, private router: Router,
    private loginService: LoginService) {
    this.employeeId = this.route.snapshot.params['id'];
  }

  private employeeId

  public employee : any

  ngOnInit(): void {
    console.log("ID: ", this.employeeId)
    this.employeeService.getEmployeeById(this.employeeId).then((res: any) => {
      this.employee=res
      console.log("EMP: ",this.employee)
    }, err => {
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: err.error,
        showConfirmButton: false,
        timer: 1500
      })
      if(this.loginService.getItem("userInfo").role == "ADM"){
        this.router.navigate(["home/employee-list"]);
      }
    })
  }

  editEmployee(){
    this.router.navigate([`home/employee-update/${this.employeeId}`]);
  }

}
