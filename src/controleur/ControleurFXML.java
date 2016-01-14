package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
/**
 * Created by 1494778 on 2016-01-14.
 */
public class ControleurFXML {
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

    }

}
