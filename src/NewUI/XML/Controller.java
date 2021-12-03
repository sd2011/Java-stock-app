package NewUI.XML;

import NewUI.Data.ItemData;
import NewUI.Data.RequestData;
import NewUI.Data.StockData;
import NewUI.Data.UserData;
import NewUI.UIAdapter;
import NewUI.XML.Body.BodyController;
import NewUI.XML.Header.HeaderController;
import System.SystemProxy;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    public ScrollPane scroller;
    private SystemProxy prox;
    private boolean isLoaded;
    private String currentUser;
    private Task<Boolean> currentTask;

    public void cancelCurrentTask() {
        currentTask.cancel();
    }

    public void addPage(page pageToAdd) throws IOException {

        switch (pageToAdd) {
            case USER_PAGE:
                this.bodyComponentController.changeToUserPage(new SimpleStringProperty(currentUser), pageToAdd);
                break;
            case TRADE:
                this.bodyComponentController.makeTrade();
                break;

            case SHOW_USERS:
                showUsers();
                break;

            case INFO:
                this.bodyComponentController.addPage(page.INFO);
                getFilterdStocks("",true);
        }
    }

    public page getCurrentPage() {
        return this.currentPage;
    }

    public void setScroll(double width) {
        this.scroller.prefWidthProperty().set(width);
    }

    public String getCurrentUser() {
        return this.currentUser;
    }


    public static enum page {
        START,
        USER_PAGE,
        TRADE,
        SHOW_USERS,
        INFO,
        EXCEPTION
    }

    private page currentPage;


    @FXML
    private ToolBar headerComponent;
    @FXML
    private HeaderController headerComponentController;
    @FXML
    private Pane bodyComponent;
    @FXML
    private BodyController bodyComponentController;

    private SimpleBooleanProperty isUserActive;

    public Controller(){

    }

    @FXML
    public void initialize() throws IOException {
        this.prox = new SystemProxy();
        this.isLoaded = false;
        currentUser = null;
        isUserActive = new SimpleBooleanProperty(false);


        this.headerComponent.disableProperty().bind(this.isUserActive.not());

        if (headerComponentController != null && bodyComponentController != null ) {
            headerComponentController.setMainController(this);
            bodyComponentController.setMainController(this);
        }
        //starting start
        this.bodyComponentController.addPage(page.START);



       // bodyComponentController.setPage("START");
    }

    public Pane getBodyComponent() {
        return this.bodyComponent;
    }

    public void setCurrentPage(page page) {
        this.currentPage = page;
    }



    public void setPrimaryStage(Stage primaryStage) {
        this.bodyComponentController.setPrimaryStage(primaryStage);
    }

    public void readFile(String file) {

            UIAdapter<Task> ui = new UIAdapter<Task>(whatToDo ->{
                try {
                    if (whatToDo != null) {
                        this.bodyComponentController.bindProgress(whatToDo);
                    } else {
                        showUsers();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, prox,error -> {
                try {
                    cancelCurrentTask();
                    this.bodyComponentController.makeException(error);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            currentTask =  ui.loadFile(file);

            this.isLoaded = true;


        //need to change

    }

    public void showUsers() throws IOException {
        this.bodyComponentController.addPage(page.SHOW_USERS);
        getUsers();
    }

    private void getUsers() {
        UIAdapter<UserData> ui = new UIAdapter<UserData>(user ->{
            try {
                bodyComponentController.addUser((UserData) user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, prox,error -> {
            try {
                cancelCurrentTask();
                this.bodyComponentController.makeException(error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } );
      currentTask = ui.getUsers();
    }


    public void addUserHoldings(SimpleStringProperty name) {
        this.currentUser = name.getValue();
        this.isUserActive.set(true);
        UIAdapter<ItemData> ui = new UIAdapter<ItemData>(
                Item -> {
                    try {
                        this.bodyComponentController.addItem(Item);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        ,prox,error -> {
            try {
                cancelCurrentTask();
                this.bodyComponentController.makeException(error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        currentTask = ui.getHoldings(name.getValue());
    }

    public void getFilterdStocks(String value, boolean isSell) {
        UIAdapter<StockData> ui = new UIAdapter<StockData>(
                stockData -> {
                    try {
                        this.bodyComponentController.addStock(stockData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },prox,error -> {
            try {
                cancelCurrentTask();
                this.bodyComponentController.makeException(error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        currentTask = ui.getFilterdStocks(value,isSell,this.currentUser);

    }
    public void makeOrder(Boolean isSell, Boolean isMkt, String symbol, int amount, float limit) {
        UIAdapter<Boolean> ui = new UIAdapter<Boolean>(
                successes -> {
                    try {
                        this.bodyComponentController.informAction(successes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },prox,error -> {
            try {
                cancelCurrentTask();
                this.bodyComponentController.makeException(error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        currentTask = ui.makeOrder(isSell,isMkt,symbol,amount,limit,this.currentUser);

    }

    public void getStockInfo(String symbol, String type) {
        UIAdapter<RequestData> ui = new UIAdapter<RequestData>(
                requestData -> {
                    try {
                        this.bodyComponentController.addStockInfo(requestData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                },prox,error -> {
            try {
                cancelCurrentTask();
                this.bodyComponentController.makeException(error);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        currentTask = ui.getStockInfo(symbol,type);

    }

}
