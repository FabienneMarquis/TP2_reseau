package model;


import javafx.scene.image.Image;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;

/**
 * Base64Image extends Image et gere la creation d'une Image javafx par une string sous format base64
 * @author Gabriel_Fabienne
 */
public class Base64Image extends Image {
    private final String base64;
    public Base64Image(String base64){
        super(new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(base64)));
        this.base64 = base64;
    }

    /**
     * Get base64 string de cette image
     * @return String
     */
    public String getBase64() {
        return base64;
    }
}
