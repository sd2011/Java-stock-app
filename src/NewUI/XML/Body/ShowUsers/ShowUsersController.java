package NewUI.XML.Body.ShowUsers;

import NewUI.Data.UserData;
import NewUI.XML.Body.BodyController;
import NewUI.XML.Body.ShowUsers.User.UserController;
import NewUI.XML.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static NewUI.ResourceManager.USER_FXML_RESOURCE;

public class ShowUsersController {

    @FXML
    private FlowPane flowPaneComponent;

    private BodyController mainController;
    private  Map<String, UserController> users;


    public void initialize(){
        this.users = new HashMap<String, UserController>();
    }

    public void setMainController(BodyController mainController) {
        this.mainController = mainController;
    }



    public void addUser(UserData user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(USER_FXML_RESOURCE);
        fxmlLoader.setLocation(url);
        Node node = fxmlLoader.load(url.openStream());
        UserController controller = fxmlLoader.getController();
        controller.setMainController(this);
        controller.setName(user.getName());
        users.put(user.getName(), controller);
        flowPaneComponent.getChildren().add(node);

    }
    
    public void changeToUserPage(SimpleStringProperty name) throws IOException {
        this.mainController.changeToUserPage(name,Controller.page.USER_PAGE);
    }
    
}
