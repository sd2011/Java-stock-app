package NewUI.XML.Body;

import NewUI.Data.ItemData;
import NewUI.Data.RequestData;
import NewUI.Data.StockData;
import NewUI.Data.UserData;
import NewUI.XML.Body.ShowUsers.ShowUsersController;
import NewUI.XML.Body.Start.StartController;
import NewUI.XML.Body.Trade.TradeController;
import NewUI.XML.Body.UserPage.UserPageController;
import NewUI.XML.Body.Info.InfoController;
import NewUI.XML.Controller;
import NewUI.XML.Body.Exception.ExceptionController;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static NewUI.ResourceManager.*;


public class BodyController {

    @FXML
    public FlowPane bodyCenter;
    @FXML
    private Pane startComponent;
    @FXML
    private StartController startComponentController;

    @FXML
    private BorderPane showUsersComponent;
    @FXML
    private ShowUsersController showUsersComponentController;

    @FXML
    private GridPane userPageComponent;
    @FXML
    private UserPageController userPageComponentController;

    @FXML
    private Pane tradeComponent;
    @FXML
    private TradeController tradeComponentController;

    private Pane infoComponent;
    @FXML
    private InfoController infoComponentController;

    private Controller mainController;
    private ExceptionController exceptionComponentController;


    //
    //basic
    //
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    public Object makeNodeAndWireController(String resource) throws IOException {
        try{
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(resource);
        fxmlLoader.setLocation(url);

        Node node = fxmlLoader.load(url.openStream());

            bodyCenter.getChildren().clear();
            bodyCenter.getChildren().add(node);
        return fxmlLoader.getController();
    } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public void addPage(Controller.page pageToAdd) throws IOException {
        switch(pageToAdd) {
            case START:
                this.startComponentController = (StartController) makeNodeAndWireController(START_FXML_RESOURCE);
                this.startComponentController.setMainController(this);
                break;

            case SHOW_USERS:
                this.showUsersComponentController = (ShowUsersController) makeNodeAndWireController(SHOW_USERS_FXML_RESOURCE);
                this.showUsersComponentController.setMainController(this);
                break;

            case USER_PAGE:
                this.userPageComponentController = (UserPageController) makeNodeAndWireController(USER_PAGE_FXML_RESOURCE);
                this.userPageComponentController.setMainController(this);
                break;

            case TRADE:
                this.tradeComponentController = (TradeController) makeNodeAndWireController(TRADE_FXML_RESOURCE);
                this.tradeComponentController.setMainController(this);
                break;

            case INFO:
                this.infoComponentController = (InfoController) makeNodeAndWireController(INFO_FXML_RESOURCE);
                this.infoComponentController.setMainController(this);
                break;

            case EXCEPTION:
                this.exceptionComponentController = (ExceptionController) makeNodeAndWireController(Exception_FXML_RESOURCE);
                this.exceptionComponentController.setMainController(this);
                return;

            default:
                throw new IllegalStateException("Unexpected value: " + pageToAdd);
        }
        this.mainController.setCurrentPage(pageToAdd);
    }

    public void cancelCurrentTask() {
        this.mainController.cancelCurrentTask();
    }

    //
    //page = start page
    //
    public void setPrimaryStage(Stage primaryStage) {
        this.startComponentController.setPrimaryStage(primaryStage);
    }
    public void readFile(String file) {
        mainController.readFile(file);
    }
    //
    // page = show users page
    //
    public void addUser(UserData user) throws IOException { showUsersComponentController.addUser(user); }
    public void changeToUserPage(SimpleStringProperty name , Controller.page page) throws IOException {
       addPage(page);
        userPageComponentController.setName(name.getValue());
        this.mainController.addUserHoldings(name);
    }
    //
    //page = user page
    //
    public void addItem(ItemData item) throws IOException {
        userPageComponentController.addItem(item);
    }

    public void makeTrade() throws IOException {
        addPage(Controller.page.TRADE);
        tradeComponentController.setName(this.mainController.getCurrentUser());

    }

    //
    //page = trade page
    //
    public void getFilteredStocks(String value, boolean isSell) {
        this.mainController.getFilterdStocks(value,isSell);
    }

    public void addStock(StockData stock) throws IOException {
        if (this.mainController.getCurrentPage() == Controller.page.TRADE) {
            tradeComponentController.addStock(stock);
        } else {
            infoComponentController.addStock(stock);
        }
    }

    public void makeOrder(Boolean isSell, Boolean isMkt, String symbol, int amount, float limit) {
        this.mainController.makeOrder(isSell,isMkt,symbol,amount,limit);
    }

    public void informAction(Boolean successes) throws IOException {
      this.tradeComponentController.showInfo(successes);
    }
    //page = info page
    public void getStockInfo(String symbol, String type) {
        this.mainController.getStockInfo(symbol,type);
    }

    public void addStockInfo(RequestData requestData) throws IOException {
        this.infoComponentController.addStockInfo(requestData);
    }

    public void bindProgress(Task task) {
        this.startComponentController.bindProgress(task);
    }

    public void makeException(String error) throws IOException {
        addPage(Controller.page.EXCEPTION);
        this.exceptionComponentController.setError(error);
    }

    public void returnToPage() {
        try {
            addPage(this.mainController.getCurrentPage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
