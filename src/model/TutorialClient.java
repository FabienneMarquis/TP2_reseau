package model;
import java.net.*;
import java.io.*;
import java.util.*;

/*
 * The TutorialClient that can be run both as a console or a GUI
 */
public class TutorialClient extends Observable {

    // for I/O
    private ObjectInputStream sInput;		// to read from the socket
    private ObjectOutputStream sOutput;		// to write on the socket
    private Socket socket;


    // the server, the port and the username
    private String server, username;
    private int port;



    /*
     * Constructor call when used from a GUI
     * in console mode the ClienGUI parameter is null
     */
    TutorialClient(String server, int port, String username) {
        this.server = server;
        this.port = port;
        this.username = username;
        // save if we are in GUI mode or not
    }

    /*
     * To start the dialog
     */
    public boolean start() {
        // try to connect to the server
        try {
            socket = new Socket(server, port);
        }
        // if it failed not much I can so
        catch(Exception ec) {
            display("Error connecting to server:" + ec);
            return false;
        }

        String msg = "Client accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(msg);

		/* Creating both Data Stream */
        try
        {
            sInput  = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return false;
        }

        // creates the Thread to listen from the server
        new ListenFromServer().start();
        // Send our username to the server this is the only message that we
        // will send as a String. All other messages will be ChatMessage objects
        try
        {
            sOutput.writeObject(username);
        }
        catch (IOException eIO) {
            display("Exception doing login : " + eIO);
            disconnect();
            return false;
        }
        // success we inform the caller that it worked
        return true;
    }

    /*
     * To send a message to the console or the GUI
     */
    private void display(String msg) {
        notifyObservers(msg);
    }

    /*
     * To send a message to the server
     */
    void sendMessage(Message msg) {
        try {
            sOutput.writeObject(msg);
        }
        catch(IOException e) {
            display("Exception writing to server: " + e);
        }
    }

    /*
     * When something goes wrong
     * Close the Input/Output streams and disconnect not much to do in the catch clause
     */
    private void disconnect() {
        try {
            if(sInput != null) sInput.close();
        }
        catch(Exception e) {} // not much else I can do
        try {
            if(sOutput != null) sOutput.close();
        }
        catch(Exception e) {} // not much else I can do
        try{
            if(socket != null) socket.close();
        }
        catch(Exception e) {} // not much else I can do

       display("disconnected");

    }
    /*
     * To start the TutorialClient in console mode use one of the following command
     * > java TutorialClient
     * > java TutorialClient username
     * > java TutorialClient username portNumber
     * > java TutorialClient username portNumber serverAddress
     * at the console prompt
     * If the portNumber is not specified 1500 is used
     * If the serverAddress is not specified "localHost" is used
     * If the username is not specified "Anonymous" is used
     * > java TutorialClient
     * is equivalent to
     * > java TutorialClient Anonymous 1500 localhost
     * are eqquivalent
     *
     * In console mode, if an error occurs the program simply stops
     * when a GUI id used, the GUI is informed of the disconnection
     */
    public static void main(String[] args) {
        // default values
        int portNumber = 1500;
        String serverAddress = "localhost";
        String userName = "Anonymous";

        // depending of the number of arguments provided we fall through
        switch(args.length) {
            // > javac TutorialClient username portNumber serverAddr
            case 3:
                serverAddress = args[2];
                // > javac TutorialClient username portNumber
            case 2:
                try {
                    portNumber = Integer.parseInt(args[1]);
                }
                catch(Exception e) {
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java TutorialClient [username] [portNumber] [serverAddress]");
                    return;
                }
                // > javac TutorialClient username
            case 1:
                userName = args[0];
                // > java TutorialClient
            case 0:
                break;
            // invalid number of arguments
            default:
                System.out.println("Usage is: > java TutorialClient [username] [portNumber] {serverAddress]");
                return;
        }
        // create the TutorialClient object
        TutorialClient tutorialClient = new TutorialClient(serverAddress, portNumber, userName);
        // test if we can start the connection to the MyServer
        // if it failed nothing we can do
        if(!tutorialClient.start())
            return;

        // wait for messages from user
        Scanner scan = new Scanner(System.in);
        // loop forever for message from the user
        while(true) {
            System.out.print("> ");
            // read message from user
            String msg = scan.nextLine();
            // logout if message is LOGOUT
            if(msg.equalsIgnoreCase("LOGOUT")) {
                tutorialClient.sendMessage(new Message(Message.LOGOUT, ""));
                // break to do the disconnect
                break;
            }
            // message WhoIsIn
            else if(msg.equalsIgnoreCase("WHOISIN")) {
                tutorialClient.sendMessage(new Message(Message.WHOISIN, ""));
            }
            else {				// default to ordinary message
                tutorialClient.sendMessage(new Message(Message.MESSAGE, msg));
            }
        }
        // done disconnect
        tutorialClient.disconnect();
    }

    /*
     * a class that waits for the message from the server and append them to the JTextArea
     * if we have a GUI or simply System.out.println() it in console mode
     */
    class ListenFromServer extends Thread {

        public void run() {
            while(true) {
                try {
                    String msg = (String) sInput.readObject();
                    // if console mode print the message and add back the prompt
                    display(msg);
                }
                catch(IOException e) {
                    display("MyServer has close the connection: " + e);
                    break;
                }
                // can't happen with a String object but need the catch anyhow
                catch(ClassNotFoundException e2) {
                }
            }
        }
    }
}
