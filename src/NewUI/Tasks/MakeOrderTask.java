package NewUI.Tasks;

import NewUI.UIAdapter;
import System.SystemProxy;
import javafx.concurrent.Task;

public class MakeOrderTask extends Task<Boolean> {
     private final SystemProxy prox;

        private final UIAdapter ui;

        private final String name;
        private final Boolean isSell;
        private final boolean isMkt;
        private final  String symbol;
        private final int amount;
       private final float limit;


        public  MakeOrderTask(UIAdapter ui, SystemProxy prox, Boolean isSell, Boolean isMkt, String symbol, int amount, float limit, String name) {
                   this.ui=ui;
                   this.prox = prox;
                   this.isSell = isSell;
                   this.isMkt =isMkt;
                   this.symbol = symbol;
                   this.amount =amount;
                   this.limit = limit;
                   this.name = name;
    }


    @Override
    protected Boolean call() throws Exception {
           try {

               String type  =isMkt ? "MKT" : "LIMIT";
               if (prox.invoke("enterRequest", !isSell, symbol, amount, limit, type,name)) {
                this.ui.sendData(true);  //   System.out.println("Request has been completed!\n ");
               } else {
                   this.ui.sendData(false);  //      System.out.println("Request has yet to be completed,\n" +
                 //          " our system will continue to try to fulfil the request until the request will be completed\n\n ");
               }
           } catch (Throwable throwable) {
              ui.makeException(throwable.getMessage());
           }return Boolean.TRUE;
        }
}

