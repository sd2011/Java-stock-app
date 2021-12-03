package System.Users;

import System.RseHoldings;
import System.RseItem;

import java.util.HashMap;
import java.util.Map;

public class Holdings {
    private Map<String, Item> items;

    Holdings(RseHoldings holdings) {
        this.items = new HashMap<>();
        for (RseItem item : holdings.getRseItem()) {
            items.put(item.getSymbol(), new Item(item));
        }


    }

    public Holdings(String symbol, int quantity) {
        this.items = new HashMap<>();
        items.put(symbol, new Item(symbol,quantity));
    }

    public void addHoldings(RseHoldings holdings) {
        this.items = new HashMap<>();
        for (RseItem item : holdings.getRseItem()) {
            items.put(item.getSymbol(), new Item(item));
        }
    }

    public int getSize() {
        return this.items.size();
    }

    public Item getItem(String symbol) {
        return items.get(symbol);
    }

    public Item getItem(int i) {
        return this.items.get(this.items.keySet().toArray()[i]);
    }

    public String getItemSymbol(int i) {
        return getItem(i).getSymbol();
    }

    public String getItemSymbol(String symbol) {
        return getItem(symbol).getSymbol();
    }


    public int getItemQuantity(int i) {
        return getItem(i).getQantity();
    }

    public int getItemQuantity(String symbol) {
        return getItem(symbol).getQantity();
    }

    public void updateStatus(String symbol, boolean buyOrSell, int amount) {
        Item itemToChange = getItem(symbol);
        if (itemToChange == null)
            itemToChange = addItem(symbol, 0);

            itemToChange.update(buyOrSell, amount);
            if (itemToChange.getQantity() <= 0) this.items.remove(symbol);

    }

    public Item addItem(String symbol, int quantity) {
        Item item = new Item(symbol,quantity);
        this.items.put(symbol,item);
        return item;
    }



}







