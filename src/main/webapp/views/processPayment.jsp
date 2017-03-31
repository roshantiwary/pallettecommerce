<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Processing Payment</title>
</head>
<body>
	 <form id="payuform" action="${action}" name="payuform" method="POST">
		<input type="hidden" name="key" value="${key}"/>
		<input type="hidden" name="hash" value="${hash}"/>
		<input type="hidden" name="txnid" value="${txnid}"/>
		<input type="hidden" name="amount" value="${amount}"/>
		<input type="hidden" name="firstname" value="${firstname}"/>
		<input type="hidden" name="email" value="${email}"/>
		<input type="hidden" name="phone" value="${phone}"/>
		<input type="hidden" name="productinfo" value="${productinfo}"/>
		<input type="hidden" name="surl" value="${surl}"/>
		<input type="hidden" name="furl" value="${furl}"/>
		<input type="hidden" name="service_provider" value="${service_provider}"/>
		<input type="hidden" name="lastname" value="${lastname}"/>
		<input type="hidden" name="curl" value="${curl}"/>
		<input type="hidden" name="address1" value="${address1}"/>
		<input type="hidden" name="address2" value="${address2}"/>
		<input type="hidden" name="city" value="${city}"/>
		<input type="hidden" name="state" value="${state}"/>
		<input type="hidden" name="country" value="${country}"/>
		<input type="hidden" name="zipcode" value="${zipcode}"/>
		<input type="hidden" name="udf1" value="${udf1}"/>
		<input type="hidden" name="udf2" value="${udf2}"/>
		<input type="hidden" name="hashString" value="${hashString}"/>
		<input type="hidden" name="udf3" value="${udf3}"/>
		<input type="hidden" name="udf4" value="${udf4}"/>
		<input type="hidden" name="udf5" value="${udf5}"/>
		<input type="hidden" name="pg" value="${pg}"/>
		<input type="submit" value="submit" style="display:none;"/>	 
	 </form>
	 
	 <script type="text/javascript">
	 	document.getElementById("payuform").submit();
	 </script>

</body>
</html>