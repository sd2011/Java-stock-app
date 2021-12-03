package System.Stocks;

import System.Users.User;

import java.io.Serializable;

public class Transaction extends Request implements Serializable {
     private final float cycle;
     private final String buyer;
     private final String seller;

     public Transaction(String type, boolean buyOrSell, int amount, float gate, User user, String buyer, String seller){
         super(type, buyOrSell,amount ,gate,user );
         this.buyer = buyer;
         this.seller = seller;
         this.cycle = amount * gate;
     }

    public Transaction(Request request, String buyer, String seller) {
        super(request);
        this.cycle = request.getAmount() * request.getGate();
        this.buyer = buyer;
        this.seller = seller;
    }

    public float getCycle() { return this.cycle; }

    public String getBuyer() {
         return this.buyer;
    }

    public String getSeller() {
         return this.seller;
    }
}
