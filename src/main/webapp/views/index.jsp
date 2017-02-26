<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Pallette Commerce Home Page</title>
	</head>
	
	<body>
		Welcome to Pallette Commerce 
		<%--The below piece of code checks if the user is anonymous/logged In. --%>
		
		<sec:authorize access="authenticated">
			, ${account.firstName}&nbsp;${account.lastName} !!
			<div class="container">
				 <ul>
					
			         	<li>
			         		<form:form id="logoutfm" method="post" action="logout"	modelAttribute="logoutForm" class="form-horizontal-logout" role="form">
								<h2 class="form-heading">Logout from Pallette Commerce.</h2>
								<button type="submit" class="btn btn-default">Logout</button>
							</form:form>
			         	</li>
			       
				</ul>
			</div>
		</sec:authorize>
	</body>
</html>