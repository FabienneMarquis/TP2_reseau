package controleur;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by 1494778 on 2016-01-14.
 */
public class Controleur extends Application implements Initializable{
    @FXML
    private GridPane root;

    @FXML
    private Scene scene;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String path = "../vue/vue_tp2.fxml";
       System.out.print(getClass().getResource(
               path));

        root = FXMLLoader.load(getClass().getResource(
                path));
        scene = new Scene(root);

        primaryStage.setTitle("Bibliothèque");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
