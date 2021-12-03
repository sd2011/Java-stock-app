package System.Log;

import System.Stocks.Transaction;
import System.Users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logger implements Serializable {
    private static Map<String ,List<LogTransaction>> newUserLogs;



    public static void addTransactionLog(Transaction trans, String symbol, int amountOfBuyer, int amountOfSeller ,User buyer, User seller) {
    if (newUserLogs == null){
        newUserLogs = new HashMap<>();
    }
        addTransactionLogForUser(trans, symbol,buyer, amountOfBuyer,true);
        addTransactionLogForUser(trans, symbol,seller, amountOfSeller,false);
    }

    public static void addTransactionLogForUser(Transaction trans,String symbol, User user, int amount,boolean buyOrSell){
        String name =user.getName();
        if (!newUserLogs.containsKey(name)) {
          newUserLogs.put(name, new ArrayList<>());
        }
        newUserLogs.get(name).add(new LogTransaction(trans,user,symbol,amount,buyOrSell));
        user.toggleNotifications(true);
    }

    public static List<LogTransaction> GetTransactionLogsOfUser(String userName) {
        return newUserLogs.get(userName);
    }

    public static void deleteUserLogs(String userName) {
        newUserLogs.remove(userName);
    }



    /*
    public List<String> getNewStockTransactions() {
        List<String> transactionsDate = new ArrayList<String>();

        for(LogTransaction log : newTransactions){
            transactionsDate.add(log.getDate());
        }

        newTransactions.clear();

        return transactionsDate;
    }
*/



}
