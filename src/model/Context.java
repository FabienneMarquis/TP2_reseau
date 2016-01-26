package model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by 0940135 on 2016-01-21.
 */
public class Context {
    private static Context context;
    private User user;
    private ServerThread serverThread;
    private ClientThread clientThread;
    private String defaultPicture = "";
    private final String DEFAULT_PICTURE = "default_picture";
    private int port;
    private final String PORT = "port";
    private String appIcon ="";
    private final String APP_ICON = "app_icon";
    private final String confFile = "conf.txt";

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

    public String getDefaultPicture(){
        if(defaultPicture.length() == 0){
            defaultPicture = getDefaultPictureFromProperties();
        }
        return defaultPicture;
    }

    public int getPort() {
        if(port== 0){
            port = getPortFromProperties();
        }
        return port;
    }

    public String getAppIcon(){
        if(appIcon.length() == 0){
            appIcon = getAppIconFromProperties();
        }
        return appIcon;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private int getPortFromProperties() {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream(confFile);
            properties.load(in);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return Integer.valueOf(properties.getProperty(PORT));
    }

    private String getDefaultPictureFromProperties(){
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream(confFile);
            properties.load(in);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(DEFAULT_PICTURE);
    }

    private String getAppIconFromProperties(){
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream(confFile);
            properties.load(in);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return properties.getProperty(APP_ICON);
    }

}
