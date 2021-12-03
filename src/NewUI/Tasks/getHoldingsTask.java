package NewUI.Tasks;

import NewUI.Data.ItemData;
import NewUI.UIAdapter;
import System.SystemProxy;
import javafx.concurrent.Task;

public class getHoldingsTask extends Task<Boolean> {

    private final SystemProxy prox;
    private final UIAdapter ui;
    private final String name;


    public getHoldingsTask(UIAdapter ui, SystemProxy prox, String name) {
        this.ui  = ui;
        this.prox = prox;
        this.name = name;
    }

    @Override
    public Boolean call() {
        try {
            String symbol;
            int quantity;
            float gate;
            int size = prox.invoke("getHoldingsSize",this.name);
            for(int i=0; i<size ; i++  ) {
                symbol = prox.invoke("getUserItemSymbol" ,this.name,i );
                quantity = prox.invoke("getUserItemQuantity" ,this.name,i );
                gate = prox.invoke("getStockCurrentPrice" ,symbol);

                ui.sendData(new ItemData(symbol,quantity,gate));
            }
        } catch (Throwable throwable) {
            ui.makeException(throwable.getMessage());
        }
        return Boolean.TRUE;
    }

    @Override
    protected void cancelled() {
        super.cancelled();
    }
}
