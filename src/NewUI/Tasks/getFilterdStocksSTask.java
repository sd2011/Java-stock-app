package NewUI.Tasks;

import NewUI.Data.StockData;
import NewUI.UIAdapter;
import System.SystemProxy;
import javafx.concurrent.Task;

public class getFilterdStocksSTask extends Task<Boolean> {
    private UIAdapter ui;
    private SystemProxy prox;
    private String str;
    private String name;
    private Boolean isSell;
    public getFilterdStocksSTask(UIAdapter uiAdapter, SystemProxy prox, String str, boolean isSell,String name) {
        this.isSell = isSell;
        this.ui = uiAdapter;
        this.name = name;
        this.prox = prox;
        this.str = str;



    }

    @Override
        protected Boolean call() throws Exception {

                String symbol;
            int size = 0;
            try {
                    size = isSell ? prox.invoke("getHoldingsSize",this.name) :
                            prox.invoke("getStocksSize");

                for(int i=0; i<size ; i++  ) {
                    symbol = isSell ? prox.invoke("getUserItemSymbol", this.name, i) :
                            prox.invoke("getStockSymbol", i);

                    if (symbol.indexOf(this.str.toUpperCase()) == 0) {
                        ui.sendData(new StockData(symbol));
                    }
                }
            }
                 catch (Throwable throwable) {
                     ui.makeException(throwable.getMessage());

                 }
            return Boolean.TRUE;
        }

    @Override
    protected void cancelled() {
        super.cancelled();
    }
}


