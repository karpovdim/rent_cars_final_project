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
<title><fmt:message key="page.personal_profile.title" /></title>
</head>
<body>
	<%@ include file="/pages/part/navbar.jsp"%>
	<div class="row justify-content-md-center">
		<div class="col col-lg-6 mt-3">
			<table class="table">
				<tbody>
					<tr>
						<th scope="row">${user.getFirstName()}</th>
						<td><a
							href="${pageContext.request.contextPath}/controller?command=to_change_first_name_page_command"
							class="link-primary"><fmt:message
									key="page.personal_profile.change" /> </a></td>
					</tr>
					<tr>
						<th scope="row">${user.getLastName()}</th>
						<td><a
							href="${pageContext.request.contextPath}/controller?command=to_change_last_name_page_command"
							class="link-primary"><fmt:message
									key="page.personal_profile.change" /> </a></td>
					</tr>
					<tr>
						<th scope="row">+375${user.getPhoneNumber()}</th>
						<td><a
							href="${pageContext.request.contextPath}/controller?command=to_change_phone_number_page_command"
							class="link-primary"><fmt:message
									key="page.personal_profile.change" /> </a></td>
					</tr>
					<tr>
						<th scope="row">${user. getEmailLogin()}</th>
						<td><a
							href="${pageContext.request.contextPath}/controller?command=to_change_email_page_command"
							class="link-primary"><fmt:message
									key="page.personal_profile.change" /> </a></td>
					</tr>
<%--					<tr>--%>
<%--						<th scope="row"><fmt:message--%>
<%--								key="page.personal_profile.day_of_birth" /></th>--%>
<%--						<td>${user.getDateOfBirth()}</td>--%>
<%--					</tr>--%>
<%--					<tr>--%>
<%--						<th scope="row"><fmt:message--%>
<%--								key="page.personal_profile.discount" /></th>--%>
<%--						<td>${user.getDiscount()}</td>--%>
<%--					</tr>--%>
					<c:if test="${is_authenticated && user.getRole() == 'ADMIN'}">
						<tr>
							<th scope="row"><fmt:message
									key="page.personal_profile.role" /></th>
							<td>${user.getRole()}</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<a
				href="${pageContext.request.contextPath}/controller?command=to_change_password_page_command"
				class="link-primary"><fmt:message
					key="page.personal_profile.password" /> </a>
		</div>
	</div>
</body>
</html>