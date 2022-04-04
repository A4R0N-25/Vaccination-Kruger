import { Injectable } from "@angular/core";
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    Router,
} from "@angular/router";
import { LoginService } from "../services/login.service";

@Injectable()
export class TokenGuard implements CanActivate {

    constructor(private router: Router, private loginService: LoginService) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        this.loginService.validateToken().then(res =>{
            this.loginService.validation = true
            console.log("Token: ",true)
        }, err =>{
            console.log("Token: ",false)
            this.loginService.validation = false
        })
        if(this.loginService.validation){
            return true
        }else{
            localStorage.clear()
            this.router.navigate(["/login"], {
                queryParams: {
                    return: state.url
                }
            });
            return false;
        }
        /*if (validate!=null) {
            return validate;
        } else {
            this.router.navigate(["/login"], {
                queryParams: {
                    return: state.url
                }
            });
            return false;
        }*/
    }
}
