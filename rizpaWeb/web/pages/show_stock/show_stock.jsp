<%@ page import="Rizpa.Constants.Constants" %>
<%@ page import="Rizpa.Utils.SessionUtils" %>
<%@ page import="Rizpa.Utils.ServletUtils" %>
<%@ page import="Rizpa.StandardURL.StandardURL" %><%--
  Created by IntelliJ IDEA.
  User: stav
  Date: 29/06/2021
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%Object symbol = session.getAttribute(Constants.CURRENT_STOCK);%>
<% String usernameFromSession = SessionUtils.getUserName(request);%>
<%boolean userTypeFromContext = ServletUtils.getUserManager(request.getServletContext()).getUserType(usernameFromSession);%>
<% String userType = (userTypeFromContext ? "trader" : "admin"); %>

<head>
    <title>show_stock</title>
    <link rel="preconnect" href="https://fonts.googleapis.com/%22%3E">
    <link rel="preconnect" href="https://fonts.gstatic.com/" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../../common/commonCSS.css">
    <link rel="stylesheet" href="show_stock.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.4.1/chart.min.js" integrity="sha512-5vwN8yor2fFT9pgPS9p9R7AszYaNn0LkQElTXIsZFCL7ucT8zDCAqlQXDdaqgA1mZP47hdvztBMsIoFxq/FyyQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script src="../../common/jquery-3.6.0.min.js"></script>
    <script src="../../common/commonJS.js"></script>
    <script src="show_stock.js"></script>
</head>
</head>
<body>
<div class="show-stock-header" >
    <h2><%=symbol%> Info </h2>
</div>
<div class="show-stock-body">
    <div class="left-side">
        <div id="stock-info-container" class="stock-info-container">
        </div>

        <div class="item-quantity"> <%if(userTypeFromContext == Constants.USER_TYPE_TRADER){%>
            <label>You currently hold
            <div id="quantity"></div>
         shares from this stock</label>
        <%} else {%>

        <div>
            admin can not hold shares from this stock
        </div>
        <%}%>
        </div>
        <div class="services">
            <a href=<%=StandardURL.RETURN_HOME%> >click here to return to homePage </a>
            <button id="chat-button" isOpen="false">chat</button>
            <div id="chat-placement" class="chat-placement"></div>
        </div>

    </div>
    <div class="center">
        <div class="transactions-component-container">
            <h4>Transactions made:</h4>
            <ul id = "transactions-list" class="transactions-list">
            </ul>
        </div>
            <canvas id="myChart" width="300" height="100"></canvas>
    </div>
    <div id="right-side" class="right-side" userType=<%=userType%>>
        <%if(userTypeFromContext == Constants.USER_TYPE_TRADER){%>
    <form class="order-action-container" method="post" action="make_order">
        Make an order
        <label>Buy or Sell:
            <select name="buyOrSell">
                <option value="true">Buy</option>
                <option value="false">Sell</option>
            </select>
        </label>
        <label>Order Type
        <select name="orderType" id="orderType">
            <option value="LMT">LMT</option>
            <option value="MKT">MKT</option>
            <option value="FOK">FOK</option>
            <option value="IOC">IOC</option>
         </select>
        </label>
        <div></div>
        <label>Limit:</label><input type="text" name="limit" id="limit" class="input-line">
        <label>Amount:</label><input type="number" step="1" min="1" name="amount" class="input-line"/>
        <div><label>are you sure you want to make this order? </label><input type="checkbox" required/></div>
        <input type="submit" value="submit">
        <%Object orderMassage = request.getAttribute(Constants.ORDER_SUCCESS_MESSAGE);%>
        <%Object orderSuccess = request.getAttribute(Constants.ORDER_SUCCESS);%>
        <%if(orderMassage != null){%>
        <span class=<%=(boolean) orderSuccess ? "good-message" : "err-message" %> ><%=orderMassage%></span>
        <%}%>
    </form>
    <%} else{%>
    <div class="admin-right">
        <h3>Stock orders:</h3>
        <div id="order-list" class="order-list">
            <div>No orders has been made...</div>
        </div>
    </div>
    <%}%>
    </div>
</div>
<div id="bottom-container" class="bottom-container">
    <div id="bottom-notifications-container" class="bottom-notifications-container">
    </div>
</div>
</body>
</html>
