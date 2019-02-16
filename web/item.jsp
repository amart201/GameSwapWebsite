<%-- 
   Author     : AaronMartin
--%>

<%@page import="business.User"%>
<%@page import="swaps.Game"%>
<jsp:include page="header.jsp" />
<jsp:include page="site-navigation.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% User user = (User) session.getAttribute("theUser"); %>
<%Game gameItem = (Game) request.getAttribute("gameItem");%>
  <!--bread crumb-->
  <p class="breadcrumb">home &gt; categories &gt; item</p>
  <main id="itempage">
    <!--image and info on the item-->
    <img src=<c:out value="${gameItem.imageURL}"></c:out>>
    <div id="iteminfo">
        <h3><c:out value='${gameItem.name}'></c:out></h3>
        <h4>for <c:out value='${gameItem.console}'></c:out></h4>
        <h4>Rating: <c:out value='${gameItem.rating}'></c:out>/5</h4>
    <c:if test="${theUser != null}">
      <!--swap button-->
      <form action="ProfileController">
          <input type="hidden" name="itemList" value="<c:out value="${gameItem.code}"></c:out>">
          <input type="hidden" name="theItem" value="<c:out value="${gameItem.code}"></c:out>">
        <button type="submit" name="action" value="offer"><img src="images/swapicon.jpg" alt="swap icon">Swap it</button>
      </form>
      <!--rate button-->
      <form action="ProfileController" method="post">
        <button type="submit" name="action" value="rateitem"><img src="images/staricon.png" alt="star icon">Rate it</button>
        <input type="hidden" name="gameItem" value="${gameItem.code}">
        <input type="radio" name="rating" value="1">1
        <input type="radio" name="rating" value="2">2
        <input type="radio" name="rating" value="3">3
        <input type="radio" name="rating" value="4">4
        <input type="radio" name="rating" value="5">5
      </form>
    </c:if>
    </div>

              <p><c:out value="${gameItem.description}"></c:out></p>
          <h1><strong><c:out value="${message}"></c:out></strong></h1>
    <a href="CatalogController">&lt;Back to Categories Page</a>
  </main>
  <jsp:include page="footer.jsp" />
