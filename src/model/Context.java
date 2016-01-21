package model;

/**
 * Created by 0940135 on 2016-01-21.
 */
public class Context {
    private static Context context;
    private User user;
    private ServerThread serverThread;
    private ClientThread clientThread;
    private Context(){

    }

    public static Context getInstance(){
        if(context == null){
            context = new Context();
        }
        return context;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServerThread getServerThread() {
        return serverThread;
    }

    public void setServerThread(ServerThread serverThread) {
        this.serverThread = serverThread;
    }

    public ClientThread getClientThread() {
        return clientThread;
    }

    public void setClientThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }
}
