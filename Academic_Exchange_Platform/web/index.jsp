
<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Academic Exchange Platform</title>

        <!-- Vue.js CDN -->
        <script src="https://cdn.jsdelivr.net/npm/vue@3.2.37/dist/vue.global.js"></script>

        <!-- CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/index.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/header.css">
    </head>

    <%@ include file="WEB-INF/views/header.jsp" %> 

    <body>
        <div id="app" class="main-container">
            <h1 class="welcome-message">Welcome to the Academic Exchange Platform</h1>

            <div class="registration-sections">
                <div class="registration-card" @mouseover="hoverMessage('Institution')" @mouseout="hoverMessage('')">
                    <h2>Academic Institution</h2>
                    <p>Join as an academic institution and connect with professionals worldwide.</p>
                    <a href="${pageContext.request.contextPath}/institutionRegister" class="btn">Register</a>
                </div>

                <div class="registration-card" @mouseover="hoverMessage('Professional')" @mouseout="hoverMessage('')">
                    <h2>Academic Professional</h2>
                    <p>Register to collaborate with institutions and share your expertise.</p>
                    <a href="${pageContext.request.contextPath}/professionalRegister" class="btn">Register</a>
                </div>
            </div>

            <div class="login-section">
                <p>Or log in here:</p>
                <a href="${pageContext.request.contextPath}/login" class="btn login-btn">Login</a>
            </div>
        </div>

        <!-- Vue.js App Initialization -->
        <script>
        const app = Vue.createApp({
        });
        app.mount('#app');
        </script>

    </body>
</html>
