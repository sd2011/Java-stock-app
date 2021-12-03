<%--
  Created by IntelliJ IDEA.
  User: stav
  Date: 25/06/2021
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@page import="Rizpa.Utils.*" %>
<%@ page import="Rizpa.Constants.Constants" %>
<%@ page import="Rizpa.StandardURL.StandardURL" %>
<% String usernameFromSession = SessionUtils.getUserName(request);%>
<%boolean userTypeFromContext = ServletUtils.getUserManager(request.getServletContext()).getUserType(usernameFromSession);%>
<% String userType = (userTypeFromContext ? "trader" : "admin"); %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="preconnect" href="https://fonts.googleapis.com/%22%3E">
    <link rel="preconnect" href="https://fonts.gstatic.com/" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../../common/commonCSS.css">
    <link rel="stylesheet" href="home.css">
    <link rel="stylesheet" href="../account_management/account_management.css">
    <title>Title</title>
    <script src="../../common/jquery-3.6.0.min.js"></script>
    <script src="../account_management/account_management.js"></script>
    <script src="../../common/commonJS.js"></script>
    <script src="home.js"></script>
</head>
<body>
    <div id="body-components" class="body-components" userType=<%=userType%>>
        <div id="home-container" class="home-container">
            <div id ="home-header" class="home-header">
                <h2>Welcome <%=usernameFromSession%></h2>
            </div>
            <div id="home-body-container" class="home-body-container">
                <div id="left-side" class="left-side">
                    <h3>Users online:</h3>
                    <div id = "users-container" class= "users-container">
                        <ul id ="users-list">
                            <li>no users connected</li>
                        </ul>
                    </div>
                </div>
                <div id ="home-center" class="home-center">
                    <h3>Stocks</h3>
                        <ul id = "stocks-list" class="stocks-list">
                            <li>no stocks are currently available.</li>
                        </ul>
                </div>
            </div>
        </div>
        <%if(userTypeFromContext == Constants.USER_TYPE_TRADER) { %>
        <div class="account-management-container">
            <% Object popMassage = request.getAttribute(Constants.POP_SUCCESS_MASSAGE); %>
        <div id="pop-up" class="pop-up" style= <%if(popMassage != null) {%><%="height:450px;width:350px"%><%}%>>
            <%if(popMassage != null) {%>
            <div class="pop-up-content"><h2 class="massage"><%=popMassage%></h2><button  id="ok-button" class="ok-button">Ok</button></div>
            <% }%>
        </div>
        <div class="management-header">Account Management</div>
        <div class="management-body">
            <div class="left-side">
                <h3>Current balance: </h3>
                <div id ="balance-container" class="balance-container"><div class="balance">0.0</div></div>
                <div class="buttons">
                    <h3>Options:</h3>
                    <button id="issueShare">Issue a Share</button>
                    <button id="loadMoney">Load Money!</button>
                    <div >
                        <button id="chat-button" isOpen="false">chat</button>
                        <div id="chat-placement" class="chat-placement"></div>
                    </div>
                    <div id="file-component">
                        <form action="add_file" method="post" enctype = "multipart/form-data" >
                            <input type="file" value="chose file to upload" id="myFile" name="myFile" required>
                            <input id="submitFile" type="submit" value="Upload the file">
                        </form>
                        <div id="fileStatus">
                            <% Object fileMassage = request.getAttribute(Constants.FILE_SUCCESS_MESSAGE);%>
                            <% Object successObj = request.getAttribute(Constants.FILE_SUCCESS);%>

                            <% if(fileMassage != null){%>
                            <span class =<%=((boolean)successObj ? "good-message" : "err-message")%> ><%=fileMassage%></span>
                            <%}%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="center">
                <h3>Actions history:</h3>
                <div id="actions-container" class="actions-container">
                    <h5>user did not make any actions</h5>
                </div>
            </div>
        </div>
        </div>
    </div>
    <%}else {%>
    <div id="admin-massage">
        <h3>Admin is not allowed to add files or manage account, but can chat</h3>
        <div >
            <button id="chat-button" isOpen="false">chat</button>
            <div id="chat-placement" class="chat-placement"></div>
        </div>
    </div>
    <% }%>
    <div id="bottom-container" class="bottom-container">
        <div id="bottom-notifications-container" class="bottom-notifications-container">
        </div>
    </div>
</body>
<script>
    $("#ok-button").on("click",popDown);
</script>
</html>
