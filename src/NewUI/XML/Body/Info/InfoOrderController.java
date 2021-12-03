package NewUI.XML.Body.Info;

import NewUI.Data.RequestData;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

public class InfoOrderController extends RequestData {

    @FXML
    public Label dateLabel;
    @FXML
    public Label typeLabel;
    @FXML
    public Label amountLabel;
    @FXML
    public Label priceLabel;
    @FXML
    public Label buyerSellerLabel;
    public TitledPane titleLabel;

    private SimpleStringProperty buyerSeller;
    private InfoController mainController;

    public InfoOrderController() {
        super("","","",-1,-1,"","");
        buyerSeller = new SimpleStringProperty("");
    }


        public void initialize(){
        titleLabel.textProperty().bind(this.date);
             dateLabel.textProperty().bind(Bindings.concat("Date: ",this.date));
             typeLabel.textProperty().bind(Bindings.concat("Type: ",this.gateType));
             amountLabel.textProperty().bind(Bindings.concat("Amount: ",this.amount));
            priceLabel.textProperty().bind(Bindings.concat("Price: ",this.price));
            buyerSellerLabel.textProperty().bind(this.buyerSeller);
        }


        public void setMainController(InfoController mainController) {
            this.mainController = mainController;
        }

        public void setBuyerSeller(){

        if(this.orderType.getValue().equals("buy")) this.buyerSeller.set("Buyer: " + this.buyer.getValue());
        else if(this.orderType.getValue().equals("sell")) this.buyerSeller.set("Seller: " + this.buyer.getValue());
        else this.buyerSeller.set("Buyer: " + this.buyer.getValue() + " ,Seller: " + this.seller.getValue() );
    }

    }


