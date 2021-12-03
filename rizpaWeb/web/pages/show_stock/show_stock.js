/*const url ={
    getStock:"/rizpaWeb_war_exploded/get_stock",
    getItemQuantity: "/rizpaWeb_war_exploded/get_item_quantity",
    getNotifications:"/rizpaWeb_war_exploded/get_Notifications",
    deleteNotifications:"/rizpaWeb_war_exploded/delete_notifications",
}*/
let dataInGraphAmount= -1;


let ctx;
let myChart;


let ordersNum =0;
$(function (){
    setInterval(getStockInfo,2000)
    setInterval(showNotifications,2000);
    if($("#right-side").attr("userType") === "trader")
        setInterval(getItemQuantity, 2000);
    else
        setInterval(getOrders,2000);

    $("#chat-button").on("click", toggleChat);


    $("#orderType").on("change",function() {
        $("#limit").prop("disabled", $(this).val() === "MKT");
        $("#limit")
            .attr("value", $(this).val() === "MKT" ? "0" : $("#limit").val())

    });
    //graph stuff
     ctx = $("#myChart");


     myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: [],
            datasets: [{
                label: 'Stock cycle',
                data: [],
                borderColor: 'rgb(75, 192, 192)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });


})


function getStockInfo(){
    $.ajax({
        url:url.getStock,
        success:function(stock){
            $("#stock-info-container").empty();

            $(
            '<div class="stock-container">'+
            '<div  class="stock">'+
            '<div class="stock-attribute symbol">'+
            '<label>SYMBOL: </label><div>'+stock.symbol+'</div>'+
            '</div>'+
            '<div class="stock-attribute company-name">'+
            '<label>company name: </label><div>'+stock.companyName +'</div>'+
            '</div>'+
            '<div class="stock-attribute gate">'+
            '<label>Current gate: </label><div>'+stock.price+'</div>'+
            '</div>'+
            '<div class="stock-attribute cycle">'+
            '<label>Transactions cycle: </label><div>'+stock.transactionsCycle+'</div>'+
            '</div>'+
            '</div>'+
            '</div>'
        ).appendTo($("#stock-info-container"));

            $("#transactions-list").empty();
            $.each(stock.transactions || [],function (index,transaction){


                $(
                '<div class="transaction-container">'+
                         '<div  class="transaction">'+
                             '<div class="transaction-attribute date">'+
                                '<label>Date: </label><div>'+transaction.date+'</div>'+
                             '</div>'+
                             '<div class="transaction-attribute amount">'+
                                '<label>Amount: </label><div>'+transaction.amount +'</div>'+
                             '</div>'+
                             '<div class="transaction-attribute gate">'+
                                '<label>Gate: </label><div>'+transaction.gate+'</div>'+
                             '</div>'+
                        '</div>'+
                    '</div>'
                ).appendTo($("#transactions-list"));

                if(index > dataInGraphAmount){
                    dataInGraphAmount = index;
                    myChart.data.labels.push(transaction.date);
                    myChart.data.datasets.forEach((dataSet)=>{dataSet.data.push(transaction.cycle);});
                    myChart.update();
                }
            });

            $(".transaction-container").sort(function(divA,divB){
                return $(divA).id < $(divB).id ? -1 : 1;
            }).appendTo($("#transactions-list"));
        },
        error:function(err){
            conosle.log(err);
        }

    });
}

function getItemQuantity(){
    $.ajax({
        url:url.getItemQuantity,
        success:function (quantity){
            $("#quantity").empty();
            console.log(quantity);

            $('<h4>'+quantity +'</h4>').appendTo($("#quantity"));
        },
        error:function(error){
            $("#quantity").empty();
            console.log(quantity);

            $('<h4>'+0+'</h4>').appendTo($("#quantity"));
        }
    })
}

function getOrders(){
    $.ajax({
        url:url.getOrders,
        success:function (orders){
            if(orders.length === 0 || orders.length === ordersNum)
                return;
            $("#order-list").empty();

            $.each(orders || [] ,function (index,order){
                const buyOrSell = order.buyOrSell === "true" ? "BUY" : "SELL";

                $(
                '<div class="order-container">'+
                '<div  class="order">'+
                '<div class="order-attribute date">'+
                '<label>Date: </label><div>'+order.date+'</div>'+
                '</div>'+
                '<div class="order-attribute buyOrSell">'+
                    '<label>Order: </label><div>'+buyOrSell+'</div>'+
                '</div>'+
                '<div class="order-attribute user">'+
                '<label>User: </label><div>'+order.user.name+'</div>'+
                '</div>'+
                '<div class="order-attribute orderType">'+
                '<label>Order type: </label><div>'+order.type+'</div>'+
                '</div>'+
                '<div class="order-attribute amount">'+
                '<label>Amount: </label><div>'+order.amount +'</div>'+
                '</div>'+
                '<div class="order-attribute gate">'+
                '<label>Gate: </label><div>'+order.gate+'</div>'+
                '</div>'+
                '</div>'+
                '</div>'
            ).appendTo($("#order-list"));
        });
            ordersNum = orders.length;
        },
        error:function (err) {
            console.log(err.responseText);
        },
    });
}






