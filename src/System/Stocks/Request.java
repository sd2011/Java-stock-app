package System.Stocks;

import System.Users.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Request implements Serializable {
    private final String date;
    private final User user;
    private int amount;
    private float gate;
    private final boolean BuyOrSell;



    protected enum Type{
        LMT,MKT,FOK,IOC;


    }
    Type type;

    public Request(Request req) {
        this.amount = req.getAmount();
        this.BuyOrSell = req.getBuyOrSell();
        this.type= req.getType();
        this.date = req.getDate();
        this.gate= req.getGate();
        this.user = req.getUser();
    }





    public Request(String type, boolean buyOrSell, int amount, float gate, User user){
    this.amount = amount;
    this.BuyOrSell = buyOrSell;
    this.type= Type.valueOf(type);
    this.date =  DateTimeFormatter.ofPattern("HH:mm:ss:SSS").format(LocalDateTime.now());
    this.gate=gate;
    this.user = user;
    }

    public String getDate() { return this.date; }

    public int getAmount() { return this.amount; }
    public float getGate() { return this.gate; }

    public Type getType() {return this.type;}


    public boolean getBuyOrSell() {
        return this.BuyOrSell;
    }

    public User getUser() {
        return  this.user;
    }

    public void setGate(float gate) {
        this.gate =gate;
    }

    public void downAmount(int amount) {

        this.amount -= amount;

    }
}
