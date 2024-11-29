<%-- 
    Document   : academic_institution_registration
    Created on : Nov 28, 2024, 7:33:07 p.m.
    Author     : Ethan 
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Institution Registration Page</title>
    </head>
    <body>
        <div align="center">
            <h1>Academic Institution Registration Form</h1>
            <form action="<%= request.getContextPath()%>/institutionRegister" method="post">
                <table style="with: 80%">
                    <tr>
                        <td>Email:</td>
                        <td><input type="text" name="email" /></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="text" name="password" /></td>
                    </tr>
                    <tr>
                        <td>Institution Name</td>
                        <td>
                            <label for="institutionNamesList">Select Institution:</label>
                            <select name="institutionNameID" id="institutionName" style="width: 200px; height: 25px; font-size: 14px;">
                                <c:forEach var="institution" items="${institutionNamesList}">
                                    <option value="${institution.institutionNameID}">${institution.name}</option>
                                </c:forEach>
                                   
                            </select>
                        </td>
                    </tr>
                </table>
                <input type="submit" value="Submit" />
            </form>
        </div>
    </body>
</html>
