import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { LoginService } from '../services/login.service';

declare interface RouteInfo {
  path: string;
  option: string | undefined;
  title: string;
  icon: string;
  class: string;
}

export const ROUTES: RouteInfo[] = [
  { path: '/home/employee-info/', option: 'EMP', title: 'Employee Info', icon: 'info', class: 'active' },
  { path: '/home/employee-list', option: 'ADM', title: 'Employee List', icon: 'list_alt', class: '' },
];

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})

export class LayoutComponent implements OnInit {

  constructor(private location: Location, private route: Router, private loginService: LoginService) { }

  selected: string = "";

  //user: String | null = "";

  menuItems: any[] | undefined;

  userInfo : any

  ngOnInit() {
    /*console.log(sessionStorage.getItem('nombre'));
    if (sessionStorage.getItem('nombre') == null) {
      this.route.navigate([""]);
    }*/
    this.selected = (this.location.path().split("/"))[2];
    console.log(this.selected)
    this.location.onUrlChange(val => {
      this.selected = (val.split("/"))[2];
      console.log(this.selected)
    });
    this.menuItems = ROUTES.filter(menuItem => menuItem);
    //this.user = this.loginService.getItem('nombre');
    this.userInfo = this.loginService.getItem("userInfo")
  }

  signOut() {
    localStorage.clear();
    this.route.navigate([""]);
  }
}
