package pop;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

public class ButtonPop {

	public String display(Stage stage) {
		
		int boxWidth = 400;
		int boxHeight = 300;
		

		String projName = "";

		Stage popup = new Stage(StageStyle.TRANSPARENT);	
		

		Label label1 = new Label("Adding a project?  Write the project name.");

		Button addButton = new Button("Add");
		Button cancelButton = new Button("Cancel");

		TextField txtField = new TextField();

		VBox vb = new VBox(10);
		
		HBox hb = new HBox(10);
		
		StackPane sp = new StackPane();
		sp.getStyleClass().add("box1");
		
		popup.initModality(Modality.APPLICATION_MODAL);

		popup.initOwner(stage);
		popup.setX(popup.getOwner().getX() + popup.getOwner().getScene().getX() + (popup.getOwner().getScene().getWidth() - boxWidth)/2);
		popup.setY(popup.getOwner().getY() + popup.getOwner().getScene().getY() + (popup.getOwner().getScene().getHeight() - boxHeight)/2);
		
		
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(addButton, cancelButton);
		
		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(label1, txtField, hb);
		vb.getStyleClass().add("box2");
		
		txtField.setAlignment(Pos.CENTER);
		
		sp.getChildren().add(vb);
		
		Scene scene = new Scene(sp, boxWidth, boxHeight);
		scene.getStylesheets().add(getClass().getResource("dialog.css").toExternalForm());
		scene.setFill(null);
	

			addButton.setOnAction(e -> popup.close());
			
			cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			
				public void handle(ActionEvent args) {
				
					txtField.setText("|56481894568|jghdtrsrtkjugkjn;ljohfufgdgfjkljh567486870926|>><<.....7/*/*/*/*/vvcf");
					popup.close();
				
				}
			
			});



		popup.setScene(scene);
		popup.showAndWait();
		
		
		
		projName = txtField.getText().trim();
		

		return projName;
		
	} // End of display()

} // End of Class
