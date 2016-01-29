package model;

/**
 * Thread qui permet le Client de pas locker l'interface
 * @author Gabriel_Fabienne
 */
public class ClientThread extends Thread{
    Client client;

    /**
     * Creation du thread client.cree un client a partir d'un user
     * @param user
     */
    public ClientThread(User user){
        client = new Client(user);
    }

    /**
     * Methode run du thread
     */
    public void run(){
        client.start();
    }

    /**
     * Methode qui appelle sendMessage du client
     * @param msg
     */
    public void sendMsg(Message msg){
        client.sendMessage(msg);
    }

    /**
     * Get pour avoir l'objet Client
     * @return Client client
     */
    public Client getClient(){
        return client;
    }
}
