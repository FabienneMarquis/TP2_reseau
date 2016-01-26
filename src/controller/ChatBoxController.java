package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import model.*;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private ImageView userImg;

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
    private Button btnEnvoyerFichier;

    private ObservableList<String> messagesServer = FXCollections
            .synchronizedObservableList(FXCollections.observableArrayList());

    private ObservableList<String> messagesClient = FXCollections
            .synchronizedObservableList(FXCollections.observableArrayList());

    private ClientThread clientThread;
    @FXML
    void connection(ActionEvent event) {
        System.out.println("wut?");

        clientThread = new ClientThread(new User(ipDistant.getText(), Integer.valueOf(portDistant.getText())));
        clientThread.getClient().addObserver(this);
        clientThread.start();

    }

    @FXML
    void envoyerFichierSource(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select your file");

        File file = fileChooser.showOpenDialog(((Button)event.getSource()).getScene().getWindow());
        if (Context.getInstance().getServerThread().getServer().hasConnection()){
            Context.getInstance().getServerThread().getServer().sendFile(file);
        }
        else if(clientThread.getClient().isConnected())
            clientThread.getClient().sendFile(file);
    }

    @FXML
    void envoyerMSG(ActionEvent event) {
        System.out.println("envoyer");
        if (Context.getInstance().getServerThread().getServer().hasConnection()){
            System.out.println("message envoyer au client");
            Context.getInstance().getServerThread().getServer().sendMessage(new Message(Context.getInstance().getUser().getIp(), messageEnvoyer.getText()));
        }
        else if(clientThread.getClient().isConnected())
            clientThread.sendMsg(new Message(Context.getInstance().getUser().getIp(),messageEnvoyer.getText()));
    }

    @FXML
    void quitter(ActionEvent event) {
        ((Button)event.getSource()).getScene().getWindow().fireEvent(new WindowEvent(null, WindowEvent.WINDOW_CLOSE_REQUEST));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listViewInfoServeur.setItems(messagesServer);
        listViewConversation.setItems(messagesClient);
        nomUtilisateur.setText(Context.getInstance().getUser().getNom());
        userImg.setImage(Base64ImageFactory.getInstance().makeFromBase64DataString(Context.getInstance().getUser().getImageBase64()));
        System.out.println("IP: " +Context.getInstance().getUser().getIp());
        Context.getInstance().setServerThread(
                new ServerThread(
                        Context.getInstance().getUser().getPort() == 0 ?
                                new Server() :
                                new Server(Context.getInstance().getUser().getPort())
                )
        );
        Context.getInstance().getServerThread().getServer().addObserver(this);
        Context.getInstance().getServerThread().start();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof FileMessage)
            Platform.runLater(()->{
                FileMessage fileMessage = (FileMessage) arg;
                fileMessage.writeFile();
                messagesClient.add("fichier reçu:" + fileMessage.getFilename());
            });
        else if(arg instanceof Message){

            Platform.runLater(() -> {
                String message = ((Message)arg).getContent();
                if(o instanceof Client){
                    message = "<"+message;
                }else if(o instanceof Server){
                    message = ">"+message;
                }
                messagesClient.add(message);
            });
        }else if(arg instanceof String)
            Platform.runLater(() -> {
                messagesServer.add(((String) arg));
            });
        else if(arg instanceof User)
            Platform.runLater(()->{
                User friend = (User) arg;
                nomAmi.setText(friend.getNom());
                avatarAmi.setImage(Base64ImageFactory.getInstance().makeFromBase64DataString(friend.getImageBase64()));
            });

    }

}
