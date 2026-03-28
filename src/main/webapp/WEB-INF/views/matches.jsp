<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Matches</title>
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
        <h1>Matches</h1>

        <div class="input-container">
            <form method="get" action="${pageContext.request.contextPath}/matches" style="width:100%; display:flex;">
                <input class="input-filter"
                       name="filter_by_player_name"
                       value="${filter}"
                       placeholder="Filter by name"/>
                <button class="btn-filter" type="submit">Search</button>
            </form>
        </div>

        <c:if test="${empty matches}">
            <div style="margin-top: 20px;">No matches found</div>
        </c:if>

        <c:if test="${not empty matches}">
            <table class="table-matches">
                <tr>
                    <th>Player One</th>
                    <th>Player Two</th>
                    <th>Winner</th>
                </tr>

                <c:forEach var="m" items="${matches}">
                    <tr>
                        <td>${m.player1Name}</td>
                        <td>${m.player2Name}</td>
                        <td><span class="winner-name-td">${m.winnerName}</span></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <div style="margin-top: 20px;">

            <c:if test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/matches?page=${currentPage - 1}&filter_by_player_name=${filter}">
                    Previous
                </a>
            </c:if>

            <span style="margin: 0 10px;">
                Page ${currentPage}
            </span>

            <c:if test="${hasNextPage}">
                <a href="${pageContext.request.contextPath}/matches?page=${currentPage + 1}&filter_by_player_name=${filter}">
                    Next
                </a>
            </c:if>

        </div>

        <a href="${pageContext.request.contextPath}/new-match">
            <button class="form-button">New Match</button>
        </a>

    </div>
</main>

<footer>
    <div class="footer">
        <p>Tennis Scoreboard</p>
    </div>
</footer>

</body>
</html>