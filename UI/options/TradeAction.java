package UI.options;

import UI.Options;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import System.SystemProxy;

public class TradeAction {
    public void execute(Options options){
    Scanner scanner = options.getScanner();
    SystemProxy prox = options.getProx();

    try {
        int opt;

        System.out.println("Please select an action, write the action number number.\n");
        System.out.println("1.LIMIT\n2.MKT\n9.Return to menu.\n");
        opt = scanner.nextInt();
        scanner.nextLine();

        while (opt != 1 && opt != 9 && opt != 2) {
            System.out.println("Sorry our system only support Limit and MKT at the moment, please chose again..\n ");
            System.out.println("Please select an action, write the action number.\n");
            System.out.println("1.LIMIT\n2.MKT\n9.Return to menu.\n");
            opt = scanner.nextInt();
            scanner.nextLine();
        }
        if (opt != 9) {
            //exption
            System.out.println("Are you buying or selling?\n write b for buying or s for selling\n");
            String BorS = scanner.next();
            scanner.nextLine();
            while (!BorS.equals("b") && !BorS.equals("s")) {
                System.out.println("Sorry, please write either b for buying or s for selling , in lowercase, for the system to understand your choice\n");
                BorS = scanner.next();
                scanner.nextLine();
            }
            boolean BuyOrSell = BorS.equals("b");
            System.out.println("Please write stock name");
            String symbol = scanner.next();
            scanner.nextLine();
            System.out.println("Please enter the amount of stocks you wish to sell/buy ");
            int amount = scanner.nextInt();
            scanner.nextLine();

            float gate;
            String type;
            if(opt == 1) {
                System.out.println("Please enter limit gate");
                gate = scanner.nextFloat();
                scanner.nextLine();
                type = "LIMIT";
            }

            else {
                gate = 0;
                type = "MKT";
            }

            if (prox.invoke("enterRequest", BuyOrSell, symbol, amount, gate, type)) {
                System.out.println("Request has been completed!\n ");
            } else {
                System.out.println("Request has yet to be completed,\n" +
                        " our system will continue to try to fulfil the request until the request will be completed\n\n ");


            }
            System.out.println("Transactions made by request:\n ");
            //need to addres print
            List<String> transactionsMadeByRequest = prox.invoke("getStockTransactionsMadeByRequest");
            for (String transaction : transactionsMadeByRequest)
                System.out.println(
                        "Date: " + prox.invoke("getStockTransactionDate", symbol, transaction) +
                                " Amount sold: " + prox.invoke("getStockTransactionAmount", symbol, transaction) +
                                " Sale price: " + prox.invoke("getStockTransactionGate", symbol, transaction) +
                                " Action revenue: " + prox.invoke("getStockTransactionCycle", symbol, transaction) +
                                " \n"
                );
        }

    }catch(
    InputMismatchException e){
        System.out.println("Error! Input does not match specified argument\nPlease make sure to enter input according to the system requests \n");
    }


        catch (Throwable e){
        System.out.println("Error! "+e.getMessage()+"\n");
    }

}
}
