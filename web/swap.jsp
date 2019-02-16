<jsp:include page="header.jsp" />
<jsp:include page="site-navigation.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <!--bread crumb-->
  <p class="breadcrumb">home &gt; categories &gt; Swap item</p>
  <main id="itempage">
    <!--image and info on the item-->
    <div id="iteminfo">
        <img src=<c:out value="${gameItem.imageURL}"></c:out>>
    <div id="iteminfo">
        <h3><c:out value="${gameItem.name}"></c:out></h3>
    <h4>for <c:out value="${gameItem.console}"></c:out></h4>
    </div>

        <p><c:out value="${gameItem.description}"></c:out></p>

    <div class="swap">
    <h2>Select an item from your available swaps</h2>
    <form action="ProfileController" method="post">
    <table>
      <c:forEach var="userItem" items="${currentProfile.userItems}">
        <c:if test="${userItem.status == 'a'}">
        <tr>
            <td><input type="radio" name="userItem" value="<c:out value="${userItem['userItem'].code}"></c:out>" checked><a href="item.jsp"><c:out value="${userItem['userItem'].name}"></c:out></a></td>
        </tr>
        </c:if>
      </c:forEach>
    </table>
        <input type="hidden" name="swapItem" value="<c:out value="${gameItem.code}"></c:out>">
        <input type="submit" name="action" value="confirmSwap">
    </form>
    </div>
  </main>
  <jsp:include page="footer.jsp" />
