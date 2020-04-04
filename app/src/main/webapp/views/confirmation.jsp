<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Order Confirmation</title>
</head>
<body>
	<h1>Your Order ${orderId} was successfully Placed.</h1>
	<h2>Order Total is :: ${amount}</h2>

	<h2>Shipping Details:</h2>
	<br> ${firstname}
	<br> ${email}
	<br> ${phone}
	<br> ${lastname}
	<br> ${address1}
	<br> ${address2}
	<br> ${city}
	<br> ${state}
	<br> ${country}
	<br> ${zipcode}
	<br>

	<h1>Find below the items.</h1>
	<c:forEach items="${itemList}" var="commerceItem">
		 Title :: <p>${commerceItem.productTitle}</p>
		<br />
		Image :: <p>${commerceItem.productImage}</p>
		<br />
		Description ::	<p>${commerceItem.description}</p>
		<br />
		SkuId :: <p>${commerceItem.catalogRefId}</p>
		<br />
		Product Id :: <p>${commerceItem.productId}</p>
		<br />
		Quantity ::	<p>${commerceItem.quantity}</p>
		<br />
		Amount :: <p>${commerceItem.amount}</p>
		<br />
	</c:forEach>


</body>
</html>