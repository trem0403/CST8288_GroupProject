<%@page import="com.google.gson.Gson"%>
<%@page import="java.util.Map"%>
<%@page import="model.Course"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
    <head>
        <title>Course Search</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/course_search.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/header.css">

    </head>
    <body>

        <%@ include file="header.jsp" %>


        <!-- Create list of all courses -->
        <%
            List<Course> courses = (List<Course>) request.getAttribute("courses");
        %>

        <h1>Search Courses</h1>

        <form id="courseForm">
            <%-- Institution Filter --%>
            <div class="filter-section">
                <label for="institutionFilter">Institution:</label>
                <select id="institutionFilter">
                    <option value="all">All</option>
                    <%
                        Map<Integer, String> institutionMap = (Map<Integer, String>) request.getAttribute("institutionMap");
                        for (Map.Entry<Integer, String> entry : institutionMap.entrySet()) {
                    %>
                    <option value="<%= entry.getKey()%>">
                        <%= entry.getValue()%>
                    </option>
                    <% } %>
                </select>
            </div>

            <%-- Term Filter --%>
            <div class="filter-section">
                <label for="termFilter">Term:</label>
                <select id="termFilter">
                    <option value="all">All</option>
                    <%
                        Map<Integer, String> termMap = (Map<Integer, String>) request.getAttribute("termMap");
                        for (Map.Entry<Integer, String> entry : termMap.entrySet()) {
                    %>
                    <option value="<%= entry.getKey()%>">
                        <%= entry.getValue()%>
                    </option>
                    <% }%>
                </select>
            </div>

            <%-- Search Bar --%>
            <input type="text" id="searchInput" placeholder="Search for course titles...">
        </form>

        <script>
            const courses = <%= new Gson().toJson(courses)%>; // Serialize Java list to JSON
            const institutionMap = <%= new Gson().toJson(institutionMap)%>;
            const termMap = <%= new Gson().toJson(termMap)%>;
        </script>

        <script>
            // Function to handle the "Request to Teach" button click
            function requestToTeach(courseID) {
                const form = document.getElementById('courseForm');
                const hiddenInput = document.createElement('input');
                hiddenInput.type = 'hidden';
                hiddenInput.name = 'courseID';  // Name to match parameter in the servlet
                hiddenInput.value = courseID;   // Set the courseID value

                // Create the action input field
                const actionInput = document.createElement('input');
                actionInput.type = 'hidden';
                actionInput.name = 'action';  // Action name
                actionInput.value = 'requestToTeach';  // Action to trigger requestToTeach in the servlet

                form.appendChild(hiddenInput); // Add courseID to the form
                form.appendChild(actionInput); // Add action to the form

                // Submit the form to trigger the POST method
                form.submit();
            }
        </script>

        <%-- Course Table --%>
        <table id="courseTable">
            <thead>
                <tr>
                    <th>Institution</th>
                    <th>Title</th>
                    <th>Code</th>
                    <th>Term</th>
                    <th>Outline</th>
                    <th>Schedule</th>
                    <th>Delivery Method</th>
                    <th>Preferred Qualifications</th>
                    <th>Compensation</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>

            </tbody>
        </table>

        <script src="${pageContext.request.contextPath}/JS/course_search.js"></script>

    </body>
</html>
