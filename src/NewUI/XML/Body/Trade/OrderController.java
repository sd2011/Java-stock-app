package NewUI.XML.Body.Trade;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class OrderController {

    public Label errorLabel;
    @FXML
    private Button mktButton;

    private SimpleBooleanProperty isMkt;
    @FXML
    private Button lmtButton;
    @FXML
    private Label chosenStock;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField limitTextField;

    private TradeController mainController;




    private SimpleStringProperty chosenStockSymbol;

    public OrderController(){
        this.isMkt =null;
        this.chosenStockSymbol = new SimpleStringProperty("");
    }


    public void initialize(){
    this.chosenStock.textProperty().bind(this.chosenStockSymbol);
    this.errorLabel.visibleProperty().set(false);

    }

    public void setMainController(TradeController tradeController){
        this.mainController =tradeController;
    }

    public void setOrderType(ActionEvent event) {
        Node node = (Node) event.getSource();

       if(isMkt == null)
        {
            isMkt = new SimpleBooleanProperty(true);
            mktButton.disableProperty().bind(isMkt);
            lmtButton.disableProperty().bind(isMkt.not());
            this.limitTextField.disableProperty().bind(isMkt);
        }


        isMkt.set(node.equals(mktButton));
       if(isMkt.getValue()) limitTextField.textProperty().set("0");
    }

    public void setChosenStock(SimpleStringProperty chosenStockSymbol){
        this.chosenStockSymbol.set(chosenStockSymbol.getValue());
    }

    public void makeOrder(ActionEvent event) {
        makeOrder();
    }



    public void makeOrder() {
        if(
                this.isMkt == null ||
                        this.chosenStockSymbol.getValue().equals("") ||
                        this.amountTextField.textProperty().getValue().equals("") ||
                        this.limitTextField.textProperty().getValue().equals("") ||
                        this.limitTextField.textProperty().getValue().equals("")
        ) {
            this.errorLabel.textProperty().set("Please make sure  to complete all of the above before ordering.");
            this.errorLabel.visibleProperty().set(true);
        }

        else     this.mainController.makeOrder(this.isMkt,this.chosenStockSymbol,this.amountTextField.textProperty(),this.limitTextField.textProperty());
    }
}

