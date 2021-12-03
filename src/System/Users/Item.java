package System.Users;

import System.RseItem;

public class Item {
    private String symbol;
    private int quantity;
    private int amountOnHold;


    public Item(RseItem item){
        this.symbol = item.getSymbol();
        this.quantity = item.getQuantity();
        this.amountOnHold =0;
    }

    public Item(String symbol, int quantity){
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getQantity() {
        return this.quantity;
    }

    public void update(boolean buyOrSell, int amount) {
        this.quantity = buyOrSell ? this.quantity + amount : this.quantity - amount;
        if(!buyOrSell) this.amountOnHold -= amount;
    }

    public void addAmountOnHold(int amount) {
        this.amountOnHold +=amount;
    }

    public int getAmountOnHold() {
        return this.amountOnHold;
    }
}
