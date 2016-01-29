package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

/**
 * Controlleur de la fenettre de creation de l'usager
 * @author Gabriel_Fabienne
 */
public class CreateUserController implements Initializable {

    @FXML
    private Button acceptBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextField usernamefield;

    @FXML
    private TextField portfield;

    @FXML
    private ImageView image;
    private Stage stage;

    @FXML
    void accept(ActionEvent event) {
        if(!usernamefield.getText().isEmpty() ){
            int port = 0;
            try{
                port = Integer.valueOf(portfield.getText());
            }catch (Exception e){
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Avertissement");
                alert.setHeaderText("Port invalide");
                alert.setContentText(" Port est invalide. Default utiliser ");
                alert.showAndWait();
            }
            if(port != 0){
                Context.getInstance().setPort(port);
            }
            Context.getInstance().setUser(new User(usernamefield.getText(),((Base64Image)image.getImage()).getBase64(),Context.getInstance().getIp(),Context.getInstance().getPort()));

            loadChatBox(stage);
        }
    }



    @FXML
    void cancel(ActionEvent event) {
        ((Button)event.getSource()).getScene().getWindow().fireEvent(new WindowEvent(null, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    /**
     * Setteur pour le stage
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadChatBox(Stage primaryStage) {
        String path = "/view/chatBox.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                path));
        GridPane root;
        try {
            root = fxmlLoader.load();

            Scene scene = new Scene(root);


            primaryStage.setTitle("Best Messenger");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.out.println("ERROR with FXML");
            e.printStackTrace();
        }



    }

    /**
     * Initialize method for this controler
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        image.fitWidthProperty().bind(((Pane)image.getParent()).widthProperty());
        image.fitHeightProperty().bind(((Pane)image.getParent()).heightProperty());
        image.preserveRatioProperty().setValue(false);
        image.setImage(Base64ImageFactory.getInstance().makeFromBase64DataString(Context.getInstance().getDefaultPicture()));
    }
}
