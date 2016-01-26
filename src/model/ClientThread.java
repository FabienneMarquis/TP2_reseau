package model;

/**
 * Created by 0940135 on 2016-01-21.
 */
public class ClientThread extends Thread{
    Client client;
    public ClientThread(User user){
        client = new Client(user);
    }
    public void run(){
        client.start();
    }
    public void sendMsg(Message msg){
        client.sendMessage(msg);
    }
    public Client getClient(){
        return client;
    }
}
