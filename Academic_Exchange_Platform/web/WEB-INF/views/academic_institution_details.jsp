<%@page import="model.AcademicInstitution"%>
<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<%-- 
    Document   : academic_professional_details
    Created on : Dec. 1, 2024, 11:41:11 p.m.
    Author     : Sancheaz
--%>
<!DOCTYPE html>
<html>
<head>
    <title>Complete Profile</title>
</head>
<body>
    <h2>Complete Your Profile</h2>

    <%
        AcademicInstitution academicInstitution = (AcademicInstitution) request.getAttribute("academicProfessional");
    %>

    <form action="<%= request.getContextPath() %>/professionalProfile" method="post">
        Name: <input type="text" name="name" value="<%= AcademicInstitution != null ? AcademicInstitution.getName() : "" %>" required><br>
        Current Position: <input type="text" name="currentPositionAtInstitution" value="<%= AcademicInstitution != null ? AcademicInstitution.getCurrentPositionAtInstitution() : "" %>" required><br>
        Education Background: 
        <textarea name="educationBackground" required><%= academicProfessional != null ? academicProfessional.getEducationBackground() : "" %></textarea><br>
        Area of Expertise: <input type="text" name="areaOfExpertise" value="<%= AcademicInstitution != null ? AcademicInstitution.getAreaOfExpertise() : "" %>" required><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
