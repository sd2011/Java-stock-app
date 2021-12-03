package UI.options;

import UI.Options;

import java.util.Scanner;
import System.SystemProxy;
public class ActionsLists {
    public void execute(Options options) {
        Scanner scanner = options.getScanner();
        SystemProxy prox = options.getProx();

        try {

            float cycle, allBuyCycles = 0, allSellCycles = 0, allTransactionsCycles = 0;
            int stocksSize = prox.invoke("getStocksSize"), requestsSize;
            String symbol;
            for (int i = 0; i < stocksSize; i++) {
                symbol = prox.invoke("getStockSymbol", i);
                System.out.println("Actions of " + symbol + ":\n");
                requestsSize = prox.invoke("getStockBuyRequestsSize", symbol);
                System.out.println("\nBuy requests:\n");
                for (int j = 0; j < requestsSize; j++) {
                    cycle = prox.invoke("getStockBuyRequestCycle", symbol, j);
                    allBuyCycles += cycle;
                    System.out.println(
                            "date: " + prox.invoke("getStockBuyRequestDate",symbol, j) +
                                    " amount: " + prox.invoke("getStockBuyRequestAmount",symbol, j) +
                                    " gate: " + prox.invoke("getStockBuyRequestGate",symbol, j) +
                                    " cycle: " + cycle +
                                    "\n"
                    );

                }
                if (requestsSize == 0) System.out.println("No buy requests...\n");
                System.out.println("\nSell requests:\n");
                requestsSize = prox.invoke("getStockSellRequestsSize",symbol);
                for (int j = 0; j < requestsSize; j++) {
                    cycle = prox.invoke("getStockSellRequestCycle",symbol, j);
                    allSellCycles += cycle;
                    System.out.println(
                            "date: " + prox.invoke("getStockSellRequestDate",symbol, j) +
                                    " amount: " + prox.invoke("getStockSellRequestAmount",symbol, j) +
                                    " gate: " + prox.invoke("getStockSellRequestGate",symbol, j) +
                                    " cycle: " + cycle +
                                    "\n"
                    );

                }
                if (requestsSize == 0) System.out.println("No sell requests...\n");
                requestsSize = prox.invoke("getStockTransactionsSize",symbol);
                System.out.println("\nTransactions:\n");
                for (int j = 0; j < requestsSize; j++) {
                    cycle = prox.invoke("getStockTransactionCycle",symbol, j);
                    allTransactionsCycles += cycle;
                    System.out.println(
                            "date: " + prox.invoke("getStockTransactionDate",symbol, j) +
                                    " amount: " + prox.invoke("getStockTransactionAmount",symbol, j) +
                                    " gate: " + prox.invoke("getStockTransactionGate",symbol, j) +
                                    " cycle: " + cycle +
                                    "\n"
                    );
                }
                if (requestsSize == 0) System.out.println("No transactions...\n");
                System.out.println("Overall Cycles:\n Buy actions - " + allBuyCycles + ", Sell actions - " + allSellCycles + ", Transactions - " + allTransactionsCycles + "\n");

            }
        } catch(Throwable e){
            System.out.println("Error!: "+e.getMessage()+"\n");
        }
    }
}
