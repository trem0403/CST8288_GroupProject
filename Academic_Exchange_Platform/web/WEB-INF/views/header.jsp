<header id="header-app">
    <div class="header-container">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/index.jsp">
                <img src="${pageContext.request.contextPath}/images/academic_logo.png" alt="Academic Logo" class="logo-image" />
            </a>
            <span class="platform-title">Academic Exchange Platform</span>
        </div>

        <c:choose>
            <c:when test="${not empty sessionScope.userID}">
                <!-- Show logout button when user is logged in -->
                <div class="logout">
                    <a href="logout">Logout</a>
                </div>
            </c:when>
        </c:choose>
    </div>
</header>
