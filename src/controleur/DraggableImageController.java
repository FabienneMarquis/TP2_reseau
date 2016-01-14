package controleur;

/**
 * Created by fabienne et gabriel on 2016-01-14.
 */

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.net.URL;
import java.util.ResourceBundle;

public class DraggableImageController implements Initializable{

        @FXML
        private ImageView imageView;

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
        void dragDone(DragEvent  event) {
           /* the drag-and-drop gesture ended */

                /* if the data was successfully moved, clear it */
            if (event.getTransferMode() == TransferMode.MOVE) {

            }

            event.consume();

        }

        @FXML
        void dragDropped( DragEvent event) {
             /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasUrl()) {
                imageView.setImage(new Image(db.getUrl()));
                success = true;
            }
                /* let the source know whether the string was successfully
                 * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        }

        @FXML
        void dragEntered(DragEvent  event) {

            /* the drag-and-drop gesture entered the target */

                /* show to the user that it is an actual gesture target */
            if (event.getGestureSource() != imageView &&
                    event.getDragboard().hasString()) {
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(0.5);
                imageView.setEffect(colorAdjust);
            }

            event.consume();
        }

        @FXML
        void dragExited(DragEvent  event) {
            /* mouse moved away, remove the graphical cues */
            imageView.setEffect(null);

            event.consume();

        }

        @FXML
        void dragOver(DragEvent  event) {

            /* data is dragged over the target */

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
            if (event.getGestureSource() != imageView &&
                    event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        }
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}

