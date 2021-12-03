package NewUI.Tasks;

import NewUI.Data.RequestData;
import NewUI.UIAdapter;
import javafx.concurrent.Task;
import System.SystemProxy;

public class getStockInfoTask extends Task<Boolean> {

    private UIAdapter ui;
    private final SystemProxy prox;
    private final String symbol;
    private final String orderType;



    public  getStockInfoTask(UIAdapter ui, SystemProxy prox, String symbol, String orderType) {
    this.ui =ui;
    this.prox =prox;
    this.symbol = symbol;
    this.orderType = orderType;
    }

    @Override
    protected Boolean call() throws Exception {
        int size;
        try {
            switch (orderType) {
                case "buy":

                    doLoop(
                            "getStockBuyRequestsSize",
                            "getStockBuyRequestDate",
                            "getStockBuyRequestType",
                            "getStockBuyRequestAmount",
                            "getStockBuyRequestGate",
                            "getStockBuyRequestUserName",
                            null

                    );


                    break;
                case "sell":
                    doLoop(
                            "getStockSellRequestsSize",
                            "getStockSellRequestDate",
                            "getStockSellRequestType",
                            "getStockSellRequestAmount",
                            "getStockSellRequestGate",
                            "getStockSellRequestUserName",
                            null

                    );
                    break;
                case "transaction":
                    doLoop(
                            "getStockTransactionsSize",
                            "getStockTransactionDate",
                            "getStockTransactionType",
                            "getStockTransactionAmount",
                            "getStockTransactionGate",
                            "getStockTransactionBuyer",
                            "getStockTransactionSeller"

                    );
                    break;
            }
            } catch(Throwable throwable) {
            ui.makeException(throwable.getMessage());
        }
                return Boolean.TRUE;
            }

        @Override
        protected void cancelled () {
            super.cancelled();
        }

    //personA is the buyer in case of transaction
    private void doLoop(String methodSize,String methodDate,String methodType,
                String methodAmount,String methodPrice,String methodPersonA,String methodPersonB) throws Throwable {
          int size;
            String date;
            String gateType;
            int amount;
            float price;
            String personA;
            String personB;

            size = prox.invoke(methodSize,this.symbol);
            for(int i=0; i<size ; i++  ) {
                date = prox.invoke(methodDate ,this.symbol,i );
                gateType = prox.invoke(methodType ,this.symbol,i );
                amount = prox.invoke(methodAmount ,this.symbol,i);
                price = prox.invoke(methodPrice ,this.symbol,i );
                personA = prox.invoke(methodPersonA ,this.symbol,i );
                personB = methodPersonB != null ? prox.invoke(methodPersonB ,this.symbol,i) : null;

                ui.sendData(new RequestData(orderType,date,gateType,amount,price,personA,personB));
            }
        }
}




