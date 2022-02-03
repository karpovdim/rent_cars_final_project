<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="pgn" uri="pagination" %>
<c:set var="language" value="${sessionScope.locale}" scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="local.pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/css/links_to_bootstrap.jsp" %>
    <title><fmt:message key="page.admin_add_car.title"/></title>
</head>
<body>
<%@ include file="/pages/part/navbar.jsp" %>
<div class="row justify-content-md-center">
    <div class="col col-lg-6 mt-3">
        <h3 class="text-center">
            <fmt:message key="page.admin_add_car.heading"/>
        </h3>
        <form action="${pageContext.request.contextPath}/controller"
              method="post">
            <input type="hidden" name="command" value="admin_add_car_page">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" value="true"
                       name="conditioner" id="flexCheckDefault"> <label
                    class="form-check-label" for="flexCheckDefault"><fmt:message
                    key="page.admin_add_car.conditioner"/></label>
            </div>
            <br/>
            <label class="visually-hidden" for="autoSizingSelect">Preference</label>
            <fmt:message key="page.admin_add_car.transmission"/>
            <select class="form-select" name="transmission">
                <option selected><fmt:message
                        key="page.admin_add_car.choose_transmission"/></option>
                <option value="AUTOMATIC"><fmt:message
                        key="page.admin_add_car.automatic"/></option>
                <option value="MANUAL"><fmt:message
                        key="page.admin_add_car.manual"/></option>
            </select>
            <c:if test="${car_transmission_type_incorrect == true}">
                <p class="text-danger">
                    <fmt:message key="change.error"/>
                </p>
            </c:if>
            <div class="mb-2">
                <label for="inputCost" class="form-label"><fmt:message
                        key="page.admin_add_car.cost"/></label>
                <input type="text"
                       class="form-control" name="cost" required
                       pattern="^\d{1,5}$"/>
            </div>
            <c:if test="${car_cost_incorrect == true}">
                <p class="text-danger">
                    <fmt:message key="change.error"/>
                </p>
            </c:if>
            <div class="mb-2">
                <label for="inputRegistrationNumber" class="form-label"><fmt:message
                        key="page.admin_add_car.registration_number"/></label>
                <input type="text"
                       class="form-control"
                       placeholder="1212AA"
                       name="registration_number"
                       required pattern="^\d{4}\w{2}$"/>
            </div>
            <c:if test="${car_registration_number_incorrect == true}">
                <p class="text-danger">
                    <fmt:message key="change.error"/>
                </p>
            </c:if>
            <c:if test="${car_exists == true}">
                <p class="text-danger">
                    <fmt:message key="car.exists"/>
                </p>
            </c:if>
            <div class="mb-2">
                <label for="inputDescriptionCar" class="form-label"><fmt:message
                        key="page.admin_add_car.description_car"/></label>
                <input type="text"
                       class="form-control"
                       required pattern="^[^<>]{0,64}$"
                       name="car_description"/>
            </div>
            <c:if test="${car_description_incorrect == true}">
                <p class="text-danger">
                    <fmt:message key="change.error"/>
                </p>
            </c:if>
            <label class="visually-hidden" for="autoSizingSelect">Preference</label>
            <fmt:message key="page.admin_add_car.status"/>
            <select class="form-select" name="car_status">
                <option value="choose status"><fmt:message
                        key="page.admin.chang_status"/></option>
                <option value="BOOKED"><fmt:message
                        key="page.admin_cars.booked"/></option>
                <option value="FREE"><fmt:message
                        key="page.admin_cars.free"/></option>
                <option value="CAR_IS_SERVICED"><fmt:message
                        key="page.admin_cars.service"/></option>
                <option value="IMPOSSIBLE_TO_RENT"><fmt:message
                        key="page.admin_cars.impossible"/></option>
            </select><br/>
            <c:if test="${car_status_incorrect == true}">
                <p class="text-danger">
                    <fmt:message key="change.error"/>
                </p>
            </c:if>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="submit"/>
            </button>
        </form>
    </div>
</div>
</body>
</html>