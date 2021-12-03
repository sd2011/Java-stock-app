package NewUI.XML.Body.ShowUsers.User;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import NewUI.Data.UserData;
import NewUI.XML.Body.ShowUsers.ShowUsersController;

import java.io.IOException;

public class UserController extends UserData {
    private ShowUsersController mainController;
    @FXML private  Label nameLabel;

    public UserController() {
        super("");
    }


    public void initialize(){
        nameLabel.textProperty().bind(Bindings.concat(this.name));
    }

    public void setMainController(ShowUsersController mainController) {
        this.mainController = mainController;
    }


    public void changeToUserPage(ActionEvent event) throws IOException {
        this.mainController.changeToUserPage(this.name);
    }
}
