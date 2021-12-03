package NewUI.Data;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RequestData {

    protected final SimpleStringProperty orderType;
    protected final SimpleStringProperty date;
    protected final SimpleStringProperty gateType;
    protected final SimpleIntegerProperty amount;
    protected final SimpleFloatProperty price;
    protected final SimpleStringProperty buyer;
    protected final SimpleStringProperty seller;

    public RequestData(String orderType, String date, String gateType, int amount, float price, String personA, String personB) {
        this.orderType =new SimpleStringProperty(orderType);
        this.date =new SimpleStringProperty(date);
        this.gateType = new SimpleStringProperty(gateType);
        this.amount = new SimpleIntegerProperty(amount);
        this.price =new SimpleFloatProperty(price);
        this.buyer = new SimpleStringProperty(personA);
        this.seller = new SimpleStringProperty(personB);
    }


    public void setOrderType (String toSet){
        this.orderType.set(toSet);
    }
    public void setDate (String toSet){
        this.date.set(toSet);
    }public void setGateType(String toSet){
        this.gateType.set(toSet);
    }public void setAmount (int toSet){
        this.amount.set(toSet);
    }public void setPrice (float toSet){
        this.price.set(toSet);
    }public void setBuyer (String toSet){
        this.buyer.set(toSet);
    }
    public void setSeller(String toSet){
        this.seller.set(toSet);
    }

    public SimpleStringProperty getOrderType ( ){
        return this.orderType;
    }
    public SimpleStringProperty getDate (){
        return this.date;
    }public SimpleStringProperty getGateType(){
        return this.gateType;
    }public SimpleIntegerProperty getAmount (){
        return this.amount;
    }public SimpleFloatProperty getPrice (){
        return this.price;
    }public SimpleStringProperty getBuyer (){
        return this.buyer;
    }public SimpleStringProperty getSeller(){
        return this.seller;
    }
}
