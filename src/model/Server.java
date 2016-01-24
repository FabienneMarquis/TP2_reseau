package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Properties;

/**
 * Created by 0940135 on 2016-01-20.
 */
public class Server extends Observable {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private int port;
    private boolean keepGoing;

    private void display(String msg) {
        System.out.println(msg);
        setChanged();
        notifyObservers(msg);
    }

    public Server() {
        this.port = getPortFromProperties();
    }

    public Server(int port){
        this.port = port;
    }
    public void start(){


        try {
            serverSocket = new ServerSocket(port);
            display("Server connected to port " + port + " and waiting");
        } catch (IOException e) {
            display("error starting server");
        }
        try {
            clientSocket = serverSocket.accept();
            System.out.println("huh?");
            try {
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                inputStream = new ObjectInputStream(clientSocket.getInputStream());

                System.out.println("wtf?");

                display("Client connected from " + clientSocket.getInetAddress().getHostAddress());

                startListening();

            } catch (Exception e) {
                display("Exception creating new Input/output Streams: " + e);
                e.printStackTrace();
            }
        } catch (IOException e) {
            display("error creating ServerSocket");
        }
    }

    private int getPortFromProperties() {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream("conf.txt");
            properties.load(in);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return Integer.valueOf(properties.getProperty("port"));
    }

    public void close() {
        try {
            if (inputStream != null)
                inputStream.close();
        } catch (IOException e) {
            disconnected(e);
        }// Not much to do
        try {
            if (outputStream != null)
                outputStream.close();
        } catch (IOException e) {
            disconnected(e);
        }// Not much to do
        try {
            if (clientSocket != null)
                clientSocket.close();
        } catch (IOException e) {
            disconnected(e);
        }// Not much to do
        try {
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            disconnected(e);
        }// Not much to do
        //disconnected();
    }

    public void sendMessage(Message message) {
        if (!clientSocket.isClosed()) {
            try {
                outputStream.writeObject(message);
                setChanged();
                notifyObservers(message);
            } catch (IOException e) {
                disconnected(e);
            }
        }
    }

    private void startListening() {


        new Thread(() -> {
            while (!clientSocket.isClosed()) {

                try {
                    System.out.println("server received msg");
                    Object obj = inputStream.readObject();
                    if(obj instanceof User)
                        sendUserInfo();
                    display(obj);

                } catch (IOException e) {
                    // Server disconnected
                    disconnected(e);
                } catch (ClassNotFoundException e) {
                    // Not much to do...
                }

            }
        }).start();


    }

    private void disconnected(Exception e) {
        try{
            clientSocket.close();
        }catch (IOException e1) {}
        try{
            outputStream.close();
        }catch (IOException e1) {}
        try{
            inputStream.close();
        }catch (IOException e1) {}
        setChanged();
        notifyObservers("DISCONNECTED: " + e);

    }

    public boolean hasConnection() {
        return clientSocket != null;
    }

    private void display(Object object){
        System.out.println(object);
        if(object instanceof User || object instanceof Message || object instanceof String){
            setChanged();
            notifyObservers(object);
        }
    }


    private void sendUserInfo(){
        try{
            outputStream.writeObject(Context.getInstance().getUser());
        }catch (IOException e){
            System.out.println("ERROR: connection");
        }

    }
}
