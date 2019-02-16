<%@page import="business.User"%>
<!DOCTYPE html>
<!--html home page for website-->
<html lang="en" dir="ltr">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Retro Video Game Swap</title>
  <link rel="stylesheet" href="swapStyle.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>

<body>
  <!--Site banner-->
  <header>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <div class="banner">
      <%User user = (User) session.getAttribute("theUser");%>
      <c:if test="${theUser == null}">
        <p>Not Signed in</p>
      </c:if>
      <c:if test="${theUser != null}">
        <p>${theUser.firstName}</p>
      </c:if>
      <h1>
        <img src="images/gameController.png" alt="logo">Retro Video Game Swap
      </h1>
    </div>
    <div class="userNav">
      <c:if test="${theUser == null}">
        <a href="login.jsp">Sign in</a>
      </c:if>
      <c:if test="${theUser != null}">
        <a href="ProfileController?action=signout">Sign out</a>
      </c:if>
      <a href="ProfileController">My Games</a>
      <a href="mySwaps.jsp">My Swaps</a>
      <a href="#">Cart</a>
    </div>
  </header>
