
$(function (){
    setInterval(checkUsers,2000);
    setInterval(checkStocks , 2000);
    setInterval(showNotifications,2000);

    addAccountManagementLogic();

    $("#chat-button").on("click", toggleChat);


});


function checkUsers(){
    $.ajax({
        url: url.userList,
        success:function(users){
            if(users === null)
                return;
           $("#users-list").empty();

           $.each(users.users || [], function(index ,user){
            console.log("adding user  "+index);

            // true === trader , false === admin
            const userType = user.userType ? "Trader" : "Admin";

        $('<div id="index" class ="user"><h4>' + user.name + '</h4><h4> '+userType + '</h4></div>').appendTo($("#users-list"));
           });
        },
        error:function (error){
            console.log(error.responseJSON);
        } ,

    });


}

function checkStocks(){
    $.ajax({
        url: url.stocksList,
        success: function(stocks){
            if(stocks === null)
                return;
            $("#stocks-list").empty();

            $.each(stocks.stocks || [] , function (index , stock){
                console.log(stock);

            $(
        '<div id='+index+' class="stock-container">'+
                '<div  class="stock">'+
                    '<div class="stock-attribute company-name">'+
                        '<label>company name: </label><div>'+stock.companyName +'</div>'+
                    '</div>'+
                    '<div class="stock-attribute symbol">'+
                        '<label>SYMBOL: </label><div>'+stock.symbol+'</div>'+
                    '</div>'+
                    '<div class="stock-attribute gate">'+
                        '<label>Current gate: </label><div>'+stock.price+'</div>'+
                    '</div>'+
                    '<div class="stock-attribute cycle">'+
                        '<label>Transactions cycle: </label><div>'+stock.transactionsCycle+'</div>'+
                    '</div>'+
                '</div>'+
             '</div>'
            ).appendTo($("#stocks-list"));

            $("#"+index).on("click",showStock);
            });
        },
        error: function(err){
           console.log("Error "+err.responseJSON);
        },
    });
}

function showStock(e){
   $.ajax({
       url: url.showStock,
       data: {currentStock:e.currentTarget.id},
       success: function(page){
           window.location.replace(page);
       },
       error: function (error){
           console.log(error.responseJSON);
       }
   })
}