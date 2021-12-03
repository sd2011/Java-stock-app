/*const url= {
    balance: "/rizpaWeb_war_exploded/get_balance",
    getUserActions:"/rizpaWeb_war_exploded/get_user_actions"
}*/

function popDown(){
    $("#pop-up").empty().height(    0).width(0);
}

function addAccountManagementLogic (){
        setInterval(getBalance,2000);
        setInterval(getUserActions, 2000);
    setInterval(showNotifications,2000);

    $("#issueShare").on("click", popShareForm);
        $("#loadMoney").on("click", popLoadMoney);
}

function getBalance(){
    $.ajax({
        url:url.balance,
        success: function(balance){
            console.log(balance);
            $("#balance-container").empty();

            $('<div class="balance">'+balance+'</div>').appendTo($("#balance-container"));

        }
        }
    )
}

function popShareForm(){

    $("#pop-up").empty().height(450).width(350);


    $('<div class="pop-up-container"><div class="pop-up-content">' +
        '<button id="close-pop">close</button>'+
        '<h2>Please enter stock details and press submit once you\'re done </h2>' +
        '<form method="post" action="issue_share" class="issue-form">' +
        '<div class="form-input">'+
        '<label>Company name: </label><input type="text" name="companyName"/>' +
        '</div>' +
        '<div class="form-input">'+
        '<label>Symbol: </label><input type="text" name="symbol"/>' +
        '</div>' +
        '<div class="form-input">'+
        '<label>Issue amount: </label><input type="number" step="1" min="1" name="IssueAmount"/>' +
        '</div>' +
        '<div class="form-input">'+
        '<label>Company estimated value: </label><input type="text"  name="companyEstimatedValue"/>' +
        '</div>' +
        '<div class="form-input">'+
        '<input type="submit" value="submit"/> ' +
        '</div>'+
        '</form>'+
        '</div>' +
        '</div>').appendTo($("#pop-up"));

        $("#close-pop").on("click",popDown);


}

function popLoadMoney(){
    $("#pop-up").empty().height(450).width(350);

    $(
    '<div class="pop-up-content">' +
        '<button id="close-pop">close</button>'+
            '<div class="addMoneyContainer">'+
                '<h3>Please enter amount of money you wish to load and submit</h3>'+
                '<form method="post" action="load_money">'+
                    '<label>amount: </label><input type="text"  name="moneyAmount"/>'+
                    '<input type="submit" value="Add Money"/>' +
                '</form>'+
            '</div>'+
        '</div>'
    ).appendTo($("#pop-up"));
    $("#close-pop").on("click",popDown);

}

function getUserActions(){
    $.ajax({
        url:url.getUserActions,
        success: function (actions){
            if(actions.actions === null)
                return;
            $("#actions-container").empty();

            $.each(actions.actions || [] , function(date ,action){
                const symbol = action.symbol ?action.symbol : "None";
               $(
           '<div class="action-container">'+
                    '<div class="action">'+
                        '<div class="action-attribute action-type">'+
                            '<label>Action type: </label><div>'+action.type +'</div>'+
                        '</div>'+
                        '<div class="action-attribute Symbol">'+
                            '<label>stock Symbol: </label><div>'+symbol +'</div>'+
                        '</div>'+
                        '<div class="action-attribute date">'+
                            '<label>Date: </label><div>'+action.date+'</div>'+
                        '</div>'+
                        '<div class="action-attribute action-sum">'+
                            '<label>Action sum: </label><div>'+action.sum+'</div>'+
                        '</div>'+
                        '<div class="action-attribute previous-balance">'+
                            '<label>Balance before transaction: </label><div>'+action.previousBalance+'</div>'+
                        '</div>'+
                        '<div class="action-attribute new-balance">'+
                            '<label>Balance after transaction: </label><div>'+action.newBalance+'</div>'+
                        '</div>'+
                    '</div>'+
                '</div>'
            ).appendTo($("#actions-container"));

            });

            $(".action-attribute").sort(function(a,b){
                return $(a).id < $(b).id ? -1 :1;
            }).appendTo($("#action"));
        }
    })
}