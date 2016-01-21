package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import model.Client;
import model.User;
import model.Message;
import model.Server;
import model.ClientThread;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by 1494778 on 2016-01-14.
 */
public class ChatBoxController implements Initializable, Observer{


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
    private ClientThread clientThread;
    @FXML
    void connection(ActionEvent event) {
        System.out.println("wut?");
//        if(client == null){
//            new Thread(()->{
//                User friend = );
//                client = new Client(friend);
//                client.addObserver(this);
//                client.reconnect();
//            }).start();
//
//        }
        clientThread = new ClientThread(new User(ipDistant.getText(), Integer.valueOf(portDistant.getText())));
        clientThread.getClient().addObserver(this);
        clientThread.start();

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
        else if(clientThread.getClient().isConnected())
            clientThread.sendMsg(new Message(Message.MESSAGE,messageEnvoyer.getText()));
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
