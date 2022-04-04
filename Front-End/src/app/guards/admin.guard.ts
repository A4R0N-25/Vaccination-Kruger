import { Injectable } from "@angular/core";
import {
    CanActivate,
    ActivatedRouteSnapshot,
    RouterStateSnapshot,
    Router,
} from "@angular/router";
import { LoginService } from "../services/login.service";

@Injectable()
export class AdminGuard implements CanActivate {

    constructor(private router: Router, private loginService: LoginService) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        let userInfo = this.loginService.getItem("userInfo")
        if (userInfo.role == "ADM") {
            return true;
        } else {
            this.router.navigate(["/home/employee-info/"+userInfo.id], {
                queryParams: {
                    return: state.url
                }
            });
            return false;
        }
    }
}
