package NewUI.XML.Body.UserPage.UserItem;

import NewUI.Data.ItemData;
import NewUI.XML.Body.UserPage.UserPageController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;

public class UserItemController extends ItemData {

    @FXML
    private Label symbolLabel;

    @FXML
    private Label gateLabel;

    @FXML
    private Label quantityLabel;

    @FXML
    private TitledPane titleLabel;
    private UserPageController mainController;

    public UserItemController() {
        super("",-1,-1);
    }

    public void setMainController(UserPageController mainController) {
        this.mainController = mainController;
    }

    public void initialize(){

        this.titleLabel.textProperty().bind(Bindings.concat(this.symbol));
        this.gateLabel.textProperty().bind(Bindings.concat(this.stockGate));
        this.quantityLabel.textProperty().bind(Bindings.concat(this.quantity));
        this.symbolLabel.textProperty().bind(Bindings.concat(this.symbol));
    }


}
