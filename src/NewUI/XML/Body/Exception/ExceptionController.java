package NewUI.XML.Body.Exception;

import NewUI.XML.Body.BodyController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class ExceptionController {
    @FXML
    private Label errorLabel;
    private SimpleStringProperty error;
    private BodyController mainController;

    public ExceptionController(){
        this.error = new SimpleStringProperty("");
    }

    public void initialize(){
        this.errorLabel.textProperty().bind(Bindings.concat("Sorry! there seem to be a problem:\n",
                this.error,"\n Click the button to return back to the page you ware. "));
    }

    public void setError(String error){
        this.error.set(error);
    }
    public void setMainController(BodyController mainController){
        this.mainController =mainController;
    }

    public void returnToPage(){
        this.mainController.returnToPage();
    }
}
