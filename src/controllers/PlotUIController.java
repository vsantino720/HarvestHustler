package controllers;
import boundary.PlaySceneBuilder;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

public class PlotUIController implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent mouseEvent) {
        Button btn = (Button) mouseEvent.getSource();
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
            if (btn.getText().startsWith("Vacant Plot")) {
                btn.setText("Plant");
                btn.setStyle("-fx-background-color: chocolate; -fx-text-fill: beige; "
                        + "-fx-background-radius: 4; -fx-border-color: beige; "
                        + "-fx-border-radius: 4; -fx-border-width: 4; -fx-font-size: 18");
            } else {
                String toolColor = "gray";
                ComboBox toolBox = PlaySceneBuilder.getToolBox();
                if (toolBox.getValue() == "Water") {
                    toolColor = "darkblue";
                } else if (toolBox.getValue() == "Pesticide") {
                    toolColor = "purple";
                } else if (toolBox.getValue() == "Fertilizer") {
                    toolColor = "brown";
                }
                btn.setText("Harvest");
                btn.setStyle("-fx-background-color: linear-gradient(to right, green, "
                        + toolColor + "); "
                        + "-fx-text-fill: beige; -fx-background-radius: 4; "
                        + "-fx-border-color: beige; -fx-border-radius: 4; "
                        + "-fx-border-width: 4; -fx-font-size: 18");
                PlaySceneBuilder.getWaterInfo().setText("Right click to use "
                    + toolBox.getValue() + ".");
            }
        }
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            MainWindow.updatePlots();
            PlaySceneBuilder.getWaterInfo().setText(null);
        }
    }
}
