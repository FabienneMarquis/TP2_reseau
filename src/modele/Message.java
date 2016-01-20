package modele;


//import java.io.*;
//import java.util.Base64;
//
///**
// * Created by fabienne et gabriel on 2016-01-14.
// */
//public class Message implements Serializable {
//    private String message, ipSource,file,fileName;
//
//    public Message(String message, String ipSource) {
//        this.message = message;
//        this.ipSource = ipSource;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public String getIpSource() {
//        return ipSource;
//    }
//
//    public void setIpSource(String ipSource) {
//        this.ipSource = ipSource;
//    }
//
//    public byte[] getFile(){
//        return Base64.getDecoder().decode(file);
//    }
//
//    public void setFile(File file){
//        try{
//            FileInputStream fileInputStream = new FileInputStream(file);
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//            byte[] bitsArray = new byte[(int) file.length()];
//            bufferedInputStream.read(bitsArray, 0, bitsArray.length);
//            this.file = Base64.getEncoder().encodeToString(bitsArray);
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//}
import java.io.*;
/*
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the MyServer.
 * When talking from a Java TutorialClient to a Java MyServer a lot easier to pass Java objects, no
 * need to count bytes or to wait for a line feed at the end of the frame
 */
public class Message implements Serializable {

    protected static final long serialVersionUID = 1112122200L;

    // The different types of message sent by the TutorialClient
    // WHOISIN to receive the list of the users connected
    // MESSAGE an ordinary message
    // LOGOUT to disconnect from the MyServer
    public static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2, FILE = 3;
    private int type;
    private String content,filename;


    public Message(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }
    public String getContent() {
        return content;
    }
    public String getFilename(){
        return filename;
    }

}