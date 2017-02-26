<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html lang="en">
	<head>
		<title>Create An Account</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	
	<body>
		<div>
			<h2>Create Your Account</h2>
			<form:form id="signupForm" method="POST" modelAttribute="account" role="form">
				
				<label>Email : </label>
				<div>
					<form:input path="username" type="text" placeholder="Username or Email"/>
					<form:errors path="username"></form:errors>
				</div><br>
				<label>First Name : </label>
				<div>
					<form:input path="firstName" type="text" placeholder="First Name"/>
					<form:errors path="firstName"></form:errors>
				</div><br>
				<label>Last Name : </label>
				<div>
					<form:input path="lastName" type="text" placeholder="Last Name"/>
					<form:errors path="lastName"></form:errors>
				</div><br>
				<label>Password : </label>
				<div>
					<form:input path="password" type="password" placeholder="Password"/>
					<form:errors path="password"></form:errors>
				</div><br>
				<label>Phone Number : </label>
				<div>
					<form:input path="phoneNumber" type="text" placeholder="Phone Number"/>
					<form:errors path="phoneNumber"></form:errors>
				</div><br>
				<div class="col-md-offset-3 col-md-9">
                   <button id="btn-signup" type="submit" class="btn btn-info"><i class="icon-hand-right"></i>Sign Up</button>
                   <!-- <span style="margin-left:8px;">or</span>   -->
                </div>
			</form:form>
		</div>
	</body>
</html>