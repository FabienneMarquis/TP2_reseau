package controleur;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import modele.Server;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by 1494778 on 2016-01-14.
 */
public class Controleur extends Application implements Initializable{
    Server server;
    @FXML
    private GridPane root;

    @FXML
    private Scene scene;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        String path = "/vue/Vue_tp2.fxml";
        System.out.println(getClass());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                path));
        root = fxmlLoader.load();


        scene = new Scene(root);
        server = new Server();
        new Thread(()->{

            server.start();
        }).start();
        server.addObserver(fxmlLoader.getController());

        ((ControleurFXML)fxmlLoader.getController()).setServer(server);
        primaryStage.setTitle("Best Messenger");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent event)->{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Quitter?");
            alert.setContentText("Voulez-vous quitter le programme de messages instantanés ");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){
                server.close();
                Platform.exit();
                System.exit(0);
            }
            else event.consume();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
