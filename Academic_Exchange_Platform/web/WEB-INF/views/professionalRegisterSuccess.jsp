<%-- 
    Document   : professionalRegisterSuccess
    Created on : Dec. 2, 2024, 00:33:17 a.m.
    Author     : Sancheaz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Profile Completed</title>
    </head>
    <body>
    	<nav>
		    <div class="navbar">
		
		        <ul class="navbar-links">
		            
		            <c:choose>
		                <c:when test="${not empty sessionScope.userID}">
		                    <!-- Link to logout users -->
		                    <li><a href="logout">Logout</a></li>
		                </c:when>
		                
		            </c:choose>
		        </ul>
		    </div>
		</nav>
        <h2>Profile Completion Successful!</h2>
    </body>
</html>