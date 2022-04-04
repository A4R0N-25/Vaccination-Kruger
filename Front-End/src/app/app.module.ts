import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { ServicesModule } from './services/services.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LayoutComponent } from './layout/layout.component';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { CommonModule } from '@angular/common';
import { EmployeeModule } from './employee/employee.module';
import { AdminGuard } from './guards/admin.guard';
import { LoginGuard } from './guards/login.guard';
import { TokenGuard } from './guards/token.guard';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LayoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatSnackBarModule,
    ServicesModule,
    FormsModule, 
    ReactiveFormsModule,
    HttpClientModule,
    CommonModule,
    EmployeeModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
    AdminGuard,
    LoginGuard,
    TokenGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
