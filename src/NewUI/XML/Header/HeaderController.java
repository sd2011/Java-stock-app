package NewUI.XML.Header;

import NewUI.XML.Controller;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.IOException;

public class HeaderController {
    public Button userPageButton;
    public Button tradePageButton;
    public Button infoPageButton;
    public Button showUsersPageButton;
    private Controller mainController;


    public void initialize(){


    }

    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }


    public void changePageAction(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        if (node.equals(userPageButton)) this.mainController.addPage(Controller.page.USER_PAGE);
        else if (node.equals(tradePageButton)) this.mainController.addPage(Controller.page.TRADE);
        else if(node.equals(infoPageButton)) this.mainController.addPage(Controller.page.INFO);
        else if (node.equals(showUsersPageButton)) this.mainController.addPage(Controller.page.SHOW_USERS);
    }

}
