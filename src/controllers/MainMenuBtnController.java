package controllers;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;

public class MainMenuBtnController implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent mouseEvent) {
        Button btn = (Button) mouseEvent.getSource();
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
            btn.setStyle("-fx-background-color: black; -fx-text-fill: "
                    + "darkorange; -fx-background-radius: 10; -fx-border-color: darkorange;"
                    + " -fx-border-radius: 7; -fx-border-width: 6;");
        }
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            btn.setStyle("-fx-background-color: black; -fx-text-fill: navajowhite;"
                    + " -fx-background-radius: 10; -fx-border-color: navajowhite;"
                    + " -fx-border-radius: 7; -fx-border-width: 5;");
        }
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            btn.setStyle("-fx-background-color: gray; -fx-text-fill: navajowhite;"
                    + " -fx-background-radius: 10; -fx-border-color: darkorange;"
                    + " -fx-border-radius: 7; -fx-border-width: 5;");
            btn.setEffect(new BoxBlur(3, 5, 1));
        }
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            btn.setStyle("-fx-background-color: black; -fx-text-fill: navajowhite;"
                    + " -fx-background-radius: 10; -fx-border-color: navajowhite;"
                    + " -fx-border-radius: 7; -fx-border-width: 5;");
            btn.setEffect(null);
        }
    }
}
