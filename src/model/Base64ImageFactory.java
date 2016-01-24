package model;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Gabriel on 22/01/2016.
 */
public class Base64ImageFactory {
    private static Base64ImageFactory instance;

    private Base64ImageFactory(){}

    public static Base64ImageFactory getInstance(){
        if(instance == null){
            instance = new Base64ImageFactory();
        }
        return instance;
    }
    public Base64Image makeFromBase64DataString(String base64Data){
        return new Base64Image(base64Data.split(",").length > 1 ? base64Data.split(",")[1]: base64Data);
    }
    public Base64Image makeFromURL(String url){
        BufferedInputStream bufferedInputStream;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            bufferedInputStream = new BufferedInputStream(new URL(url).openStream());

            byte[] buffer = new byte[1024];
            while (true) {
                int r;
                try{
                    r = bufferedInputStream.read(buffer);
                }catch (IOException e){
                    break;
                }

                if (r == -1) break;
                out.write(buffer, 0, r);
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }




        byte[] ret = out.toByteArray();

        String base64 = DatatypeConverter.printBase64Binary(ret);
        System.out.println(base64);
        return new Base64Image(base64);
    }
}
