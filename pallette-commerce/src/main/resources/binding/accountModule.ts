import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {LocationStrategy, HashLocationStrategy} from '@angular/common';
import {RouterModule} from '@angular/router';
import { FormsModule } from "@angular/forms"; 

import { LoginComponent } from "./LoginComponent"; 

import { RegistrationComponent } from "./RegistrationComponent"; 
import { ApplicationRoutes } from "./AccountRoutingComponent"; 
import { AccountSpaComponent } from "./AccountSpaComponent"; 
import { NavComponent } from "./NavComponent"; 
import { HomeComponent } from "./HomeComponent"; 

import { HttpModule } from '@angular/http';





@NgModule({
  imports:      [ BrowserModule , FormsModule,  RouterModule.forRoot(ApplicationRoutes), HttpModule],
  declarations: [LoginComponent, RegistrationComponent, AccountSpaComponent, NavComponent, HomeComponent],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}] ,
  bootstrap:    [AccountSpaComponent, NavComponent]
})
export class AccountModule {}
