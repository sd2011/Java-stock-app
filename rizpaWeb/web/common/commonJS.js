const url= {
    balance: "/rizpaWeb_war_exploded/get_balance",
    getUserActions:"/rizpaWeb_war_exploded/get_user_actions",
    userList: "/rizpaWeb_war_exploded/user_list",
    stocksList: "/rizpaWeb_war_exploded/stocks_list",
    accountManagement: "/rizpaWeb_war_exploded/pages/account_management/account_management.jsp",
    showStock:"/rizpaWeb_war_exploded/show_stock",
    getStock:"/rizpaWeb_war_exploded/get_stock",
    getItemQuantity: "/rizpaWeb_war_exploded/get_item_quantity",
    getNotifications:"/rizpaWeb_war_exploded/get_Notifications",
    deleteNotifications:"/rizpaWeb_war_exploded/delete_notifications",
    getOrders: "/rizpaWeb_war_exploded/get_orders",
    sendText:"/rizpaWeb_war_exploded/send_text",
    getMassages:"/rizpaWeb_war_exploded/get_massages",
}

let chatIndex;
let massageInterval =0;

function showNotifications(){
    $.ajax({
        url:url.getNotifications,
        success:function (notifications){
            $("#bottom-notifications-container").empty();
            if(notifications === [] || notifications === null) {
                return;
            }
            const exitHeight = 10;
            const notificationContainerHeight = 65*Object.keys(notifications).length ;
            const notificationHeight = (100/Object.keys(notifications).length).toString() +"%";

            $("#bottom-notifications-container").height(notificationContainerHeight+ exitHeight);

            $('<div class="exit-notifications-container">'+
                '<div class="title">' +
                     'Notfications'+
                '</div>'+
                '<div id="exit-notifications" class="exit-notifications">x</div>' +
                '</div>'
            ).appendTo($("#bottom-notifications-container"));

            $(  '<div id="bottom-notifications" class="bottom-notifications"></div>')
                .height(notificationContainerHeight)
                .appendTo($("#bottom-notifications-container"));




            $("#exit-notifications").on("click",deleteNotifications);





            $.each( notifications || [] ,function(index, notification){

                const buyOrSell = notification.buyOrSell ? "buy" : "sell";
                const payment = notification.buyOrSell ? "bought" : "sold";
                const stringEnd =notification.leftOvers > 0  ? (" currently there are " + notification.leftOvers + " of the order waiting to be " +  payment ): " Order has been completed fully!";
                $('<div class="notification">' +
                    notification.transaction.date +' A new '+ buyOrSell + ' has been made. '+
                    notification.symbol + ' has been ' + payment + ' with a amount of '+
                    notification.transaction.amount + ' and gate ' + notification.transaction.gate + ' which make a ' + notification.transaction.cycle +' worth of a deal. ' +
                    ' after transaction you hold currently ' + notification.itemQuantity + ' from ' + notification.symbol  + ' item and your balance is now ' +
                    notification.balance+'. '+ stringEnd +
                    '</div>'
                )
                    .height(notificationHeight)
                    .appendTo($("#bottom-notifications"));
            });
        },
        error:function (err){
            console.log(err);
        }
    })
}

function deleteNotifications(){
    $.ajax({
        url:url.deleteNotifications,
        method:"POST",
        success:function (){
            $("#bottom-container").empty();
        },
        error:function(err){
            console.log(err);
        }
    })
}



function toggleChat(){


  if( $("#chat-button").attr("isOpen") === "false"){
      $('' +
          '<div id="chat-container" class="chat-container">' +
          '<div id="chat-header" class="chat-header">' +
          '<div id="chat-exit" class="chat-exit">x</div>' +
            '<div class="chat-title" class="chat-title">' +
                '<h3>Chat</h3>' +
            '</div>'+
          '</div>'+
            '<div id="texts" class="texts"></div>' +
            '<div id="textInput">' +
                '<form id="send-text" method="post" >' +
                    '<input id="chatText" type="text" name="chatText"/> <input type="submit" value="send">' +
                '</form>' +
            '</div>' +
          '</div>').appendTo($("#chat-placement"));
      $("#chat-button").attr("isOpen","true");

      $("#chat-exit").on("click",toggleChat);

      $("#send-text").on("submit",sendText);

      chatIndex=0;
       massageInterval = setInterval(getMassages,2000);
  }
  else{
      $("#chat-placement").empty();

      $("#chat-button").attr("isOpen","false");
      clearInterval(massageInterval);
  }


}

function sendText(e){
    e.preventDefault();
    $.ajax({
        url:url.sendText,
        data:$("#send-text").serialize(),
        type:"post",
        success:function (r){

        },
        error:function (err){
            console.log(err);
        },
    });
    $("#chatText").val("");
}

function getMassages() {
    $.ajax({
        url:url.getMassages,
        data:"chatIndex="+  chatIndex,
        dataType:"json",
        success:function (chatAndIndex) {

            if(chatAndIndex.index !== chatIndex){
                $.each(chatAndIndex.massageList ||[] , function(index,massage){
                    $(
                        '<div id="massage-'+index+'" class="text-massage">' +
                                '<div>'+massage.name+':</div><div>'+massage.text+'</div>'+
                             '</div>'
                        ).appendTo($("#texts"))
                });
            }
            chatIndex = chatAndIndex.index;

        }
    })

}