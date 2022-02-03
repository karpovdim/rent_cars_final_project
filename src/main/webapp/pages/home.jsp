<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${sessionScope.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="local.pagecontent" />
<!DOCTYPE html>
<html>
<head>
        <%@ include file="/css/links_to_bootstrap.jsp"%>
        <title><fmt:message key="sign_in.title" /></title>
</head>
<body>
<%@ include file="/pages/part/navbar.jsp"%>
<c:if test="${is_authenticated == false}">


<div class="row justify-content-md-center">
    <div class="col col-lg-6 mt-3">
        <h3 class="text-center">
            <fmt:message key="sign_in.heading" />
        </h3>
<form action="${pageContext.request.contextPath}/controller"
      method="post">
    <input type="hidden" name="command" value="sign_in_page">
        <div class="mb-2">
    <label for="inputEmail1" class="form-label">input email</label>
    <input id="inputEmail1" type="email"
           class="form-control" name="email" required
           pattern="^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\.[a-zA-Z]{2,4}"/>
        </div>
        <div class="mb-4">
    <label for="inputPassword1" class="form-label">input password</label>
    <input id="inputPassword1" type="password"
           class="form-control" name="password" required
           pattern="^[^<>]{5,64}$"/>
        </div>
    <br/>

    <button type="submit" class="btn btn-primary">
        <fmt:message key="submit"/>
    </button>
        <c:if test="${authentication_error}">
            <p class="text-danger">
                <fmt:message key="sign_in.error.message"/>
            </p>
        </c:if>
        <br/> <br/>
        <fmt:message key="sign_in.registration_message"/>
    - <a href="${pageContext.request.contextPath}/controller?command=to_sign_up_page_command"
        class="link-primary">sign up
</a>
</form>
</div>
</div>

</c:if>

</body>
</html>