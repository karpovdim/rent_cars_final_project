<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language" value="${sessionScope.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="local.pagecontent" />
<!DOCTYPE html>
<html>
<head>
<%@ include file="/css/links_to_bootstrap.jsp"%>
<title><fmt:message key="page.make_order.titel" /></title>
</head>
<body>
	<%@ include file="/pages/part/navbar.jsp"%>
	<h3 class="text-center">
		<fmt:message key="page.make_order.heading" />
	</h3>
	<div class="container">
		<div class="row">
			<div class="col">

<%--				<img src="${pageContext.request.contextPath}${car.getImageUrl()}"--%>
<%--					alt="">--%>

				<h6>${car.getCarDescription()}</h6>

<%--				<span class="year text-success">${car.getYear()}</span>--%>

				<c:if test="${car.getTransmissionType() == 'AUTOMATIC'}">
					<span class="transmission text-success"><fmt:message
							key="home_page.transmission.automatic" /></span>
				</c:if>
				<c:if test="${car.getTransmissionType() == 'MANUAL'}">
					<span class="transmission text-success"><fmt:message
							key="home_page.transmission.manual" /></span>
				</c:if>

				<c:if test="${car.isConditioner() == true}">
					<span class="conditioner text-success"><fmt:message
							key="home_page.conditioner" /></span>
				</c:if>
			</div>
			<div class="col">
				<br />
				<h5>
					<fmt:message key="page.make_order.price" />
					: ${car.getCost()}
				</h5>
				<form action="${pageContext.request.contextPath}/controller"
					method="post">
					<input type="hidden" name="command" value="make_order_page">
					<div class="form-group mt-3">
						<label for="inputDate"><fmt:message
								key="page.make_order.pick_up_date" /></label> <input type="date"
							class="form-control" name="pick_up_date">
					</div>
					<div class="form-group mt-3">
						<label for="inputDate"><fmt:message
								key="page.make_order.return_date" /></label> <input type="date"
							class="form-control" name="return_date">
					</div>
					<c:if test="${booked}">
						<p class="text-danger">
							<fmt:message key="page.make_order.car_booked" />
						</p>
					</c:if>
					<c:if test="${incorrect_date}">
						<p class="text-danger">
							<fmt:message key="page.make_order.incorrect_date" />
						</p>
					</c:if>
					<c:if test="${pick_up_date_is_before_return_date}">
						<p class="text-danger">
							<fmt:message
								key="page.make_order.pick_up_date_is_before_return_date" />
						</p>
					</c:if>
					<br />
					<button type="submit" class="btn btn-primary">
						<fmt:message key="submit" />
					</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>