<%-- 
    Document   : professionalNotifications
    Created on : Dec. 8, 2024, 3:22:41 a.m.
    Author     : Sancheaz
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Professional Notifications</title>
</head>
<body>
    <h1>Your Notifications</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Request ID</th>
                <th>Course ID</th>
                <th>Status</th>
                <th>Notification Message</th>
                <th>Notification Date</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${notifications}" var="notification">
                <tr>
                    <td>${notification.requestToTeachID}</td>
                    <td>${notification.courseID}</td>
                    <td>${notification.status}</td>
                    <td>${notification.notificationMessage}</td>
                    <td>${notification.notificationDate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>