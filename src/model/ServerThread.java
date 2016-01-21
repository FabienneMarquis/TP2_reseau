package model;

/**
 * Created by 0940135 on 2016-01-21.
 */
public class ServerThread extends Thread{
    Server server;
    public ServerThread(Server server){
        this.server = server;
    }
    public void run(){
        server.start();
    }

    public Server getServer() {
        return server;
    }
}
