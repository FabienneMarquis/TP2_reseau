package model;

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
public class Client extends Observable{

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private User user;
    private boolean connected;

    public Client(User user) {
        this.user = user;
        connected = false;
    }

    private boolean connect() {
        try {
            socket = new Socket(user.getIp(), user.getPort());
        }
        // if it failed not much I can so
        catch (Exception ec) {
            display("Error connecting to server:" + ec);
            return false;
        }

        String msg = "Client accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

		/* Creating both Data Stream */
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());


        } catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return false;
        }
        sendUserInfo(Context.getInstance().getUser());
        // creates the Thread to listen from the server
        startListening();

        // success we inform the caller that it worked
        return true;

    }

    public void reconnect() {
        connect();
    }


    public void close() {
        try {
            inputStream.close();
        } catch (IOException e) {
            disconnected();
        }// Not much to do
        try {
            outputStream.close();
        } catch (IOException e) {
            disconnected();
        }// Not much to do
        try {
            socket.close();
        } catch (IOException e) {
            disconnected();
        }// Not much to do
        disconnected();
    }

    public void sendUserInfo(User user){
        if (isConnected()) {
            try {
                System.out.println(user.getNom());
                outputStream.writeObject(user);
                outputStream.flush();
            } catch (IOException e) {
                System.out.println("outputStream closed");
                disconnected();
            }
        }
    }
    public void sendMessage(Message message) {
        if (isConnected()) {
            try {
                System.out.println(message.getContent());
                outputStream.writeObject(message);
                outputStream.flush();
                setChanged();
                notifyObservers(message);
            } catch (IOException e) {
                System.out.println("outputStream closed");
                disconnected();
            }
        }
    }

    private void startListening() {

        new Thread(() -> {
            while (!socket.isClosed()) {

                try {
                    System.out.println("client received msg");
                    Object object = (Object) inputStream.readObject();
                    display(object);
                } catch (IOException e) {
                    // Server disconnected
                    disconnected();
                } catch (ClassNotFoundException e) {
                    // Not much to do...
                }

            }
        }).start();


    }

    private void disconnected() {
        try{
            socket.close();
        }catch (IOException e1) {}
        try{
            outputStream.close();
        }catch (IOException e1) {}
        try{
            inputStream.close();
        }catch (IOException e1) {}
        setChanged();
        notifyObservers("DISCONNECTED");
    }

    public boolean isConnected() {

        return !socket.isClosed();
    }

    private void display(Object object) {
        System.out.println(object);
        setChanged();
        notifyObservers(object);
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
