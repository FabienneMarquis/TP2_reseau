package model;

import javafx.scene.image.Image;

/**
 * Created by gabriel on 2016-01-06.
 */
public class LocatedImage extends Image {
    private final String url;

    /**
     * cette méthode permet de créer l'objet locatedImage et d'y associer un URL
     * @param url
     */
    public LocatedImage(String url) {
        super(url);
        this.url = url;
    }

    /**
     * cette méthode renvoit le String de l'URL associé à l'image LocatedImage
     * @return String url
     */
    public String getURL() {
        return url;
    }
}
