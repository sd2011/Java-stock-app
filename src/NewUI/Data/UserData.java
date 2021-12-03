package NewUI.Data;

import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;

public class UserData {
    protected SimpleStringProperty name;
    protected Map<String,ItemData> items;


    public UserData(String name){
        this.name = new SimpleStringProperty(name);
        this.items = new HashMap<>();
    }

    public String getName(){return this.name.get();}
    public void setName(String name){
        this.name.set(name);
    }

    public void setName(SimpleStringProperty name) {
        this.name= name;
    }

    public ItemData getItem(String symbol){return this.items.get(symbol);}
    public void putItem(ItemData item){
        this.items.put(item.getSymbol().getValue(),item);
    }

}
