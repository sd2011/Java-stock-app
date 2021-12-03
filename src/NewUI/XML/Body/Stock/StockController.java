package NewUI.XML.Body.Stock;

import NewUI.Data.StockData;
import NewUI.XML.Body.Info.InfoController;
import NewUI.XML.Body.Trade.TradeController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class StockController extends StockData {

    public BorderPane stockComponent;
    @FXML
    private Label symbolLabel;
    private TradeController tradeController;
    private InfoController infoController;

    public StockController() {
        super("");
        tradeController =null;
        infoController = null;
    }

    public void initialize(){

        this.symbolLabel.textProperty().bind(Bindings.concat(this.symbol));
    }

    public void setMainController(Object controller) {
        if(controller.getClass().equals(TradeController.class))
                this.tradeController = (TradeController) controller;
        else
                this.infoController =(InfoController) controller;

    }


    public void setChosenSymbol(MouseEvent mouseEvent) {
        if(tradeController != null )
            this.tradeController.setChosenStock(this.symbol);
        else{
            this.stockComponent.setStyle("-fx-background-color: darkblue");
            this.infoController.setChosenStock(this.symbol);
        }
    }

    public void clearbackground(){
        this.stockComponent.setStyle("-fx-background-color: dodgerblue");
    }
}
