package model;

import java.io.Serializable;

/**
 * this class is serialize to send User information (his name, image in base64 and his ip) via a socket.
 * @author Gabriel_Fabienne
 */
public class User implements Serializable {
    protected static final long serialVersionUID = 1112122200L;
    private String nom,imageBase64,ip;
    private int port;

    /**
     * basic constructor that need a name, an base64 String, an ip and a port
     * @param nom
     * @param imageBase64
     * @param ip
     * @param port
     */
    public User(String nom, String imageBase64, String ip, int port) {
        this.nom = nom;
        this.imageBase64 = imageBase64;
        this.ip = ip;
        this.port = port;
        System.out.println("creating user with image");
    }

    /**
     * constructor that need a name and a base64 String of an image.
     * @param nom
     * @param imageBase64
     */
    public User(String nom, String imageBase64){
        this.nom = nom;
        this.imageBase64 = imageBase64;
    }

    /**
     * Constructor that need only an ip and a port
     * @param ip
     * @param port
     */
    public User(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     *
     * @return the name
     */
    public String getNom() {
        return nom;
    }

    /**
     * set the name
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * @return base64 String of the user image
     */
    public String getImageBase64() {
        return imageBase64;
    }

    /**
     * set the base64 String ot the user image
     * @param imageBase64
     */
    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    /**
     *
     * @return the ip of the user
     */
    public String getIp() {
        return ip;
    }


    /**
     *
     * @return the port of the user
     */
    public int getPort() {
        return port;
    }

}
