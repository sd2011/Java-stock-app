<%@ page import="Rizpa.Constants.Constants" %><%--
  Created by IntelliJ IDEA.
  User: stav
  Date: 24/06/2021
  Time: 19:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>RizpaWeb</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
<div class="index-component">
    <div id="header" class="header">
        <h1 id="header-text">Welcome to Rizpa App </h1>
    </div>
    <div id="body" class="body">
        <form method="GET" action="login" class="form">
            <h4> Please enter user name and choose user type </h4>
            <div id="name-component" class="name-component">
                <label class="name-component-label">name:  <input type="text" name="username"/>
                </label>
            </div>
            <div id="type-component" class="type-component">
                <label class="type-component-label">user type:
                    <select name="userType" >
                        <option value="trader" >Trader</option>
                        <option value="admin">Admin</option>
                    </select>
                </label>
            </div>
            <div id="submit-component">
                <input type="submit" value="enter"/>
            </div>
            <% Object errorMessage =request.getAttribute(Constants.USER_ERROR);%>
            <% if (errorMessage != null){%>
            <span id="error-message" class="err-message"><%=errorMessage%> </span>
            <%}%>
        </form>
    </div>

</div>
</body>
</html>