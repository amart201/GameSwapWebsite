<%@page import="business.UserProfile"%>
<%@page import="business.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<jsp:include page="site-navigation.jsp" />
  <!--bread crumb-->
  <p class="breadcrumb">Home &gt; My Games</p>
  <main>
      <% User user = (User) session.getAttribute("theUser"); %>
      <% UserProfile profile = (UserProfile) session.getAttribute("currentProfile"); %>
      <h2><c:out value="${theUser.firstName}"></c:out>'s Games for Swap</h2>

    <!--table for my games-->
    <table>
      <tr>
        <th>Game</th>
        <th>Console</th>
        <th>Rating</th>
        <th>Swapped</th>
        <th></th>
        <th></th>
      </tr>
      <c:forEach var="userItem" items="${currentProfile.userItems}">
      <tr>
          <td><a href="CatalogController?itemCode=<c:out value="${userItem['userItem'].code}"></c:out>"><c:out value="${userItem['userItem'].name}"></c:out></a></td>
        
                  <td><c:out value="${userItem['userItem'].console}"></c:out></td>
          <td><strong><c:out value="${userItem.rating}"></c:out>/5</strong></td>
        <td>
            <c:choose>
                <c:when test="${userItem.status eq 'a'}">
                    <img src="images/swapicon.jpg" alt="swap icon">
                </c:when>
                <c:when test="${userItem.status eq 's'}">
                    <img src="images/checkicon.png" alt="star icon">
                </c:when>
                <c:when test="${userItem.status eq 'p' || userItem.status eq 'o'}">
                    <img src="images/pending.png" alt="pending icon">
                </c:when>
            </c:choose>
        </td>
        <td>
          <form action="ProfileController" method="post">
              <input type="hidden" name="itemList" value="<c:out value="${userItem['userItem'].code}"></c:out>">
             <input type="hidden" name="theItem" value="<c:out value="${userItem['userItem'].code}"></c:out>">
            <button type="submit" name="action" value="update">Update</button>
          </form>
        </td>
        <td>
          <form action="ProfileController" method="post">
              <input type="hidden" name="itemList" value="<c:out value="${userItem['userItem'].code}"></c:out>">
            <input type="hidden" name="theItem" value="<c:out value="${userItem['userItem'].code}"></c:out>">
            <button type="submit" name='action' value='delete'>Delete</button>
          </form>
        </td>
        <td>
            <c:if test="${userItem.status eq 's'}">
                <form action="ProfileController" method="post">
                <button type="submit" name="action" value="rateuser"><img src="images/staricon.png" alt="star icon">Rate User</button>
                <input type="hidden" name="offerID" value="<c:out value="${userItem.offerID}"></c:out>">
                <input type="hidden" name="swapperID" value="<c:out value="${userItem.swapperID}"></c:out>">
                <input type="radio" name="rating" value="1">1
                <input type="radio" name="rating" value="2">2
                <input type="radio" name="rating" value="3">3
                <input type="radio" name="rating" value="4">4
                <input type="radio" name="rating" value="5">5
                </form>
            </c:if>
        </td>
      </tr>
      </c:forEach>
    </table>
    <p id="legend">Legend: <img src="images/swapicon.jpg" alt="swap icon"> Available to swap, <img src="images/checkicon.png" alt="star icon">
        Already swapped, <img src="images/pending.png" alt="pending icon">Pending.</p>
  </main>
  <jsp:include page="footer.jsp" />