import {Component} from "@angular/core"
import { Http, Headers, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Router, ActivatedRoute } from '@angular/router';
import 'rxjs/add/operator/map'

@Component({
	templateUrl: "/resources/ui/login.jsp"
})

export class LoginComponent{
	 loginform: any = {};
	 loading:boolean = false ;
	 returnUrl: string;

	 constructor(private http: Http, private router:Router){
	 	this.loginform.username="vagish"
	 }
	 login(){

	 	this.loading = false ;
	 	 return this.http.post('/api/authenticate', JSON.stringify({ username: this.loginform.username, password: this.loginform.password }))
            .map(res => res.json())
            .subscribe(
		    (data) => {
		    	console.log('in');
		    	this.router.navigateByUrl('/Home');
		    },
		    (err) => {
		    	//console.log('error');
		    	
				
		    }

		    ); 
	 }
}