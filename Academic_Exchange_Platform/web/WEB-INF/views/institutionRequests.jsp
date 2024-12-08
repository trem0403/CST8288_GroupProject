<%-- 
    Document   : institutionRequests
    Created on : Dec. 8, 2024, 3:20:20 a.m.
    Author     : Sancheaz
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Institution Requests</title>
</head>
<body>
    <h1>Current Requests to Teach a Course</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Request ID</th>
                <th>Professional ID</th>
                <th>Course ID</th>
                <th>Request Date</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${requests}" var="request">
                <tr>
                    <td>${request.requestToTeachID}</td>
                    <td>${request.professionalID}</td>
                    <td>${request.courseID}</td>
                    <td>${request.requestDate}</td>
                    <td>${request.status}</td>
                    <td>
                        <form action="requests" method="post">
                            <input type="hidden" name="requestID" value="${request.requestToTeachID}">
                            <select name="status">
                                <option value="Accepted">Accept</option>
                                <option value="Rejected">Reject</option>
                            </select>
                            <button type="submit">Update Status</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>