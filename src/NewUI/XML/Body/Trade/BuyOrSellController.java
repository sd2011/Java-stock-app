package NewUI.XML.Body.Trade;

import NewUI.XML.Body.BodyController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BuyOrSellController {
    private BodyController mainController;
    @FXML
    private Button buyButton;
    @FXML
    private Button sellButton;

    public void setMainController(BodyController mainController) {
        this.mainController = mainController;
    }

}