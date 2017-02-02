import {Component} from "@angular/core"

import { RegistrationComponent }  from './RegistrationComponent';
import { LoginComponent }  from './LoginComponent';

export const ApplicationRoutes = [
	{path:'login', component:LoginComponent},
	{path:'Registration', component:RegistrationComponent},

]
		