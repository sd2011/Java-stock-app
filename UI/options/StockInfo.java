package UI.options;

import UI.Options;

import java.util.InputMismatchException;
import System.SystemProxy;
import java.util.Scanner;

public class StockInfo {


    public void execute(Options options) {
        try {
         Scanner scanner = options.getScanner();
         SystemProxy prox = options.getProx();

            System.out.println("Please enter the name of the stock you wish to get info of: \n");
            String symbol = scanner.next();
            scanner.nextLine();

            symbol = prox.invoke("getStockSymbol",symbol);

            int size = prox.invoke("getStockTransactionsSize",symbol);
            printStock(symbol,prox,scanner);
            System.out.println("Stock transactions: \n");
            if(size == 0) System.out.println("No transactions been made yet...\n");
            for(int i=0; i<size;i++){
                System.out.println(
                        "Date: " + prox.invoke("getStockTransactionDate",symbol,i) +
                                " Amount sold: "+ prox.invoke("getStockTransactionAmount",symbol,i) +
                                " Sale price: " + prox.invoke("getStockTransactionGate",symbol,i)+
                                " Action revenue: " + prox.invoke("getStockTransactionCycle",symbol,i) +
                                " \n"
                );
            }

        }catch(InputMismatchException e){
            System.out.println("Error! Input does not match specified argument\n\n");
        } catch(Throwable e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void printStock (String stockSymbol,SystemProxy prox, Scanner scanner){
        //nuul exptions
        try {
            System.out.println(
                    "Name: " + stockSymbol +
                            ", Company: " + prox.invoke("getStockCompany", stockSymbol) +
                            ", Current price: " + prox.invoke("getStockCurrentPrice", stockSymbol) +
                            ", Transactions amount: " + prox.invoke("getStockTransactionsSize", stockSymbol) +
                            ", Transactions cycle: " + prox.invoke("getStockTransactionsCycle", stockSymbol) + "\n"
            );
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
