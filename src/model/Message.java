package model;

import java.io.*;

public class Message implements Serializable {

    protected static final long serialVersionUID = 1112122200L;

    private String ip;
    private String content;


    public Message(String ip, String content) {
        this.ip = ip;
        this.content = content;

    }

    public String getContent() {
        return content;
    }
    public String getIp(){return ip;}


}