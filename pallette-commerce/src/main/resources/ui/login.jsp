<!-- Login Page style -->
	<div class = "container">
		<div class="wrapper">
			<form action="" method="post" name="Login_Form" class="form-signin" (ngSubmit)="login()">       
			    <h3 class="form-signin-heading">Welcome !</h3>
				  <hr class="colorgraph"><br>
				  
				  <input type="text" class="form-control" name="Username" [(ngModel)]= "loginform.username" placeholder="Username" required="" autofocus="" />
				  <input type="password" class="form-control" name="Password" [(ngModel)]= "loginform.password" placeholder="Password" required=""/>     		  
				 
				  <button class="btn btn-lg btn-primary btn-block"  name="Submit" value="Login" >Login</button>

			</form>	


		</div>
	</div>
