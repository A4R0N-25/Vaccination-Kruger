import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from './login.service';
import { EmployeeService } from './employee.service';



@NgModule({
  declarations: [],
  providers:[
    LoginService,
    EmployeeService
  ],
  imports: [
    CommonModule
  ]
})
export class ServicesModule { }
