package modele;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by fabienne et gabriel on 2016-01-14.
 */
public class Message {
    private String nom,imageURL, message, source;
    FileInputStream messageEnvoyer;

  /*  try{

    } catch (FileNotFoundException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }
*/

    public void message(String nomN, String avatarImage, String messageEnvoyer){
        nom= nomN;
        imageURL=avatarImage;


    }

    public String getMessage() {
        return message;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getNom() {
        return nom;
    }

    public String getSource() {
        return source;
    }

    public FileInputStream getMessageEnvoyer() {
        return messageEnvoyer;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setMessageEnvoyer(FileInputStream messageEnvoyer) {
        this.messageEnvoyer = messageEnvoyer;
    }
}
