package model;

import java.io.Serializable;

/**
 * Created by 0940135 on 2016-01-19.
 */
public class User implements Serializable {
    private String nom,imageBase64,ip;
    private int port;

    public User(String nom, String imageBase64, String ip, int port) {
        this.nom = nom;
        this.imageBase64 = imageBase64;
        this.ip = ip;
        this.port = port;
    }
    public User(String nom, String imageBase64){
        this.nom = nom;
        this.imageBase64 = imageBase64;
    }

    public User(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getIp() {
        return ip;
    }


    public int getPort() {
        return port;
    }

}
