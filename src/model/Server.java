package model;

import javafx.collections.ObservableList;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Properties;

/**
 * Created by 0940135 on 2016-01-20.
 */
public class Server extends SocketManager {
    private ServerSocket serverSocket;
    private int port;

    public Server() {
        this.port = Context.getInstance().getPort();
    }

    public Server(int port){
        Context.getInstance().setPort(port);
        this.port = port;
    }
    @Override
    public void start(){
        try {
            serverSocket = new ServerSocket(port);
            display("Server connected to port " + port + " and waiting");
        } catch (IOException e) {
            display("error starting server");
        }
        try {
            socket = serverSocket.accept();
            System.out.println("huh?");
            try {
                openStreams();
                display("Client connected from " + socket.getInetAddress().getHostAddress());
                sendUserInfo(Context.getInstance().getUser());
                startListening();

            } catch (Exception e) {
                display("Exception creating new Input/output Streams: " + e);
                e.printStackTrace();
            }
        } catch (IOException e) {
            display("error creating ServerSocket");
        }
    }
    @Override
    protected void disconnected(){
        super.disconnected();

        try {
            serverSocket.close();
        } catch (Exception e) {}
    }
}
