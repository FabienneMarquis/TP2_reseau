package model;

import java.io.*;

public class Message implements Serializable {

    protected static final long serialVersionUID = 1112122200L;

    private String ip;
    private int port;
    private String content;

    public Message(String ip,int port, String content) {
        this.port = port;
        this.ip = ip;
        this.content = content;

    }

    public String getContent() {
        return content;
    }
    public String getIp(){return ip;}


    public int getPort() {
        return port;
    }
}