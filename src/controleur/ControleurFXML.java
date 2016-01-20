package controleur;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import modele.Client;
import modele.Friend;
import modele.Message;
import modele.Server;

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
    private ListView<String> listViewConversation;

    @FXML
    private TextField nomAmi;

    @FXML
    private ImageView avatarAmi;

    @FXML
    private ListView<String> listViewInfoServeur;

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

    private ObservableList<String> messagesServer = FXCollections
            .synchronizedObservableList(FXCollections.observableArrayList());

    private ObservableList<String> messagesClient = FXCollections
            .synchronizedObservableList(FXCollections.observableArrayList());
    private Server server;
    private Client client;
    @FXML
    void connection(ActionEvent event) {
        System.out.println("wut?");
        if(client == null){
            new Thread(()->{
                Friend friend = new Friend(ipDistant.getText(), Integer.valueOf(portDistant.getText()));
                client = new Client(friend);
                client.addObserver(this);
                client.reconnect();
            }).start();

        }

    }

    @FXML
    void envoyerFichierSource(ActionEvent event) {

    }

    @FXML
    void envoyerMSG(ActionEvent event) {
        System.out.println("envoyer");
        if (server.hasConnection()){
            System.out.println("message envoyer au client");
            server.sendMessage(new Message(Message.MESSAGE,messageEnvoyer.getText()));
        }
        else if(client.isConnected()){
            System.out.println("message envoyer au server");
            client.sendMessage(new Message(Message.MESSAGE,messageEnvoyer.getText()));
        }
    }

    @FXML
    void quitter(ActionEvent event) {
        ((Button)event.getSource()).getScene().getWindow().fireEvent(new WindowEvent(null, WindowEvent.WINDOW_CLOSE_REQUEST));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listViewInfoServeur.setItems(messagesServer);
        listViewConversation.setItems(messagesClient);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof Message){
            Platform.runLater(() -> {
                System.out.println("received or sent msg");
                messagesClient.add(((Message) arg).getContent());
            });
        }else
            Platform.runLater(() -> {
                messagesServer.add(((String) arg));
            });

    }

    public void setServer(Server server){
        this.server = server;
    }
}
