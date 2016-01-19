package controleur;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by 1494778 on 2016-01-14.
 */
public class ControleurFXML implements Initializable, Observer{
    @FXML
    private TextField ipDistant;

    @FXML
    private TextField portDistant;

    @FXML
    private Button btnConnection;

    @FXML
    private TextField nomUtilisateur;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<?> listViewConversation;

    @FXML
    private TextField nomAmi;

    @FXML
    private ImageView avatarAmi;

    @FXML
    private ListView<?> listViewInfoServeur;

    @FXML
    private TextField messageEnvoyer;

    @FXML
    private Button btnEnvoyer;

    @FXML
    private Button btnQuitter;

    @FXML
    private TextField fichierSource;

    @FXML
    private Button btnEnvoyerFichier;

    @FXML
    void connection(ActionEvent event) {

    }

    @FXML
    void envoyerFichierSource(ActionEvent event) {

    }

    @FXML
    void envoyerMSG(ActionEvent event) {

    }

    @FXML
    void quitter(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Avertissement");
        alert.setHeaderText("Quitter?");
        alert.setContentText("Voulez-vous quitter le programme de messages instantanés ");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
