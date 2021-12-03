package System.Log;

import System.Stocks.Transaction;
import System.Users.User;

import java.io.Serializable;

public class LogTransaction implements Serializable {
    private final Transaction transaction;
    private final float balance;
    private final int itemQuantity;
    private final int leftOvers;
    private final String symbol;
    private final boolean buyOrSell;


    public LogTransaction(Transaction transaction, User user, String symbol, int leftOvers,boolean boyOrSell){
        this.transaction = transaction;
        this.symbol=symbol;
        this.balance = user.getBalance();
        this.leftOvers = leftOvers;
        this.itemQuantity = user.getItemQuantity(symbol);
        this.buyOrSell = boyOrSell;
    }
    //(trans.getDate) A new (buyOrSell) has been made.
    // (trans.getSymbol) has been (buyOrSell ? "bought" : "sold") with a amount of
    // (trans.getAmount) add gate (trans.getGate) which make a (trans.getCycle) worth of a deal
    //after transaction you hold currently (itemQuantity) from (trans.getSymbol) item and your balance is now
    //(this.balance). (this.leftOvers > 0  ? "currently there are (this.leftOvers) of the order waiting to be
    // (buyOrSell ? "bought" : "sold") : "Order has been complete fully!



}
