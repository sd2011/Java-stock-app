package NewUI.XML.Body.Trade;

import NewUI.XML.Body.Stock.scrollLogic;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class TradeController  extends scrollLogic {

    public  Label nameLabel;
    public Button noButton;
    public Button yesButton;
    public Circle returnCircle;
    public Label returnLabel;
    @FXML
    private Pane infoLayer;
    @FXML
    private Label infoLabel;
    @FXML
    private GridPane tradeLayer;

    @FXML
    private Pane orderComponent;
    @FXML
    private OrderController orderComponentController;

    @FXML
    private Pane bigButtonsLayer;

    @FXML
    private Button bigBuyButton;
    @FXML
    private Button bigSellButton;

    @FXML
    private Button buyButton;
    @FXML
    private Button sellButton;

    private SimpleStringProperty userName;
    private SimpleBooleanProperty isSure;


    public TradeController(){
    userName = new SimpleStringProperty("");
    isSure = new SimpleBooleanProperty(false);
    }



    public void initialize(){
        tradeLayer.setVisible(false);
        tradeLayer.setDisable(true);
        infoLayer.setVisible(false);
        infoLayer.setDisable(true);
        buyButton.disableProperty().bind(this.isHoldings.not());
        sellButton.disableProperty().bind(this.isHoldings);
        StockToSearch.textProperty().bind(this.searchProperty);
        nameLabel.textProperty().bind(Bindings.concat(this.userName," Trade page"));
        yesButton.disableProperty().bind(isSure);
        yesButton.visibleProperty().bind(isSure.not());
        noButton.disableProperty().bind(isSure);
        noButton.visibleProperty().bind(isSure.not());
        returnCircle.disableProperty().bind(isSure.not());
        returnCircle.visibleProperty().bind(isSure);
        returnLabel.disableProperty().bind(isSure.not());
        returnLabel.visibleProperty().bind(isSure);

        
        this.orderComponentController.setMainController(this);

        

    }

    public void toggleBuyOrSell(ActionEvent event) {
        Node node = (Node) event.getSource();

        bigButtonsLayer.setVisible(false);
        bigButtonsLayer.setDisable(true);

        tradeLayer.setVisible(true);
        tradeLayer.setDisable(false);

        isHoldings.set(node.equals(bigSellButton) || node.equals(sellButton) );

        this.mainController.cancelCurrentTask();
        clearStocks();
        StockToSearch.positionCaret(0);
        this.searchProperty.set("");
        this.mainController.getFilteredStocks(searchProperty.getValue(), isHoldings.getValue());


    }



    public void setChosenStock(SimpleStringProperty symbol){
        this.orderComponentController.setChosenStock(symbol);
    }

    public void makeOrder(SimpleBooleanProperty isMkt, SimpleStringProperty chosenStockSymbol, StringProperty amount, StringProperty limit) {
        if (!isSure.getValue())showInfo(true);
        else
       try{
           this.mainController.makeOrder(this.isHoldings.getValue(),isMkt.getValue(),chosenStockSymbol.getValue(),Integer.parseInt(amount.getValue()),Float.parseFloat(limit.getValue()));
       }
       catch (Exception e){
           try {
               mainController.makeException(e.getMessage());
           } catch (IOException ioException) {
               ioException.printStackTrace();
           }
       }
    }


    public void showInfo(Boolean successes) {
        tradeLayer.setVisible(false);
        tradeLayer.setDisable(true);

        infoLayer.setVisible(true);
        infoLayer.setDisable(false);
        if (!isSure.getValue()){this.infoLabel.textProperty().set("Are you sure you want to commit the order?");}
        else
        this.infoLabel.textProperty().set(
                successes ?
                        "Request has been completed!" :
                        "Request has yet to be completed,\n" +
                                " our system will continue to try to fulfil the request until the request" +
                                " will be completed "
        );

    }

    public void toggleInfo(MouseEvent mouseEvent) {
        tradeLayer.setVisible(true);
        tradeLayer.setDisable(false);

        infoLayer.setVisible(false);
        infoLayer.setDisable(true);

        isSure.set(false);
    }

    public void setName(String userName) {
        this.userName.set(userName);
    }

    public void agreeAction(ActionEvent event) {
        if(event.getSource().equals(yesButton)) {
            isSure.set(true);
            this.orderComponentController.makeOrder();
        }
            else{
                tradeLayer.setVisible(true);
                tradeLayer.setDisable(false);

                infoLayer.setVisible(false);
                infoLayer.setDisable(true);
            }
        }

    }



