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
 * Server manage all that have to do with the server side aspect of the connection between sockets
 * it extends SocketManage that does most of the heavy lifting
 * @author Gabriel_Fabienne
 */
public class Server extends SocketManager {
    private ServerSocket serverSocket;
    private int port;

    /**
     * Constructor that build the server by getting the port from the context singleton
     */
    public Server() {
        this.port = Context.getInstance().getPort();
    }

    /**
     * Construct the server by using the port provided as an argument.
     * @param port
     */
    public Server(int port){
        Context.getInstance().setPort(port);
        this.port = port;
    }

    /**
     * Overrided start method of SocketManager
     * it start the serverSocket from the port
     * and wait for a socket to connect to it
     * and then open the streams
     * send the user information
     * and advice the listeners that it is now connected and has a socket connected to it
     */
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

                System.out.println("wtf?");
                startListening();
                sendUserInfo(Context.getInstance().getUser());
                display(SocketManager.CONNECT);

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
