package modele;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Fabienne et Gabriel on 2016-01-14.
 */
public class Connection {
    private ServerSocket serverSocket;
    private Socket socket;

    public Socket(int portAmi) throws IllegalArgumentException, IOException{
        sSocket = new ServerSocket(portAmi);  }

    public ServerSocket getsSocket() {
        return sSocket;
    }

    public void setsSocket(ServerSocket sSocket) {
        this.sSocket = sSocket;
    }
    public void testConnection(){

    }
}
