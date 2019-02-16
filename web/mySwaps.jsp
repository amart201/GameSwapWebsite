<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />
<jsp:include page="site-navigation.jsp" />
  <!--bread crumb-->
  <p class="breadcrumb">Home &gt; My Swaps</p>
  <main>
      <h2><c:out value="${theUser.firstName}"></c:out>'s Swap Offers</h2>

    <!--table for my games-->
    <table>
      <tr>
        <th>Game</th>
        <th>Swap Offer</th>
        <th>Swapper Rating</th>
        <th></th>
        <th></th>
      </tr>
      <c:forEach var="userItem" items="${currentProfile.userItems}">
          <c:if test="${userItem.status == 'p' || userItem.status == 'o'}">
      <tr>
          <td><a href="CatalogController?itemCode=<c:out value="${userItem['userItem'].code}"></c:out>"><c:out value="${userItem['userItem'].name}"></c:out></a></td>
          <td><a href="CatalogController?itemCode=<c:out value="${userItem['swapItem'].code}"></c:out>"><c:out value="${userItem['swapItem'].name}"></c:out></a></td>
          <td><c:out value="${userItem.swapperRating}"></c:out>/5</td>
        <c:choose>
          <c:when test="${userItem.status == 'p'}">
          <td>
              <form action="ProfileController" method="post">
               <input type="hidden" name="itemList" value="<c:out value="${userItem['userItem'].code}"></c:out>">
               <input type="hidden" name="theItem" value="<c:out value="${userItem['userItem'].code}"></c:out>">
                <button value="accept" name="action" type="submit">Accept</button>
              </form>
          </td>
          <td>
            <form action="ProfileController" method="post">
              <input type="hidden" name="itemList" value="<c:out value="${userItem['userItem'].code}"></c:out>">
              <input type="hidden" name="theItem" value="<c:out value="${userItem['userItem'].code}"></c:out>">
              <button value="reject" name="action" type="submit">Reject</button>
            </form>
          </td>
          </c:when>
          <c:when test="${userItem.status == 'o'}">
            <td colspan="2">
                <form action="ProfileController" method="post">
                    <input type="hidden" name="itemList" value="<c:out value="${userItem['userItem'].code}"></c:out>">
                    <input type="hidden" name="theItem" value="<c:out value="${userItem['userItem'].code}"></c:out>">
                    <button value="withdraw" name="action" type="submit">Withdraw swap</button>
                </form>
            </td>
          </c:when>
        </c:choose>
      </tr>
          </c:if>
      </c:forEach>
    </table>
  </main>
  <jsp:include page="footer.jsp" />