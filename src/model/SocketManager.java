package model;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.Socket;
import java.util.Observable;

/**
 * this abstract class deal with the shared functionnality of the Server and the Client
 * @author Gabriel_Fabienne
 */
public abstract class SocketManager extends Observable {
    protected Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    public static String CONNECT = "connect";


    public SocketManager() {

    }

    /**
     * Method that need to be written for the Client or the Server
     */
    public abstract void start();

    protected void openStreams() throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Close the sockets and the streams
     */
    public void close() {
        disconnected();
    }

    /**
     * Send the User Object by the OutputStream if a error occurs we close the connection.
     * @param user
     */
    public void sendUserInfo(User user) {
        if (isConnected()) {
            try {
                System.out.println(user.getImageBase64());
                //outputStream.flush();
                outputStream.writeObject(user);
                outputStream.flush();
            } catch (IOException e) {
                disconnected();
            }
        }
    }

    /**
     * Send a Message Object by the OutputStream if an error occurs we close the connection.
     * @param message
     */
    public void sendMessage(Message message) {
        if (isConnected()) {
            try {
                System.out.println(message.getContent());
                //outputStream.flush();
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

    protected void startListening() {

        new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    System.out.println("client received msg");
                    Object object = (Object) inputStream.readObject();
                    if(object instanceof User){
                        System.out.println("Friend: " + ((User) object).getNom());
                        Context.getInstance().setFriend((User) object);
                    }
                    display(object);
                } catch (IOException e) {
                    // Server disconnected
                    disconnected();
                } catch (ClassNotFoundException e) {
                    // Not much to do...
                    System.out.println("class not found");
                }

            }
        }).start();


    }

    protected void disconnected() {
        try {
            socket.close();
        } catch (Exception e) {
        }
        try {
            outputStream.close();
        } catch (Exception e) {
        }
        try {
            inputStream.close();
        } catch (Exception e) {
        }
        display("DISCONNECTED");
    }

    /**
     *
     * @return true if the socket is connected
     */
    public boolean isConnected() {
        return socket!=null?socket.isConnected():false;
    }

    protected void display(Object object) {
        System.out.println(object);
        setChanged();
        notifyObservers(object);
    }

    /**
     * Send a file via the Sockets by reading it and making a FileMessage and writing to the socket
     * @param file
     */
    public void sendFile(File file) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] buffer = new byte[1024];
            while (true) {
                int r;
                try {
                    r = fileInputStream.read(buffer);
                } catch (IOException e) {
                    break;
                }

                if (r == -1) break;
                out.write(buffer, 0, r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        byte[] ret = out.toByteArray();

        String base64 = DatatypeConverter.printBase64Binary(ret);

        String filename = file.toPath().getFileName().toString();
        try {
            outputStream.writeObject(new FileMessage(Context.getInstance().getUser().getIp(),Context.getInstance().getPort(), base64, filename));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
