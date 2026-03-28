<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Match Score</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="header">
    <section class="nav-header">
        <div class="brand">
            <img src="${pageContext.request.contextPath}/images/menu.png" class="logo">
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
        <h1>Current match</h1>

        <div class="current-match-image"></div>

        <!-- ✅ Показываем режим -->
        <c:if test="${match.score.tieBreak}">
            <div style="color: orange; font-weight: bold; margin-bottom: 10px;">
                Tie-break
            </div>
        </c:if>

        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <th class="table-text">Games</th>
                    <th class="table-text">Points</th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <tr class="player1">
                    <td>${match.player1.name}</td>
                    <td>${match.score.player1Sets}</td>
                    <td>${match.score.player1Games}</td>
                    <td>
                        <c:choose>
                            <c:when test="${match.score.tieBreak}">
                                ${match.score.player1TiePoints}
                            </c:when>
                            <c:otherwise>
                                ${match.score.player1Points}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form method="post">
                            <button class="score-btn" name="player" value="1">Score</button>
                        </form>
                    </td>
                </tr>

                <tr class="player2">
                    <td>${match.player2.name}</td>
                    <td>${match.score.player2Sets}</td>
                    <td>${match.score.player2Games}</td>
                    <td>
                        <c:choose>
                            <c:when test="${match.score.tieBreak}">
                                ${match.score.player2TiePoints}
                            </c:when>
                            <c:otherwise>
                                ${match.score.player2Points}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <form method="post">
                            <button class="score-btn" name="player" value="2">Score</button>
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
    </div>
</main>

<footer>
    <div class="footer">
        <p>Tennis Scoreboard</p>
    </div>
</footer>

</body>
</html>