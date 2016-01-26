package model;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

/**
 * Created by 0940135 on 2016-01-26.
 */
public class FileMessage extends Message{
    private String filename;
    public FileMessage(String ip,int port, String content,String filename) {
        super(ip,port, content);
        this.filename = filename;
    }
    public String getFilename(){
        return filename;
    }
    public void writeFile(){
        ByteArrayInputStream bis = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(this.getContent()));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int readedBytes;
        try {
            while ((readedBytes = bis.read(buffer))>0)
            {
                bos.write(buffer,0,readedBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] fileContent = bos.toByteArray();
        try {
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File dir = new File("/temp");
        if(!dir.exists()){
            dir.mkdir();
        }
        File tmp = createNewFile(dir,filename);
        try {
            FileOutputStream fos = new FileOutputStream(tmp);
            fos.write(fileContent);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private File createNewFile(File dir, String tmpFilename){
        String filenameWithoutExt = tmpFilename.split("\\.")[0];
        String ext = tmpFilename.split("\\.")[1];
        File tmp = new File(dir,tmpFilename);
        int id = 0;
        while (tmp.exists()){
            id++;
            filename = filenameWithoutExt+id+"."+ext;
            tmp = new File(dir,filename);
        }
        if(!tmp.exists()){
            try {
                tmp.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tmp;
    }
}
