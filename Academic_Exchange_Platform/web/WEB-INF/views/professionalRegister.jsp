<%-- 
    Document   : professional-profile-form
    Created on : Dec. 1, 2024, 11:41:11 p.m.
    Author     : Sancheaz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Complete Profile</title>
</head>
<body>
    <h2>Complete Your Profile</h2>
    <form action="profile" method="post">
        Name: <input type="text" name="name" required><br>
        Current Position: <input type="text" name="currentPosition" required><br>
        Current Institution: <input type="text" name="currentInstitution" required><br>
        Education Background: <textarea name="educationBackground" required></textarea><br>
        Area of Expertise: <input type="text" name="areaOfExpertise" required><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
