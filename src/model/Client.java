package model;

import javafx.collections.ObservableList;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Observable;

/**
 * Client manage all that have to do with the Client side aspect of the connection between sockets
 * it extends SocketManage that does most of the heavy lifting
 * @author Gabriel_Fabienne
 */
public class Client extends SocketManager{

    private User user;

    /**
     * Constructeur qui prend User pour faire l'instance
     * le User est l'ami sur lequel on va devoir ce connecter
     *
     * @param user
     */
    public Client(User user) {
        this.user = user;
    }

    /**
     * Methode start du thread
     * ouvre le socket avec un serveur avec l'ip et le port qui lui est donner par l'attribut user
     * ouvre l'outputStream et l'inputStream
     * envoie les info User de l'usager de l'application au serveur connecter
     * commence l'ecoute des streams
     * avise qui ecoute la classe un message pour signaler la connection
     * si la connection echoue un message d'erreur et afficher et la deconnection et lancer pour fermer les streams
     * ou le serveur si possible.
     *
     */
    @Override
    public void start() {
        try {
            socket = new Socket(user.getIp(), user.getPort());
            openStreams();
            sendUserInfo(Context.getInstance().getUser());
            startListening();
            display(SocketManager.CONNECT);

            String msg = "Client accepted " + socket.getInetAddress() + ":" + socket.getPort();
            display(msg);
        }
        // if it failed not much I can so
        catch (Exception ec) {
            display("Error connecting to server:" + ec);
            disconnected();
        }
    }

}
