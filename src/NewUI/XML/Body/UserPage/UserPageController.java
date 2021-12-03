package NewUI.XML.Body.UserPage;

import NewUI.Data.ItemData;
import NewUI.Data.UserData;
import NewUI.XML.Body.BodyController;
import NewUI.XML.Body.UserPage.UserItem.UserItemController;
import NewUI.XML.Body.UserPage.UserPageButton.UserPageButtonController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleFloatProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static NewUI.ResourceManager.USER_PAGE_ITEM_FXML_RESOURCE;


public class UserPageController extends UserData {

    @FXML
    private Label worthLabel;
    @FXML
    private Accordion accordionComponent;
    private BodyController mainController;
    @FXML private  Label nameLabel;
    @FXML private Pane userPageButtonComponent;
    @FXML
    private UserPageButtonController userPageButtonComponentController;

    private SimpleFloatProperty worth;
    private UserItemController userPageItemComponentController;

    private Map<String, UserItemController> controllers;

    public UserPageController() {
        super("");
        controllers = new HashMap<>();
        this.worth = new SimpleFloatProperty(0);
    }


    public void initialize(){
        nameLabel.textProperty().bind(Bindings.concat(this.name));
        worthLabel.textProperty().bind(Bindings.concat(this.worth));
        this.userPageButtonComponentController.setMainController(this);

    }

    public void setMainController(BodyController mainController) {
        this.mainController = mainController;
    }


    public void addItem(ItemData item) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(USER_PAGE_ITEM_FXML_RESOURCE);
        fxmlLoader.setLocation(url);
        TitledPane pane = fxmlLoader.load(url.openStream());
        UserItemController controller = fxmlLoader.getController();
        controller.setMainController(this);
        controller.setItem(item);
        addToWorth(item);

        controllers.put(item.getSymbol().getValue(), controller);
        accordionComponent.getPanes().add(pane);
    }

    private void addToWorth(ItemData item) {
        float val = item.getGate().getValue() * item.getQuantity().getValue();
        this.worth.set(this.worth.getValue() + val);
    }

    public void makeTrade() throws IOException {
        this.mainController.makeTrade();
    }
}
