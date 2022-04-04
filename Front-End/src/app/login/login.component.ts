import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { LoginService } from '../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private route: Router, private loginService: LoginService, private _snackBar: MatSnackBar) { }

  private ls = window.localStorage;

  login = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  auth: boolean = false

  ngOnInit() {

  }

  validate() {
    let user = {
      username: this.login.controls['username'].value,
      password: this.login.controls['password'].value
    }

    this.loginService.login(user).then((data: any) => {
      Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Welcome ' + user.username + '!',
        showConfirmButton: false,
        timer: 1500
      })
      this.loginService.validation = true
      let userInfo = {
        username: data.username,
        role: data.role,
        token: data.token,
        id: data.id
      }
      this.ls.setItem('userInfo', JSON.stringify(userInfo))
      if (userInfo.role == "ADM") {
        this.route.navigate(["home/employee-list"]);
      } else {
        this.route.navigate(["home/employee-info/" + userInfo.id]);
      }
    }, (err: any) => {
      this._snackBar.open(err.error, "close", { duration: 2500 });
      this.login.controls['password'].reset();
      this.login.markAllAsTouched();
    })

  }

}
