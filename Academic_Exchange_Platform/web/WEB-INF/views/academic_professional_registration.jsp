<%@page import="model.InstitutionName"%>
<%@page import="model.AcademicInstitution"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Academic Institution Registration</title>

        <!-- Include Vue.js from CDN -->
        <script src="https://cdn.jsdelivr.net/npm/vue@3.2.37/dist/vue.global.js"></script>

        <!-- CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/academic_institution_registration.css" />  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/header.css" />

        <!-- JS -->
        <script src="${pageContext.request.contextPath}/JS/academic_professional_registration_validation.js" defer></script>
    </head>

    <%@ include file="header.jsp"%>

    <body class="dark-mode">

        <div id="app">
            <div class="form-container">
                <div class="form-card">
                    <h1>Academic Professional Registration</h1>
                    <form action="<%= request.getContextPath()%>/professionalRegister" method="post" onsubmit="validateForm(event)">

                        <input type="text" name="name" placeholder="Name" 
                               value="<%= request.getAttribute("name") != null ? request.getAttribute("name") : ""%>" required />
                        <div id="name-error" class="error-message">
                            <%= request.getAttribute("name-error") != null ? request.getAttribute("name-error") : ""%>
                        </div>

                        <input type="text" name="email" placeholder="Email" 
                               value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : ""%>" required />
                        <div id="email-error" class="error-message">
                            <%= request.getAttribute("email-error") != null ? request.getAttribute("email-error") : ""%>
                        </div>

                        <input type="password" name="password" placeholder="Password" 
                               value="<%= request.getAttribute("password") != null ? request.getAttribute("password") : ""%>" required />
                        <div id="password-error" class="error-message">
                            <%= request.getAttribute("password-error") != null ? request.getAttribute("password-error") : ""%>
                        </div>

                        <input type="text" name="academicPosition" placeholder="Academic Position" 
                               value="<%= request.getAttribute("academicPosition") != null ? request.getAttribute("academicPosition") : ""%>" required />
                        <div id="academicPosition-error" class="error-message">
                            <%= request.getAttribute("academicPosition-error") != null ? request.getAttribute("academicPosition-error") : ""%>
                        </div>


                        <select name="institutionNameID">
                            <%
                                List<InstitutionName> institutions = (List<InstitutionName>) request.getAttribute("institutionNameList");
                                for (InstitutionName institution : institutions) {
                            %>
                            <option value="<%= institution.getInstitutionNameID()%>">
                                <%= institution.getName()%>
                            </option>
                            <% }%>
                        </select>

                        <input type="submit" value="Register" />
                    </form>
                </div>
            </div>
        </div>

        <script>
            const app = Vue.createApp({
            });

            app.mount("#app");
        </script>

    </body>
</html>