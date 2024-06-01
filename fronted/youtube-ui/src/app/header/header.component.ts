import {Component, OnInit} from '@angular/core';
import {LoginResponse, OidcSecurityService} from "angular-auth-oidc-client";
import {HttpHeaders} from "@angular/common/http";

@Component({
  selector: 'app-header',
  templateUrl: 'header.component.html',
  styleUrl: 'header.component.css'
})
export class HeaderComponent implements OnInit{
  isAuthenticated: boolean = false

  constructor(private oidcSecurityService: OidcSecurityService) {
  }


  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(({isAuthenticated})=>{
      this.isAuthenticated = isAuthenticated;
    })
  }

  login() {
    this.oidcSecurityService.authorize();
  }

  logOff() {
    this.oidcSecurityService.logoffAndRevokeTokens()
  }
}
