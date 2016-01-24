package model;


import javafx.scene.image.Image;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;

/**
 * Created by Gabriel on 22/01/2016.
 */
public class Base64Image extends Image {
    private final String base64;
    public Base64Image(String base64){
        super(new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(base64)));
        this.base64 = base64;
    }

    public String getBase64() {
        return base64;
    }
}
