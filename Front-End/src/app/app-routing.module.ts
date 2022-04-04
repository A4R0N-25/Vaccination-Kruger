import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmployeeCreateComponent } from './employee/employee-create/employee-create.component';
import { EmployeeInfoComponent } from './employee/employee-info/employee-info.component';
import { EmployeeUpdateComponent } from './employee/employee-update/employee-update.component';
import { EmployeesListComponent } from './employee/employees-list/employees-list.component';
import { AdminGuard } from './guards/admin.guard';
import { LoginGuard } from './guards/login.guard';
import { TokenGuard } from './guards/token.guard';
import { LayoutComponent } from './layout/layout.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    canActivate: [LoginGuard],
    component: LoginComponent
  },
  {
    path: 'home',
    canActivate: [TokenGuard],
    component: LayoutComponent,
    children:[
      {
        path: "employee-list",
        canActivate: [AdminGuard],
        component:EmployeesListComponent
      },
      {
        path: "employee-create",
        component:EmployeeCreateComponent
      },
      {
        path: "employee-info/:id",
        component:EmployeeInfoComponent
      },
      {
        path: "employee-update/:id",
        component: EmployeeUpdateComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
