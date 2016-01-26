package model;

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
        this.port = Context.getInstance().getPort();
    }

    public Server(int port){
        Context.getInstance().setPort(port);
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
                outputStream.flush();
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
            outputStream.flush();
        }catch (IOException e){
            System.out.println("ERROR: connection");
        }

    }

    public void sendFile(File file){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] buffer = new byte[1024];
            while (true) {
                int r;
                try{
                    r = fileInputStream.read(buffer);
                }catch (IOException e){
                    break;
                }

                if (r == -1) break;
                out.write(buffer, 0, r);
            }
        }catch (IOException e){
            e.printStackTrace();
        }




        byte[] ret = out.toByteArray();

        String base64 = DatatypeConverter.printBase64Binary(ret);

        String filename = file.toPath().getFileName().toString();
        try {
            outputStream.writeObject(new FileMessage(Context.getInstance().getUser().getIp(),base64,filename));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
