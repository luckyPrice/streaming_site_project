import {Component, OnInit} from '@angular/core';
import {LoginResponse, OidcSecurityService} from "angular-auth-oidc-client";
import {HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'youtube-ui';


  constructor(private oidcSecurityService: OidcSecurityService) {

  }
  ngOnInit(): void {
    this.oidcSecurityService
      .checkAuth()
      .subscribe(({isAuthenticated}) => {
        console.log('app is authenticated', isAuthenticated)
      });

    // const token = this.oidcSecurityService.getAccessToken().subscribe((token) => {
    //   console.log(token)
    //   const httpOptions = {
    //     headers: new HttpHeaders({
    //       Authorization: 'Bearer ' + token,
    //     }),
    //   };
    // });

  }
}
