package NewUI.XML.Body.UserPage.UserPageButton;

import NewUI.XML.Body.UserPage.UserPageController;
import javafx.event.ActionEvent;

import java.io.IOException;

public class UserPageButtonController {


    private UserPageController mainController;

    public void setMainController(UserPageController mainController) {
        this.mainController = mainController;
    }


    public void makeTradeAction(ActionEvent event) throws IOException {
        this.mainController.makeTrade();
    }
}
