<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Pallette Commerce Login</title>
	</head>
	
	<body>
		<div class="container">
		
				<%-- form:form tag belongs to Spring MVC and helps in recognizing model and other attributes .
					 Model Attribute is used for assigning form beans that can be used for back-end validations.--%>
				
				<form:form id="loginfm" method="post" action="login" modelAttribute="loginForm" class="form-horizontal" role="form">
					<h2 class="form-heading">Log in to Pallette Commerce.</h2>
	
					<%--Path is the property name in the form bean. --%>
	                <form:input id="login-username" path="username" type="text" class="form-control" name="username" value="" placeholder="username or email"/>                                        
	                                
	                <form:input id="login-password" path="password" type="password" class="form-control" name="password" placeholder="password"/>
	                                    
	                <button type="submit" class="btn btn-default">Login</button>
	                            
				</form:form> 
		</div>
	</body>
</html>