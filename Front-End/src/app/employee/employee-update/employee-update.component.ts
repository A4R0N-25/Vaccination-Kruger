import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeService } from 'src/app/services/employee.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-employee-update',
  templateUrl: './employee-update.component.html',
  styleUrls: ['./employee-update.component.css']
})
export class EmployeeUpdateComponent implements OnInit {

  constructor(private employeeService: EmployeeService, private router: Router, private route: ActivatedRoute) {
    this.employeeId = this.route.snapshot.params['id'];
  }

  private employeeId

  public employeeInfo: any

  public flag: Boolean = false

  ngOnInit(): void {
    this.employeeService.getEmployeeById(this.employeeId).then((res: any) => {
      this.employeeInfo = res
      console.log("INFO: ", this.employeeInfo)
      this.setEmployeeInfo()
    }, err => {
      Swal.fire({
        position: 'top-end',
        icon: 'error',
        title: err.error,
        showConfirmButton: false,
        timer: 1500
      })
      this.router.navigate(["home/employee-list"]);
    })
  }

  employee = new FormGroup({
    identification: new FormControl('', [Validators.required]),
    names: new FormControl('', [Validators.required]),
    surnames: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.email]), // we can use regular expressions to validate email
    birthday: new FormControl(''),
    phone: new FormControl(''),
    address: new FormControl(''),
    status: new FormControl(''),
    vaccine: new FormControl(''),
    date: new FormControl(''),
    dose: new FormControl('')
  });

  setEmployeeInfo() {
    this.employee.controls.identification.setValue(this.employeeInfo.identification)
    this.employee.controls.names.setValue(this.employeeInfo.names)
    this.employee.controls.surnames.setValue(this.employeeInfo.surnames)
    this.employee.controls.email.setValue(this.employeeInfo.email)
    this.employee.controls.birthday.setValue(this.employeeInfo.birthday)
    this.employee.controls.phone.setValue(this.employeeInfo.phone)
    this.employee.controls.address.setValue(this.employeeInfo.address)
    this.employee.controls.status.setValue(this.employeeInfo.status)
  }

  create() {
    let detail = {
      vaccineType: this.employee.controls['vaccine'].value,
      vaccineDate: this.employee.controls['date'].value,
      vaccineDose: this.employee.controls['dose'].value
    }

    let user = {
      identification: this.employee.controls['identification'].value,
      names: this.employee.controls['names'].value,
      surnames: this.employee.controls['surnames'].value,
      email: this.employee.controls['email'].value,
      birthday: this.employee.controls['birthday'].value,
      phone: this.employee.controls['phone'].value,
      address: this.employee.controls['address'].value,
      vaccinationStatus: (this.employee.controls['status'].value == 'true' ? true : false),
      vaccinationDetails: (this.employee.controls['status'].value == 'true' ? [detail] : [])
    }
    console.log("PRE: ", user)

    console.log("POS: ", user)

    this.employeeService.updateEmployeeById(this.employeeId, user).then((res: any) => {
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
        title: 'User Update'
      })
      this.router.navigate(["home/employee-list"]);
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

}
