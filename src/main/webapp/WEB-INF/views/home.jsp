<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
  <meta charset="UTF-8">
  <title>Home</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="header">
  <div class="brand">
    <span class="logo-text">TennisScoreboard</span>
  </div>
</header>

<main>
  <div class="container" style="text-align:center; margin-top:50px;">
    <h1>Welcome to Tennis Scoreboard</h1>

    <div style="margin-top:30px;">
      <a href="${pageContext.request.contextPath}/new-match">
        <button class="form-button">Start New Match</button>
      </a>
    </div>

    <div style="margin-top:20px;">
      <a href="${pageContext.request.contextPath}/matches">
        <button class="form-button">View Matches</button>
      </a>
    </div>
  </div>
</main>

</body>
</html>
