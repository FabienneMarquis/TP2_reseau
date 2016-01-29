package model;

/**
 * Server thread make a thread to run the server without blocking the app main thread
 * @author Gabriel_Fabienne
 */
public class ServerThread extends Thread{
    Server server;
    public ServerThread(Server server){
        this.server = server;
    }

    /**
     * thread run
     */
    public void run(){
        server.start();
    }

    /**
     *
     * @return the server
     */
    public Server getServer() {
        return server;
    }
}
