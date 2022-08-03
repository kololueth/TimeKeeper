package pop;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import application.Hash;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mold.ItemEdit;

public class TimePop {
	
String projName = "";
	


	public String display(Stage stage, String name) {

		projName = name;
		
		int boxWidth = 500;
		int boxHeight = 300;

		Stage popup = new Stage(StageStyle.TRANSPARENT);
		
		Label label1 = new Label("Adjust Time start for '" + name + "'?");
		Label label2 = new Label("");
		
		label2.getStyleClass().add("clock");
		
		Button editButton = new Button("Change");
		Button cancelButton = new Button("Cancel");

		
		StackPane sp = new StackPane();
		sp.getStyleClass().add("box1");
		
		VBox vb = new VBox(10);
		HBox hb = new HBox(10);
		
		popup.initModality(Modality.APPLICATION_MODAL);
		popup.setTitle("Time Creeper - Adjust Time");
		popup.initOwner(stage);
		popup.setX(popup.getOwner().getX() + popup.getOwner().getScene().getX() + (popup.getOwner().getScene().getWidth() - boxWidth)/2);
		popup.setY(popup.getOwner().getY() + popup.getOwner().getScene().getY() + (popup.getOwner().getScene().getHeight() - boxHeight)/2);
		
		Slider slider = new Slider(-180, 180, 0);
		
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			
			
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					
				
							
						label2.setText(Integer.toString((int) slider.getValue()));
						
						String time = new SimpleDateFormat("h:mm a").format(Date.from(Instant.ofEpochSecond(Hash.start + (int) slider.getValue() * (60 * 1)))); // Example January.21.2019
					
						label2.setText(time);
					
				}
				
			
		});
		

		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(editButton, cancelButton);

		vb.setAlignment(Pos.CENTER);
		vb.getChildren().addAll(label2, label1, slider, hb);
		vb.getStyleClass().add("box2");
		
		sp.getChildren().add(vb);


			editButton.setOnAction(new EventHandler<ActionEvent>() {
			
			
				public void handle(ActionEvent args) {
				
					Hash.start = Hash.start + (int) slider.getValue() * (60 * 1);
					
					popup.close();
				
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
	
	

}
