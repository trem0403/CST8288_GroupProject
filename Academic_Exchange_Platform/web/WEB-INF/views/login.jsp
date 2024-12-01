<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login</title>

        <!-- Include Vue.js from CDN -->
        <script src="https://cdn.jsdelivr.net/npm/vue@3.2.37/dist/vue.global.js"></script>

        <!-- CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/academic_institution_registration.css" />  
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/header.css" />

        <!-- JS -->
    </head>

    <%@ include file="header.jsp"%>

    <body class="dark-mode">

        <div id="app">
            <div class="form-container">
                <div class="form-card">
                    <h1>Login</h1>
                    <form action="<%= request.getContextPath()%>/login"  method="post">

                        <div id="login-error" class="error-message">
                            <%= request.getAttribute("login-error") != null ? request.getAttribute("login-error") : ""%>
                        </div>

                        <input type="text" name="email" placeholder="Email" 
                               value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : ""%>" required />


                        <input type="password" name="password" placeholder="Password" 
                               value="<%= request.getAttribute("password") != null ? request.getAttribute("password") : ""%>" required />

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