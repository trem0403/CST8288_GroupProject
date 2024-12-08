<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Course Search</title>
</head>
<body>
    <h1>Search for Courses</h1>

    <form action="AcademicInstitutionServlet?action=searchCourses" method="get">
        <div class="form-group">
            <label for="title">Course Title:</label>
            <input type="text" id="title" name="title" placeholder="Enter course title" value="${title}" required>
        </div>

        <div class="form-group">
            <label for="code">Course Code:</label>
            <input type="text" id="code" name="code" placeholder="Enter course code" value="${code}">
        </div>

        <div class="form-group">
            <label for="termID">Term:</label>
            <select id="termID" name="termID" required>
                <c:forEach var="term" items="${termList}">
                    <option value="${term.id}" <c:if test="${term.id == termID}">selected</c:if>>${term.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="institutionID">Institution:</label>
            <select id="institutionID" name="institutionID" required>
                <c:forEach var="institution" items="${institutionList}">
                    <option value="${institution.institutionID}" <c:if test="${institution.institutionID == institutionID}">selected</c:if>>${institution.name}</option>
                </c:forEach>
            </select>
        </div>

        <button type="submit">Search</button>
    </form>
</body>
</html>
