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
 * Controlleur de la Chat box
 * @author Gabriel_Fabienne
 */
public class ChatBoxController implements Initializable, Observer {


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

        clientThread = new ClientThread(new User(ipDistant.getText(), Integer.valueOf(portDistant.getText())));
        clientThread.getClient().addObserver(this);
        clientThread.start();

    }

    @FXML
    void envoyerFichierSource(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select your file");

        File file = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (file != null) {
            if (Context.getInstance().getServerThread().getServer().isConnected()) {
                Context.getInstance().getServerThread().getServer().sendFile(file);
            } else if (clientThread.getClient().isConnected())
                clientThread.getClient().sendFile(file);
        }
    }

    @FXML
    void envoyerMSG(ActionEvent event) {
        if (Context.getInstance().getServerThread().getServer().isConnected()) {
            Context.getInstance().getServerThread().getServer().sendMessage(new Message(Context.getInstance().getUser().getIp(), Context.getInstance().getPort(), messageEnvoyer.getText()));
        } else if (clientThread.getClient().isConnected())
            clientThread.sendMsg(new Message(Context.getInstance().getUser().getIp(), Context.getInstance().getPort(), messageEnvoyer.getText()));
        messageEnvoyer.clear();
    }

    @FXML
    void quitter(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().fireEvent(new WindowEvent(null, WindowEvent.WINDOW_CLOSE_REQUEST));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listViewInfoServeur.setItems(messagesServer);
        listViewConversation.setItems(messagesClient);
        nomUtilisateur.setText(Context.getInstance().getUser().getNom());
        userImg.setImage(Base64ImageFactory.getInstance().makeFromBase64DataString(Context.getInstance().getUser().getImageBase64()));
        System.out.println("IP: " + Context.getInstance().getUser().getIp());
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
        if (arg instanceof FileMessage)
            Platform.runLater(() -> {
                FileMessage fileMessage = (FileMessage) arg;
                fileMessage.writeFile();
                messagesClient.add("fichier reçu:" + fileMessage.getFilename());
            });
        else if (arg instanceof Message) {

            Platform.runLater(() -> {
                Message message = (Message) arg;
                System.out.println(message.getIp());
                System.out.println(message.getPort());
                String txt = "";
                if (message.getIp().equals(Context.getInstance().getUser().getIp()) && Context.getInstance().getPort() == message.getPort()) {
                    txt = Context.getInstance().getUser().getNom() + ": " + message.getContent();
                } else {
                    txt = Context.getInstance().getFriend().getNom() + ": " + message.getContent();
                }
                messagesClient.add(txt);
            });
        } else if (arg instanceof String)
            if (((String) arg).equals(SocketManager.CONNECT)) {
                btnConnection.setDisable(true);
                ipDistant.setDisable(true);
                portDistant.setDisable(true);
            } else
                Platform.runLater(() -> {
                    messagesServer.add(((String) arg));
                });
        else if (arg instanceof User)
            Platform.runLater(() -> {
                User friend = (User) arg;
                nomAmi.setText(friend.getNom());
                avatarAmi.setImage(Base64ImageFactory.getInstance().makeFromBase64DataString(friend.getImageBase64()));
                ipDistant.setText(friend.getIp());
                portDistant.setText(String.valueOf(friend.getPort()));
            });

    }

}
