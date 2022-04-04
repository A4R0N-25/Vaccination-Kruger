import { Injectable } from "@angular/core";
import {
    HttpEvent,
    HttpInterceptor,
    HttpHandler,
    HttpRequest
} from "@angular/common/http";
import { Observable } from "rxjs";
import { LoginService } from "../services/login.service";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(private loginService: LoginService) { }

    intercept(
        req: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {
        var userInfo = this.loginService.getItem("userInfo")
        //console.log("inter: ", userInfo)
        var token = userInfo?.token

        //console.log("token: ", token)

        var changedReq;

        if (token) {

            changedReq = req.clone({
                setHeaders: {
                    Authorization: `${token}`
                },
            });

        } else {

            changedReq = req;

        }
        return next.handle(changedReq);
    }
}
