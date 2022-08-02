package pop;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mold.ItemEdit;


public class EditPop {
	
	
	String projName = "";
	


	public String display(Stage stage, String name) {

		projName = name;
		
		int boxWidth = 400;
		int boxHeight = 300;

		Stage popup = new Stage(StageStyle.TRANSPARENT);
		
		Label label1 = new Label("Edit or Delete project name '" + name + "'?");
		
		Button editButton = new Button("Edit");
		Button deleteButton = new Button("Delete");
		Button cancelButton = new Button("Cancel");

		TextField txtField = new TextField();
		
		StackPane sp = new StackPane();
		sp.getStyleClass().add("box1");
		
		VBox vb = new VBox(10);
		HBox hb = new HBox(10);
		
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setTitle("Time Creeper - Edit Project");
		popup.initOwner(stage);
		popup.setX(popup.getOwner().getX() + popup.getOwner().getScene().getX() + (popup.getOwner().getScene().getWidth() - boxWidth)/2);
		popup.setY(popup.getOwner().getY() + popup.getOwner().getScene().getY() + (popup.getOwner().getScene().getHeight() - boxHeight)/2);
		
		txtField.setVisible(false);
		txtField.setEditable(false);
		txtField.setAlignment(Pos.CENTER);
		txtField.setText(name);
		
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(editButton, deleteButton, cancelButton);

		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(label1, txtField, hb);
		vb.getStyleClass().add("box2");
		
		sp.getChildren().add(vb);


			editButton.setOnAction(new EventHandler<ActionEvent>() {
			
			
				public void handle(ActionEvent args) {
				
				
					if(txtField.isEditable()) {
				
				
						if(!projName.equals(txtField.getText().trim())) {  // If projName doesn't equal text edit
					
							new ItemEdit().edit(projName, txtField.getText().trim());  // pass original text and new text to ItemEdit
				
							projName = txtField.getText().trim();
				
							popup.close();
				
						} else {
					
							popup.close();
					
						}
				
					} // End of outer if
					
				
				txtField.setVisible(true);
				txtField.setEditable(true);
				editButton.setText("Change");
				label1.setText("Go Ahead and Edit '" + name + "'!");
				hb.getChildren().remove(deleteButton);
		
				
				
				} //End of handle
			
			});
		
		
		
			deleteButton.setOnAction(new EventHandler<ActionEvent>() {
				
				int count = 0;
			
			
				public void handle(ActionEvent args) {
					
					count++;
					
					
					if(count == 1) {
						
						label1.setText("Are you sure you want to delete '" + name + "'?");
						
						hb.getChildren().remove(editButton);
						
						
					} else if(count == 2) {
				
				
					new ItemEdit().erase(txtField.getText());  // Get unedited project name
				
					projName = "";
				
					popup.close();
					
					}
				
						
				} //End of handle
			
			});
			
			
		
			cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			
			
				public void handle(ActionEvent args) {
				
					popup.close();
				
				} //End of handle
			
			});

		

		Scene scene = new Scene(sp, boxWidth, boxHeight);
		scene.getStylesheets().add(getClass().getResource("dialog.css").toExternalForm());
		scene.setFill(null);

		popup.setScene(scene);
		popup.showAndWait();
		

		return projName;
		
		
	} // End of Display()
	
	
	
} // End of Class
