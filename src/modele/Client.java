package modele;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;

/**
 * Created by Fabienne et Gabriel on 2016-01-14.
 */
public class Client extends Observable {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Friend friend;
    private boolean connected;

    public Client(Friend friend) {
        this.friend = friend;
        connected = false;
    }

    private boolean connect() {
        try {
            socket = new Socket(friend.getIp(), friend.getPort());
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
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

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

    public void sendMessage(Message message) {
        if (isConnected()) {
            try {
                System.out.println(message.getContent());
                outputStream.writeObject(message);
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
                    notifyObservers(inputStream.readObject());
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
        setChanged();
        notifyObservers("DISCONNECTED");
    }

    public boolean isConnected() {

        return !socket.isClosed();
    }

    private void display(String msg) {
        setChanged();
        notifyObservers(msg);
    }

}
