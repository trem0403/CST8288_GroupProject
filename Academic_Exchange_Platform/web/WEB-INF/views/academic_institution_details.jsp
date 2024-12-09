<%@page import="model.AcademicInstitution"%>
<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%-- 
    Document   : academic_institution_details
    Created on : Dec. 1, 2024, 11:41:11 p.m.
    Author     : Sancheaz + Ethan
--%>
<!DOCTYPE html>
<html>
    <head>
        <title>Complete Profile</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/header.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/academic_institution_details.css" />
    </head>
    <body>
        <%@ include file="header.jsp"%>

        <h2>Complete Your Institution Profile</h2>


        <%
            AcademicInstitution academicInstitution = (AcademicInstitution) request.getAttribute("academicInstitution");
        %>

        <form action="<%= request.getContextPath()%>/institutionProfile" method="post">
            Zip: <input type="text" name="zip" value="<%= academicInstitution != null ? academicInstitution.getZip() : ""%>" required><br>
            <input type="submit" value="Submit">
        </form>

    </body>
</html>
