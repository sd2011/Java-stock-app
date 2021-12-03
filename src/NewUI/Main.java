package NewUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import NewUI.XML.Controller;

import java.net.URL;

import static NewUI.ResourceManager.APP_FXML_RESOURCE;


public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(APP_FXML_RESOURCE);
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load(url.openStream());


        Controller controller = fxmlLoader.getController();
        Scene scene = new Scene(root, 650, 500);
        primaryStage.setTitle("Rizpa System");
        primaryStage.setScene(scene);
        controller.setPrimaryStage(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(Main.class);
    }
}






