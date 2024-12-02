<%@page import="model.AcademicProfessional"%>
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
        AcademicProfessional academicProfessional = (AcademicProfessional) request.getAttribute("academicProfessional");
    %>

    <form action="<%= request.getContextPath() %>/professionalProfile" method="post">
        Name: <input type="text" name="name" value="<%= academicProfessional != null ? academicProfessional.getName() : "" %>" required><br>
        Current Position: <input type="text" name="currentPositionAtInstitution" value="<%= academicProfessional != null ? academicProfessional.getCurrentPositionAtInstitution() : "" %>" required><br>
        Education Background: 
        <textarea name="educationBackground" required><%= academicProfessional != null ? academicProfessional.getEducationBackground() : "" %></textarea><br>
        Area of Expertise: <input type="text" name="areaOfExpertise" value="<%= academicProfessional != null ? academicProfessional.getAreaOfExpertise() : "" %>" required><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
