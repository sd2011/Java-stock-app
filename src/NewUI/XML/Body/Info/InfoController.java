package NewUI.XML.Body.Info;

import NewUI.Data.RequestData;
import NewUI.XML.Body.Stock.scrollLogic;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static NewUI.ResourceManager.INFO_ORDER_FXML_RESOURCE;

public class InfoController  extends scrollLogic {

    @FXML
    public Button buyButton;
    @FXML
    public Button sellButton;
    @FXML
    public Button transactionsButton;
    @FXML
    public Accordion ordersComponent;
    public Label noOrders;
    private String type;
    private String symbol;
    private Map<String,InfoOrderController> orders;

    SimpleBooleanProperty isStock;
    SimpleBooleanProperty isType;
    SimpleBooleanProperty orderSize;

    public InfoController(){
        this.isStock =new SimpleBooleanProperty(false);
        this.isType =new SimpleBooleanProperty(false);
        this.orderSize = new SimpleBooleanProperty(false);
    }

    public void initialize(){

        StockToSearch.textProperty().bind(this.searchProperty);
        orders = new HashMap<>();
        noOrders.visibleProperty().bind(Bindings.and(isStock,isType).and(orderSize.not()));

    }
    
    public void setChosenStock(SimpleStringProperty symbol) {
        if(this.symbol != null) stocks.get(this.symbol).clearbackground();
        this.symbol = symbol.getValue();
        isStock.set(true);
        if(isStock.getValue() && isType.getValue())   getNewOrders();
    }

    public void getNewOrders(){
        this.mainController.cancelCurrentTask();
        this.orderSize.set(false);
        this.orders.clear();
        this.ordersComponent.getPanes().clear();
        this.mainController.getStockInfo(symbol,this.type);
    }

    public void setType(ActionEvent event) {
        this.buyButton.disableProperty().set(false);
        this.sellButton.disableProperty().set(false);
        isType.set(true);
        this.transactionsButton.disableProperty().set(false);
        if(event.getSource().equals(buyButton)) {
            type = "buy";
            this.buyButton.disableProperty().set(true);
        }
        else if(event.getSource().equals(sellButton)){
            type = "sell";
            this.sellButton.disableProperty().set(true);
        }
        else if(event.getSource().equals(transactionsButton)){
            type = "transaction";
            this.transactionsButton.disableProperty().set(true);
        }

        if(isStock.getValue() && isType.getValue())    getNewOrders();
    }

    public void addStockInfo(RequestData requestData) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(INFO_ORDER_FXML_RESOURCE);
        fxmlLoader.setLocation(url);
        Node node = fxmlLoader.load(url.openStream());
        InfoOrderController controller = fxmlLoader.getController();
        controller.setMainController(this);
        setInfoController(requestData,controller);
        orders.put(controller.getDate().getValue(), controller);
        ordersComponent.getPanes().add((TitledPane) node);
        this.orderSize.set(true);
    }

    private void setInfoController(RequestData requestData, InfoOrderController controller) {
        controller.setAmount(requestData.getAmount().getValue());
        controller.setBuyer(requestData.getBuyer().getValue());
        controller.setDate(requestData.getDate().getValue());
        controller.setGateType(requestData.getGateType().getValue());
        controller.setOrderType(requestData.getOrderType().getValue());
        controller.setPrice(requestData.getPrice().getValue());
        controller.setSeller(requestData.getSeller().getValue());
        controller.setBuyerSeller();
    }
}
