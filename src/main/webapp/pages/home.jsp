<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="pgn" uri="/WEB-INF/tld/custom.tld" %>
<c:set var="language" value="${sessionScope.locale}" scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/css/links_to_bootstrap.jsp" %>
    <title><fmt:message key="sign_in.title"/></title>
</head>
<body>
<%@ include file="/pages/part/navbar.jsp" %>
<c:if test="${is_authenticated == false}">


    <div class="row justify-content-md-center">
        <div class="col col-lg-6 mt-3">
            <h3 class="text-center">
                <fmt:message key="sign_in.heading"/>
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

<div class="row justify-content-md-center">
    <div class="col col-lg-6 mt-3">
        <table class="table">
            <tbody>
            <thead>
            <tr>
                <th scope="col"><fmt:message key="page.home.car" /></th>
                <th scope="col"><fmt:message key="page.home.transmission" /></th>
                <th scope="col"><fmt:message key="page.home.cost" /></th>
                <c:if test="${is_authenticated == true}">
                <th scope="col"><fmt:message key="page.home.order" /></th>
                </c:if>
            </tr>
            </thead>
            <c:forEach var="car" items="${cars}">
                <tr>
                    <td>${car.getCarDescription()}</td>
                    <td>
                        <c:if test="${car.getTransmissionType() == 'AUTOMATIC'}">
                            <span class="transmission text-success"><fmt:message
                                    key="home_page.transmission.automatic"/></span>
                        </c:if>

                        <c:if test="${car.getTransmissionType() == 'MANUAL'}">
                        <span class="transmission text-success"><fmt:message
                                key="home_page.transmission.manual"/></span>
                        </c:if>

                    </td>
                    <td>${car.getCost()}</td>
                    <td><c:if test="${car.isConditioner() == true}">
								<span class="conditioner text-success"><fmt:message
                                        key="home_page.conditioner"/></span>
                    </c:if></td>

                    <td align="left">
                        <form action="${pageContext.request.contextPath}/controller"
                              method="post">
                            <input type="hidden" name="command" value="sign_up_page">

                        </form>

                        <c:if test="${is_authenticated == true}">
                            <a
                                    href="${pageContext.request.contextPath}/controller?command=to_make_order_page_command&car_id=${car.getId()}"
                                    class="link-primary"><fmt:message key="home_page.order" />
                            </a>
                        </c:if></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<pgn:pagination currentPage="${current_page}"
                maxPage="${max_number_of_pages}" pageType='home'/>
</div>
</div>

<br/>
</body>
</html>