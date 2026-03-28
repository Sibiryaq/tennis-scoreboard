<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>New Match</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="header">
    <section class="nav-header">
        <div class="brand">
            <img src="${pageContext.request.contextPath}/images/menu.png" class="logo" alt="">
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <nav class="nav-links">
            <a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a>
        </nav>
    </section>
</header>

<main>
    <div class="container">
        <h1>Start new match</h1>

        <div class="new-match-image"></div>

        <div class="form-container center">

            <c:if test="${not empty error}">
                <div style="color:red; margin-bottom: 10px;">
                        ${error}
                </div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/new-match">

                <label class="label-player" for="player1">Player one</label>
                <input id="player1"
                       class="input-player"
                       name="player1"
                       value="${player1}"
                       placeholder="Name"
                       required>

                <label class="label-player" for="player2">Player two</label>
                <input id="player2"
                       class="input-player"
                       name="player2"
                       value="${player2}"
                       placeholder="Name"
                       required>

                <input class="form-button" type="submit" value="Start">
            </form>

        </div>
    </div>
</main>

<footer>
    <div class="footer">
        <p>Tennis Scoreboard</p>
    </div>
</footer>

</body>
</html>