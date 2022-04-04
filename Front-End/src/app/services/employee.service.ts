import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";


@Injectable()
export class EmployeeService {

    constructor(private http: HttpClient){
    }

    getAllEmployees(){
        return this.http.get(`${environment.apiUrl}/employee`).toPromise()
    }

    getEmployeeById(id: number){
        return this.http.get(`${environment.apiUrl}/employee/${id}`).toPromise()
    }

    createNewEmployee(newEmployee: any){
        return this.http.post(`${environment.apiUrl}/employee`,newEmployee).toPromise()
    }

    updateEmployeeById(id: number, updateInfo:any){
        return this.http.put(`${environment.apiUrl}/employee/${id}`,updateInfo).toPromise()
    }

    deleteEmployee(id: number){
        return this.http.delete(`${environment.apiUrl}/employee/${id}`).toPromise()
    }

    getEmployeesByVaccinationStatus(status: boolean){
        return this.http.get(`${environment.apiUrl}/employee/filter/status/${status}`).toPromise()
    }

    getEmployeesByVaccinationType(type:string){
        return this.http.get(`${environment.apiUrl}/employee/filter/vaccine/type/${type}`).toPromise()
    }

    getEmployeeByVaccinationDateRange(dateInfo: any){
        return this.http.post(`${environment.apiUrl}/employee/filter/vaccination/dates`, dateInfo).toPromise()
    }

}