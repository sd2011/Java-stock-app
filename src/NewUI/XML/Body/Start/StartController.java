package NewUI.XML.Body.Start;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import NewUI.XML.Body.BodyController;

import java.io.File;

public class StartController {
    public Pane startLayer;
    public Pane loadingLayer;
    public Label progressLabel;
    public ProgressBar progressbar;
    private BodyController mainController;
    private Stage primaryStage;
    private Button loadButton;

    private final SimpleBooleanProperty isLoading;

    public StartController(){
        this.isLoading = new SimpleBooleanProperty(false);
    }

    public void initialize(){
        this.startLayer.visibleProperty().bind(this.isLoading.not());
        this.startLayer.disableProperty().bind(this.isLoading);

        this.loadingLayer.visibleProperty().bind(this.isLoading);
    }


    @FXML
    private void LoadFileListener(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        this.isLoading.set(true);
        String absolutePath = selectedFile.getAbsolutePath();
        mainController.readFile(absolutePath);
    }

    public void setMainController(BodyController mainController) {
        this.mainController = mainController;
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void bindProgress(Task task) {
        this.progressLabel.textProperty().bind(task.messageProperty());
        this.progressbar.progressProperty().bind(task.progressProperty());
    }
}
