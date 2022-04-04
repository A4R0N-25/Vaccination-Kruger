import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";

@Injectable()
export class LoginService {

    constructor(private http: HttpClient){
    }

    private ls = window.localStorage;

    public validation = false

    login(loginRequest:any){
        return this.http.post(`${environment.apiUrl}/login`,loginRequest).toPromise()
    }

    validateToken(){
        return this.http.get(`${environment.apiUrl}/login`).toPromise()
    }

    public getItem(key: any) {
        const value = this.ls.getItem(key);
        if(value == null){
            return null;
        }else{
            return JSON.parse(value)
        }
      }

}