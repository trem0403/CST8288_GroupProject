<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Course Search Results</title>
</head>
<body>
    <h1>Search Results</h1>

    <!-- Display success or error messages -->
    <c:if test="${not empty message}">
        <div class="success-message">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <!-- Back to search form link -->
    <a href="AcademicInstitutionServlet?action=searchCourses">Back to Search</a>

    <c:if test="${not empty courses}">
        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Code</th>
                    <th>Term</th>
                    <th>Institution</th>
                    <th>Delivery Method</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="course" items="${courses}">
                    <tr>
                        <td>${course.title}</td>
                        <td>${course.code}</td>
                        <td>${course.termID}</td>
                        <td>${course.institutionID}</td>
                        <td>${course.deliveryMethod}</td>
                        <td>
                            <form action="AcademicInstitutionServlet?action=requestToTeach" method="post">
                                <input type="hidden" name="courseID" value="${course.courseID}">
                                <button type="submit">Request to Teach</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty courses}">
        <p>No courses found matching your criteria.</p>
    </c:if>
</body>
</html>
