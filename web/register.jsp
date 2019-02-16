<jsp:include page="header.jsp" />
<jsp:include page="site-navigation.jsp" />
<!--bread crumb-->
  <p class="breadcrumb">Home &gt; Register</p>
  <main>
      <div class="login">
      <form action="ProfileController" method="post">
          <table>
              <tr><td>First Name: </td>
                  <td><input type="text" name="firstname" required></td></tr>
              <tr><td>Last Name: </td>
                  <td><input type="text" name="lastname" required></td></tr>
              <tr><td>Email: </td>
                  <td><input type="email" name="email" required></td></tr>
              <tr><td>Password: </td>
                  <td><input type="password" name="password" required></td></tr>
              <tr><td>Address 1: </td>
                  <td><input type="text" name="address1" required></td></tr>
              <tr><td>Address 2: </td>
                  <td><input type="text" name="address2" value=""></td></tr>
              <tr><td>City: </td>
                  <td><input type="text" name="city" required></td></tr>
              <tr><td>State: </td>
                  <td><input type="text" name="state" maxlength="2" required></td></tr>
              <tr><td>Zip Code: </td>
                  <td><input type="text" name="zipcode" maxlength="10" required></td></tr>
              <tr><td>Country: </td>
                  <td><input type="text" name="country" required></td></tr>
        </table>
          
        <button class="button" type="submit" name="action" value="register">Register</button>
      </form>
      </div>
  </main>
<jsp:include page="footer.jsp" />

