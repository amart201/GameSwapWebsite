<%-- 
   Author     : AaronMartin
--%>

<%@page import="swaps.Game"%>
<%@page import="java.util.ArrayList"%>
<jsp:include page="header.jsp" />
<jsp:include page="site-navigation.jsp" />
<%ArrayList<Game> gameList = (ArrayList) request.getAttribute("gameList"); %>
<%Game game;%>
    <!--bread crumb-->
    <p class="breadcrumb">home &gt; categories</p>
  <main>
    <!--catoragized by console and game-->
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <h2>Super Nintendo</h2>
        <ul>
                <c:forEach items="${gameList}" var="gameItem">
                    <c:if test="${gameItem.console == 'SNES'}">
                        <li><a href="CatalogController?itemCode=<c:out value="${gameItem.code}"></c:out>"><c:out value="${gameItem.name}"></c:out></a></li>
                    </c:if>
                </c:forEach>
        </ul>
        <h2>Sega Genesis</h2>
        <ul>
            <c:forEach items="${gameList}" var="gameItem">
                    <c:if test="${gameItem.console == 'Sega Genesis'}">
                        <li><a href="CatalogController?itemCode=<c:out value="${gameItem.code}"></c:out>"><c:out value="${gameItem.name}"></c:out></a></li>
                    </c:if>
                </c:forEach>
        </ul>
    <!--<h2>Super Nintendo</h2>
      <ul>
        <li><a href="item.jsp">Super Mario World</a></li>
        <li><a href="item.jsp">Zelda: A Link to the Past</a></li>
        <li><a href="item.jsp">Super Metroid</a></li>
      </ul>
    <h2>Sega Genesis</h2>
      <ul>
        <li><a href="item.jsp">Sonic 2</a></li>
        <li><a href="item.jsp">Earth Worm Jim</a></li>
        <li><a href="item.jsp">Mortal Kombat 2</a></li>
      </ul>-->
  </main>
  <jsp:include page="footer.jsp" />
