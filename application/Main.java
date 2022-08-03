package application;
	
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mold.ConfigureEdit;
import mold.RecordWrite;
import pop.AlertPop;
import pop.ButtonPop;
import pop.EditPop;
import pop.TimePop;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;



public class Main extends Application {
	
	
	public static String[] projects = new String[6];
	public static String newProj;
	
	int guibs = projects.length;
	
	public Stage primaryStage;
	
	
	Button[] button = new Button[guibs];
	
	Label focusProjName = new Label();
	Label focusProjStat = new Label();

	ExecutorService recordTimer = Executors.newSingleThreadExecutor(new DaemonThread());	
	Future rtask;
	

	
	
	
	@Override
	public void init() {
		
		/* Fills Hashmap*/
		new Thread( new HashFill() ).start();
		
		/* Looks for config.xml. If config.xml is not found, config.xml is created in new Thread.  */
			try {
				
				DocumentBuilderFactory foundry = DocumentBuilderFactory.newInstance();
				DocumentBuilder build = foundry.newDocumentBuilder();
			
				NodeList itemList = build.parse(new File("config.xml")).getElementsByTagName("item");


					for (int i = 0; i < projects.length; i++) {
	  		    
				
							projects[i] = ((Element) itemList.item(i)).getAttribute("name").trim();
      
							
					} // End of for
					
					
			}  catch (IOException e) {
				
				for(int i = 0; i < projects.length; i++) {
					
					projects[i] = "";
				
				}
				
				new Thread( new Configure() ).start();
				
	    		System.out.println(getClass().getName() + " - Error");
	    		System.out.println(e.toString());
	    		e.printStackTrace();
				
			} catch (SAXException e) {
				
				// Handle error with config.xml document change //
				
	    		System.out.println(getClass().getName() + " - Error");
	    		System.out.println(e.toString());
	    		e.printStackTrace();
				
			} catch (ParserConfigurationException e) {
				
	    		System.out.println(getClass().getName() + " - Error");
	    		System.out.println(e.toString());
	    		e.printStackTrace();
			
			}	
		
		
	} // End of init()
	
	
	

	
	@Override
	public void start(Stage primaryStage) {
		
			
		try {
			
			
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("TimeCreeper");
			this.primaryStage.setFullScreen(false);
			this.primaryStage.setMaximized(false);
			this.primaryStage.setMaxHeight(500);
			this.primaryStage.setMaxWidth(600);
		
			
			
			GridPane gp = new GridPane();
			GridPane gp2 = new GridPane();
			GridPane gp3 = new GridPane();
			
			gp3.getRowConstraints().add(new RowConstraints(250));
			gp3.getColumnConstraints().add(new ColumnConstraints(350));
			gp3.getRowConstraints().add(new RowConstraints(75));
			gp3.getColumnConstraints().add(new ColumnConstraints(350));
			
			Pane topPane = new Pane();
			Pane botPane = new Pane();
			
			topPane.setPrefHeight(100);
			topPane.setPrefWidth(primaryStage.getMaxWidth());
			topPane.getStyleClass().add("topPane");
			botPane.setPrefHeight(100);
			botPane.setPrefWidth(primaryStage.getMaxWidth());
			botPane.getStyleClass().add("botPane");
		
			VBox vb = new VBox();
			vb.getStyleClass().add("box-vb");
			vb.setSpacing(3);
			
			VBox vb2 = new VBox();
			vb2.getStyleClass().add("box-vb");
			vb2.setPrefSize(350, 325);  // width, height
			vb2.setAlignment(Pos.CENTER);
			
			StackPane sp1 = new StackPane();
			StackPane sp2 = new StackPane();
			StackPane sp3 = new StackPane();
			sp3.getStyleClass().add("box-vb");			
			
			focusProjName.getStyleClass().add("status");
			focusProjStat.getStyleClass().add("status2");
		
			sp2.getChildren().add(focusProjStat);
			sp1.getChildren().add(focusProjName);
		
			vb2.getChildren().addAll(sp1, sp2);
		
			gp3.add(vb2, 0, 1); // column row
			gp3.add(sp3, 0, 0);
		
			gp2.add(vb, 0, 0); // column row
			gp2.add(gp3, 1, 0); // column row
			
			gp.add(topPane, 0, 0); // column row
			gp.add(gp2, 0, 1); // column row
			gp.add(botPane, 0, 2); // column row
		
	
		
	
				for(int i = 0; i < button.length; i++) {
				
					button[i] = new Button(projects[i]);
					button[i].setMaxHeight(50);
					button[i].setMaxWidth(250);
					button[i].setMinHeight(50);
					button[i].setMinWidth(250);
					button[i].getStyleClass().add("default-button");
					button[i].addEventFilter(MouseEvent.MOUSE_ENTERED, Entered);
					button[i].addEventFilter(MouseEvent.MOUSE_PRESSED, Pressed);
					button[i].addEventFilter(MouseEvent.MOUSE_CLICKED, Clicked);

					vb.getChildren().add(button[i]);
				
				}
			
			Scene scene = new Scene(gp,600,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			this.primaryStage.setMinWidth(vb.getWidth() );
			this.primaryStage.setMinHeight(vb.getHeight() );
		
			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			
	
			this.primaryStage.setMinWidth(this.primaryStage.getWidth() - scene.getWidth() + vb.getWidth());
			this.primaryStage.setMinHeight(this.primaryStage.getHeight() - scene.getHeight() + vb.getHeight() );
			

		} catch(Exception e) {	e.printStackTrace();} // End of try catch
		
		
		
	} // End of start
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	EventHandler<MouseEvent> Entered = new EventHandler<MouseEvent>() {
		
		
		public void handle(MouseEvent args) {
			
			String name = args.getSource().toString().substring(args.getSource().toString().indexOf("'") + 1, args.getSource().toString().lastIndexOf("'"));
			
			
				if(Hash.hash.containsKey(name)) {
					
					focusProjName.setText(name);
					
					focusProjStat.setText(Hash.valueString(Hash.hash.get(name)));
					
			
				}
				
				if(!Hash.hash.containsKey(name) && !name.equals("")) {
					
					focusProjName.setText(name);
			
					focusProjStat.setText("---");
			
				}
				
				if(name.equals("")) {
					
					focusProjName.setText("");
			
					focusProjStat.setText("");
			
				}
		
		
		
	
		} // End of handle
		
		
	}; // End of Entered
	
	
	
	
	EventHandler<MouseEvent> Pressed = new EventHandler<MouseEvent>() {
		
		
		public void handle(MouseEvent args) {
			
			if(args.getButton() == MouseButton.PRIMARY) {
				
			
				String name = args.getSource().toString().substring(args.getSource().toString().indexOf("'") + 1, args.getSource().toString().lastIndexOf("'"));

						
					if(name.equals("")) { // If button is not set
						
					
						boolean dup = false;  // project duplication indicator
							
						GaussianBlur blur = new GaussianBlur();
						blur.setRadius(7);
							
					
						primaryStage.getScene().getRoot().setEffect(blur);
						newProj = new ButtonPop().display(primaryStage);
						primaryStage.getScene().getRoot().effectProperty().setValue(null);  // clears blur
						
	
							if(newProj.equals("|56481894568|jghdtrsrtkjugkjn;ljohfufgdgfjkljh567486870926|>><<.....7/*/*/*/*/vvcf")) {
								
								// Do nothing
								
							} else if(!newProj.equals("")) { // If something was added in ButtonPop
									
							
									
										for(int i = 0; i < guibs; i++) {	// duplicate check
										
											if(newProj.toUpperCase().trim().equals(projects[i].toUpperCase().trim())) {  // if project is a duplicate
											
												dup = true;
												
												new AlertPop().display(primaryStage, "Duplicate Project Name! \n Please try a new project name.");
												
												break;
						
											} 
										
										}// End of duplicate check
									
									
									
										if(!dup) {  // If project is not a duplicate

										
											for(int a = 0; a < guibs; a++) {
										
												if(args.getSource() == button[a]) {
													
													if(newProj.length() > 20) {
														
														button[a].setStyle("-fx-font-size: 14px;");
														
													}
													
													
													button[a].setText(newProj);
													
													
													projects[a] = newProj;
													new Thread( new ConfigureEdit() ).start();  // Writes entry to configuration file
						
													break;
					
												}  // End of if
				
											} // End of for
										
									
										}  // End of if dup
									

				
								} else {  // if newProj == ""
									
									new AlertPop().display(primaryStage, "No Project Added! \n Please try again.");
								
								}
								
								
								
								
				
				
						} // end of if
			
			} // End of in Primary button 
			
		} // End of handle
		
	};  // End of Pressed
	
		
	
	
	EventHandler<MouseEvent> Clicked = new EventHandler<MouseEvent>() {

		boolean on;
		boolean warn;
		public String onName = "";
		
		
	private void buttonSwitch(String newInput) {
		
		
		if(on == true && onName.equals(newInput)) {
			
			on = false;
			warn = false;
			onName = "";
		
		} else if(on == true && !onName.equals(newInput)) {
			
			warn = true;
			
		} else if(on == false && newInput.equals("")){
			
			warn = true;
				
		} else if(on == false && onName.equals("")){
			
			on = true;
			onName = newInput;
			
		}
		
		
		
	} // End of buttonSwitch()	
		
	
		
		public void handle(MouseEvent args) {
			
			String newInput = args.getSource().toString().substring(args.getSource().toString().indexOf("'") + 1, args.getSource().toString().lastIndexOf("'"));
			
			
			if(args.getButton() == MouseButton.PRIMARY) {
				
		//*******************************************************************************/
			
			
				buttonSwitch(newInput);
				
						
		//*******************************************************************************/
				
					if(on && !warn) {  // Just turned on
						
						
				
							for(int i = 0; i < guibs; i++) {
					
								if(button[i].getText().equals(newInput)) {
						
									button[i].getStyleClass().remove("default-button");
									button[i].getStyleClass().add("on-button");
							
								}  // End of if
					
							} // End of for
							
							
							
							
							Pulse Pulse = new Pulse();	
							
							Pulse.setKey(onName);  // sends project name for Hash and project entry			
							rtask = recordTimer.submit(Pulse);
							
				
					} // End of just turned on
					
			
		//*******************************************************************************/
					
					
			
					if(on && warn) {  // if another button is clicked when button is on
				
						new AlertPop().display(primaryStage, onName + " is running! \n Please turn it off before switching projects.");
			
					}  // end of if clicked an button off
			
					
					
		//*******************************************************************************/
					
					
					if(!on && warn) { // if button name is not set
				
					//	System.out.println("Button not set");
				
					}
			
			
		//*******************************************************************************/
					
		
					if(!on && !warn) {  // if just turned off
					
				
						rtask.cancel(true);
				
						focusProjName.setText(newInput);
						//focusProjStat.setText(Hash.valueString( (int) (System.currentTimeMillis()/1000 - Hash.start)));
						
						focusProjStat.setText("Done");
				
							for(int i = 0; i < guibs; i++) {
					
								if(button[i].getText().equals(newInput)) {
						
									button[i].getStyleClass().remove("on-button");
									button[i].getStyleClass().add("default-button");
								
								} // End of if
					
							} // End of for
						
			
					}  // End of just turned off
					
					
		//*******************************************************************************/
		
			
			} // if button is primary button
			
			

			if(args.getButton() == MouseButton.SECONDARY && !onName.equals(newInput)) {
				
				
				if(!newInput.equals("")) {
					
					GaussianBlur blur = new GaussianBlur();
					blur.setRadius(7);
						
				
					primaryStage.getScene().getRoot().setEffect(blur);
					
					
					String product = new EditPop().display(primaryStage, newInput); // Send stage reference and project name reference, taken from MouseEvent input
					
					primaryStage.getScene().getRoot().effectProperty().setValue(null);
					
					
						if(!product.equals(newInput)) {  // If item was edited
					
							for(int i = 0; i < guibs; i++) {
						
								if(args.getSource() == button[i]) {  //  Change source button title to edited name returned from EditPop
						
									button[i].setText(product);
									projects[i] = product;
						
									break;

								}  // End of if
				
							}
						}	
				}
				
				
			} // End of if secondary click	
			
			
			
			if(args.getButton() == MouseButton.SECONDARY && onName.equals(newInput)) {
				
				/* Needs pop up that will adjust the start time and possibly end time */
				
				
				if(!newInput.equals("")) {
					
					GaussianBlur blur = new GaussianBlur();
					blur.setRadius(7);
						
					primaryStage.getScene().getRoot().setEffect(blur);
					
					new TimePop().display(primaryStage, newInput); // Send stage reference and project name reference, taken from MouseEvent input
					
					primaryStage.getScene().getRoot().effectProperty().setValue(null);
					
			
				}
				
				
			} // End of if secondary click and on button	
			
			


			
		}  // End of handle
		
	};  // End of Clicked()
	
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	@Override
	public void stop(){}
	
	
	public static void main(String[] args) {
			
			launch(args);		
	
	}  // End of main
	
} // End of Class
