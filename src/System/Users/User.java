package System.Users;

import System.RseUser;
import System.RseHoldings;
import System.Stocks.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class
User {
    private Holdings holdings;
    private final String name;
    private final boolean userType;

    private final boolean TRADER = true;
    private final boolean ADMIN = false;

    private boolean notifications;
    private float balance;
    private Actions actions;

    public User(String name ,boolean userType){
        this.name = name;
        this.holdings =null;
        this.userType = userType;
        this.balance =0;
        this.actions = new Actions();
        this.notifications = false;
    }
    public User(RseUser user){
        this.name = user.getName();
        this.holdings = new Holdings(user.getRseHoldings());
        this.userType = ADMIN;
        this.balance = 0;
        this.actions = new Actions();
    }

    public void addHoldings(RseHoldings holdings) {
        if(this.holdings == null)
            this.holdings = new Holdings(holdings);
        else
          this.holdings.addHoldings(holdings);
    }

    public String getName() {
        return this.name;
    }

    public Holdings getHoldings(){
        return this.holdings;
    }

    public void updateStatus(String symbol, boolean buyOrSell, Transaction trans) {
        float cycle =  buyOrSell ? -trans.getCycle() : trans.getCycle();
        String buyOrSellStr = buyOrSell ? "BUY" : "SELL";
        this.actions.addAction(buyOrSellStr,symbol,trans.getDate(),cycle,this.balance,this.balance += cycle);
       // this.balance +=cycle;
        int amount = trans.getAmount();
        if (this.holdings == null) {
            this.holdings = new Holdings(symbol, 0);
        }
            this.holdings.updateStatus(symbol, buyOrSell, amount);

    }

    public void addAmountOnHold(String symbol, int amount) {
        this.holdings.getItem(symbol).addAmountOnHold(amount);
    }

    public int getItemQuantity(String symbol) {
       return this.holdings.getItem(symbol).getQantity();
    }

    public int getItemAmountOnHold(String symbol) {
        return this.holdings.getItem(symbol).getAmountOnHold();
    }

    public boolean getNotifications() {
        return this.notifications;
    }
    public float getBalance() {
        return this.balance;
    }

    public Actions getActions() {
        return this.actions;
    }

    public boolean UserType() {
        return this.userType;
    }


    public void addToBalance(float money) {
        this.actions.addAction("LOAD",null, DateTimeFormatter.ofPattern("HH:mm:ss:SSS").format(LocalDateTime.now()),money,this.balance,this.balance += money);
       // this.balance += money;
    }

    public boolean containsItem(String symbol) {
        return this.holdings.getItem(symbol) != null;
    }



    public void addItem(String symbol, int amount) {
        if(this.holdings == null){
            this.holdings = new Holdings(symbol,amount);
        }
        else{
            this.holdings.addItem(symbol,amount);
        }
    }

    public void toggleNotifications(boolean b) {
        this.notifications = b;
    }


}
