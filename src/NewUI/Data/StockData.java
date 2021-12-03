package NewUI.Data;

import javafx.beans.property.SimpleStringProperty;

public class StockData {
    protected SimpleStringProperty symbol;

    public StockData(String symbol) {
        this.symbol = new SimpleStringProperty(symbol);
    }

    public SimpleStringProperty getSymbol(){
        return this.symbol;
    }
    public void setSymbol(SimpleStringProperty symbol){
        this.symbol.set(symbol.getValue());
    }
}
