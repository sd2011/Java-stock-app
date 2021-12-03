package UI.options;

import UI.Options;

import java.util.InputMismatchException;
import java.util.Scanner;

import System.SystemProxy;

public class ShowStocks {
    public void execute(Options options) {
            Scanner scanner = options.getScanner();
            SystemProxy prox = options.getProx();
        try {
                int size = prox.invoke("getStocksSize");

                for (int i=0;i<size;i++) printStock(prox.invoke("getStockSymbol",i), prox,scanner);
            }catch(InputMismatchException e){
                System.out.println("Error! Input does not match specified argument\n\n");
            }
            catch (Throwable e){
                System.out.println("Error! "+e.getMessage()+"\n");
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

