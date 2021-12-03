package NewUI.XML.Body.Stock;

import NewUI.Data.StockData;
import NewUI.XML.Body.BodyController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static NewUI.ResourceManager.STOCK_FXML_RESOURCE;

// important note make sure to implement initialize, binding search property

public class scrollLogic {

    @FXML
    protected TextField StockToSearch;
    @FXML
    protected VBox allStocksComponent;

    protected final SimpleBooleanProperty isHoldings;


    protected  SimpleStringProperty searchProperty;
    protected int searchIndex;
    protected int searchPos;
    protected  Boolean isShiftPressed;

    protected Map<String, StockController> stocks;
    protected BodyController mainController;


    public  scrollLogic(){
        isHoldings = new SimpleBooleanProperty(false);
        searchProperty= new SimpleStringProperty("");
        searchIndex = 0;
        searchPos =0;
        isShiftPressed =false;
        this.stocks = new HashMap<String,StockController>();
    }

    public void setMainController(BodyController mainController) {
        this.mainController = mainController;
    }

    public void keyPressed(KeyEvent keyEvent) {


        String str = searchProperty.getValue();
        switch(keyEvent.getCode()){
            case BACK_SPACE :
                if(StockToSearch.getCaretPosition() == 0)
                    break;
                String subString = str.substring(0, str.length() - 1 - searchIndex);
                String newStr =
                        searchIndex > 0 ? subString +
                                str.substring(str.length() -searchIndex)  :
                                subString;
                searchPos = StockToSearch.getCaretPosition();

                this.searchProperty.set(str.length() == 1 ? "" : newStr);
                StockToSearch.positionCaret
                        (searchPos
                                -1
                        );
                break;

            case LEFT:
                if(str.length() - searchIndex > 0){
                    this.searchIndex++;
                }
                break;
            case DOWN:
                searchIndex = 0;
                break;
            case UP:
                searchIndex = str.length();

                break;
            case RIGHT:
                if(searchIndex > 0) searchIndex--;
                break;

            case SHIFT:
                this.isShiftPressed = true;
                break;
            default:

                if (Character.isLetter(keyEvent.getText().charAt(0)) || Character.isDigit(keyEvent.getText().charAt(0))) {
                    searchPos = StockToSearch.getCaretPosition();
                    String chr = isShiftPressed ? keyEvent.getText().toUpperCase() : keyEvent.getText();
                    this.searchProperty.set(str + chr);

                    StockToSearch.positionCaret
                            (searchPos
                                    + 1
                            );
                }
        }
        this.mainController.cancelCurrentTask();

        clearStocks();
       this.mainController.getFilteredStocks(searchProperty.getValue(), isHoldings.getValue());
    }




    public void KeyReleasedAction(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.SHIFT)
            this.isShiftPressed =false;
    }


    protected void clearStocks() {
        allStocksComponent.getChildren().clear();
        this.stocks.clear();
    }

    public void getTextCart(MouseEvent mouseEvent) {
        searchPos = StockToSearch.getCaretPosition();
        searchIndex = searchProperty.getValue().length() - searchPos ;
        StockToSearch.positionCaret(searchPos);
    }


    public void addStock(StockData stock) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(STOCK_FXML_RESOURCE);
        fxmlLoader.setLocation(url);
        Node node = fxmlLoader.load(url.openStream());
        StockController controller = fxmlLoader.getController();
        controller.setMainController(this);
        controller.setSymbol(stock.getSymbol());
        stocks.put(stock.getSymbol().getValue(), controller);
        allStocksComponent.getChildren().add(node);
    }


}
