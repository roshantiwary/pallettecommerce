import {Component} from "@angular/core"

import { RegistrationComponent }  from './RegistrationComponent';
import { LoginComponent }  from './LoginComponent';
import { HomeComponent }  from './HomeComponent';

export const ApplicationRoutes = [
	{path:'login', component:LoginComponent},
	{path:'Registration', component:RegistrationComponent},
	{path:'Home', component:HomeComponent},

]
		