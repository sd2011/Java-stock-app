package NewUI.Data;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemData
{
    protected SimpleStringProperty symbol;
    protected  SimpleIntegerProperty quantity;
    protected SimpleFloatProperty stockGate;

    public ItemData(String symbol, int quantity, float stockGate ) {
        this.symbol = new SimpleStringProperty(symbol);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.stockGate =new SimpleFloatProperty(stockGate);
    }


    public SimpleStringProperty getSymbol() {
        return this.symbol;
    }

    public void setItem(ItemData item) {
        this.symbol.set(item.symbol.getValue());
        this.quantity.set( item.quantity.getValue());
        this.stockGate.set(item.stockGate.getValue());
    }

    public SimpleFloatProperty getGate() {
        return this.stockGate;
    }

    public SimpleIntegerProperty getQuantity() {
        return this.quantity;
    }
}
