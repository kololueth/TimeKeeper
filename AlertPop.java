package pop;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertPop {
	
	
public void display(Stage stage, String alert) {
		
		int boxWidth = 400;
		int boxHeight = 200;
		

		Stage popup = new Stage(StageStyle.TRANSPARENT);	
		

		Label label1 = new Label(alert);
		label1.setTextAlignment(TextAlignment.CENTER);

		Button okButton = new Button("Ok");

		VBox vb = new VBox(10);
		
		
		StackPane sp = new StackPane();
		sp.getStyleClass().add("box1");
		
		popup.initModality(Modality.APPLICATION_MODAL);

		popup.initOwner(stage);
		popup.setX(popup.getOwner().getX() + popup.getOwner().getScene().getX() + (popup.getOwner().getScene().getWidth() - boxWidth)/2);
		popup.setY(popup.getOwner().getY() + popup.getOwner().getScene().getY() + (popup.getOwner().getScene().getHeight() - boxHeight)/2);
		

		
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(label1, okButton);
		vb.getStyleClass().add("box2");

		
		sp.getChildren().add(vb);
		
		Scene scene = new Scene(sp, boxWidth, boxHeight);
		scene.getStylesheets().add(getClass().getResource("dialog.css").toExternalForm());
		scene.setFill(null);
	

		okButton.setOnAction(e -> popup.close());
			
		popup.setScene(scene);
		popup.show();
		
	
		
	} // End of Display()

}
