package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Base64ImageFactory;
import model.Context;
import model.Server;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlleur principale pour l'application
 * @author Gabriel_Fabienne
 */
public class AppController extends Application implements Initializable {

    @FXML
    private GridPane root;

    @FXML
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Javafx Start method
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        loadCreateUser(primaryStage);

        primaryStage.setOnCloseRequest(AppController::close);
        primaryStage.getIcons().add(Base64ImageFactory.getInstance().makeFromBase64DataString(Context.getInstance().getAppIcon()));

    }
    private static void close(WindowEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Avertissement");
        alert.setHeaderText("Quitter?");
        alert.setContentText("Voulez-vous quitter le programme de messages instantanés ");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            if(Context.getInstance().getServerThread()!=null)
                Context.getInstance().getServerThread().getServer().close();
            Platform.exit();
            System.exit(0);
        } else event.consume();
    }

    private void loadCreateUser(Stage primaryStage) {
        String path = "/view/createUser.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                path));

        BorderPane root;
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);

            CreateUserController createUserController = fxmlLoader.getController();
            createUserController.setStage(primaryStage);
            primaryStage.setTitle("Best Messenger");
            primaryStage.setScene(scene);

            primaryStage.show();

        } catch (IOException e) {
            System.out.println("ERROR with FXML");
            e.printStackTrace();
        }



    }

    /**
     * Initialize pour ce controlleur
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
