<jsp:include page="header.jsp" />
<jsp:include page="site-navigation.jsp" />
<!--bread crumb-->
  <p class="breadcrumb">Home &gt; Sign in</p>
  <main>
      <div class="login">
      <form action="ProfileController" method="post">
          <table>
              <tr><td>Email: </td>
                  <td><input type="email" name="username"></td></tr>
              <tr><td>Password: </td>
                  <td><input type="password" name="password"></td></tr>
        </table>
          
        <button class="button" type="submit" name="action" value="signin">Sign in</button>
      </form>
          <p>Don't have an account? <a href="register.jsp">Register</a></p>
      </div>
  </main>
<jsp:include page="footer.jsp" />
