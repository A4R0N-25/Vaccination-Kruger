import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EmployeesListComponent } from './employees-list/employees-list.component';
import { BrowserModule } from '@angular/platform-browser';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { EmployeeCreateComponent } from './employee-create/employee-create.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EmployeeInfoComponent } from './employee-info/employee-info.component';
import { EmployeeUpdateComponent } from './employee-update/employee-update.component';





@NgModule({
  declarations: [
    EmployeesListComponent,
    EmployeeCreateComponent,
    EmployeeInfoComponent,
    EmployeeUpdateComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    MatButtonModule,
    MatIconModule,
    FormsModule, 
    ReactiveFormsModule 
  ]
})
export class EmployeeModule { }
