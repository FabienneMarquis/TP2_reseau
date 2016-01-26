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
 * Created by Fabienne et Gabriel on 2016-01-14.
 */
public class Client extends SocketManager{

    private User user;


    public Client(User user) {
        this.user = user;
    }

    @Override
    public void start() {
        try {
            socket = new Socket(user.getIp(), user.getPort());
            openStreams();
            sendUserInfo(Context.getInstance().getUser());
            startListening();
        }
        // if it failed not much I can so
        catch (Exception ec) {
            display("Error connecting to server:" + ec);
            disconnected();
        }
        String msg = "Client accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);
    }

}
