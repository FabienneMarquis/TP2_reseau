package controller;



import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import model.Base64Image;
import model.Base64ImageFactory;
import model.Context;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Controlleur de Drag and Drop d'image
 * @author Gabriel_Fabienne
 */
public class DraggableImageController implements Initializable {


    @FXML
    private ImageView imageView;

    private Dragboard dragBoard;

    private String urlImage;

    @FXML
    void dragDetected(MouseEvent event) {
            /* drag was detected, start drag-and-drop gesture*/

                /* allow any transfer mode */
        Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
        ClipboardContent content = new ClipboardContent();
        content.putImage(imageView.getImage());
        db.setContent(content);

        event.consume();
    }

    @FXML
    void dragDone(DragEvent event) {
           /* the drag-and-drop gesture ended */

                /* if the data was successfully moved, clear it */
        if (event.getTransferMode() == TransferMode.MOVE) {

        }

        event.consume();

    }

    @FXML
    void dragDropped(DragEvent event) {
             /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasUrl()) {
            System.out.println(db.getUrl());
            String urlOrData = db.getUrl();
            imageView.setPreserveRatio(true);
            imageView.fitHeightProperty().addListener(((observable, oldValue, newValue) -> {
                System.out.println(imageView.fitWidthProperty().getValue());
            }));
            Image image;
            if (urlOrData.startsWith("data")) {
                image = Base64ImageFactory.getInstance().makeFromBase64DataString(urlOrData);

            } else {
                image = Base64ImageFactory.getInstance().makeFromURL(urlOrData);
            }
            imageView.setImage(image);


            success = true;
        } else if (db.hasFiles()) {
            System.out.println(db.getFiles().get(0).getAbsolutePath());
        }
        System.out.println(db.getString());
                /* let the source know whether the string was successfully
                 * transferred and used */
        event.setDropCompleted(success);

        event.consume();
    }

    @FXML
    void dragEntered(DragEvent event) {

            /* the drag-and-drop gesture entered the target */
                /* show to the user that it is an actual gesture target */
        if (event.getGestureSource() != imageView && (
                event.getDragboard().hasString() || event.getDragboard().hasFiles())) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.5);
            imageView.setEffect(colorAdjust);
        }

        event.consume();
    }

    @FXML
    void dragExited(DragEvent event) {
            /* mouse moved away, remove the graphical cues */
        imageView.setEffect(null);

        event.consume();

    }

    @FXML
    void dragOver(DragEvent event) {

            /* data is dragged over the target */

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
        if (event.getGestureSource() != imageView &&
                (event.getDragboard().hasString() || event.getDragboard().hasFiles())) {
                    /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    /**
     * Initialize pour ce controlleur
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}

