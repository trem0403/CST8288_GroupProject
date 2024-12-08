<%@page import="model.Course"%>
<%@page import="model.Term"%>
<%@page import="model.InstitutionName"%>

<%@page import="java.util.List"%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Course Search</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/course_search.css"> <!-- Link to external CSS -->
        <script src="${pageContext.request.contextPath}/JS/course_search.js" defer></script> <!-- Link to external JavaScript -->
    </head>
    <body>
        
        <%-- Include the header --%>
        <%@ include file="header.jsp" %>

        <h1>Search Courses</h1>

        <%-- Filter Section --%>
        <div id="filters">
            <label for="institutionFilter">Institution:</label>
            <select id="institutionFilter">
                <option value="">All</option>
                <%-- Populate institutions dynamically --%>
                <%
                    List<InstitutionName> institutionNames = (List<InstitutionName>) request.getAttribute("institutionNames");
                    if (institutionNames != null) {
                        for (InstitutionName institutionname : institutionNames) {
                %>
                <option value="<%= institutionname.getInstitutionNameID()%>"><%= institutionname.getName()%></option>
                <%
                        }
                    }
                %>
            </select>

            <label for="termFilter">Term:</label>
            <select id="termFilter">
                <option value="">All</option>
                <%-- Populate terms dynamically --%>
                <%
                    List<Term> terms = (List<Term>) request.getAttribute("terms");
                    if (terms != null) {
                        for (Term term : terms) {
                %>
                <option value="<%= term.getTermID()%>"><%= term.getTerm()%></option>
                <%
                        }
                    }
                %>
            </select>

            <label for="searchBar">Search:</label>
            <input type="text" id="searchBar" placeholder="Search by title or code">
            <button id="searchButton">Search</button>
        </div>

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
                <%-- Populate table dynamically using server-side logic --%>
                <%
                    List<Course> courses = (List<Course>) request.getAttribute("courses");
                    if (courses != null) {
                        for (Course course : courses) {
                %>
                <tr>
                    <td><%= course.getInstitutionID()%></td>
                    <td><%= course.getTitle()%></td>
                    <td><%= course.getCode()%></td>
                    <td><%= course.getTermID()%></td>
                    <td><%= course.getOutline()%></td>
                    <td><%= course.getSchedule()%></td>
                    <td><%= course.getDeliveryMethod()%></td>
                    <td><%= course.getPreferredQualifications()%></td>
                    <td><%= course.getCompensation()%></td>
                    <td>
                        <button class="request-to-teach" data-course-id="<%= course.getCourseID()%>">
                            Request to Teach
                        </button>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="10">No courses found.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
