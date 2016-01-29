package model;

import java.io.*;

/**
 * Message is a Serialized class that is used to send txt content, an ip address, and port to
 * a socket
 * @author Gabriel_Fabienne
 */
public class Message implements Serializable {

    protected static final long serialVersionUID = 1112122200L;

    private String ip;
    private int port;
    private String content;

    /**
     * Constructor that need an ip, a port and some String content
     * @param ip
     * @param port
     * @param content
     */
    public Message(String ip,int port, String content) {
        this.port = port;
        this.ip = ip;
        this.content = content;

    }

    /**
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @return ip
     */
    public String getIp(){return ip;}


    /**
     *
     * @return port
     */
    public int getPort() {
        return port;
    }
}