<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Home</title>
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
  <div class="container" style="text-align:center; margin-top:40px;">
    <h1>Welcome to Tennis Scoreboard</h1>

    <!-- ✅ ТОЛЬКО CSS -->
    <div class="welcome-image"></div>

    <div style="margin-top:30px;">
      <a href="${pageContext.request.contextPath}/new-match">
        <button class="form-button">Start New Match</button>
      </a>
    </div>

    <div style="margin-top:15px;">
      <a href="${pageContext.request.contextPath}/matches">
        <button class="form-button">View Matches</button>
      </a>
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