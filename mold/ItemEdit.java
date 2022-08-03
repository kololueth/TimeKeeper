package mold;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import application.DocLoad;
import application.Hash;
import application.Main;

public class ItemEdit {
	
	
	public void edit(String origin, String product) {
		
		
		File configure = new File("config.xml");
		
		if(configure.exists()) {		
			configure.setWritable(true);
			
			/* Loads Configuration file into memory.  Finds open entry field and adds new project name which is taken from new project holder Main.newProj.  
			 * Main.newProj is then reset to default value */
			
			DocLoad Create = new DocLoad();
			
			Document doc = Create.load(configure);  
			
			
	
				NodeList itemsList = doc.getElementsByTagName("item");


				
					for(int i = 0; i < Main.projects.length; i++) {  // Goes through projects array
										
						if( ((Element) itemsList.item(i)).getAttribute("name").equals(origin) ) {  // If projects array has an entry, write it into DOM
							
							((Element) itemsList.item(i)).setAttribute("name", product);  // Set new name
							
						
								if(Hash.hash.containsKey(origin)) {
									
									int holder = Hash.hash.get(origin);
									
									Hash.hash.put(product, holder);
									
									Hash.hash.remove(origin);
									
									new RecordEdit().edit(origin, product);
									
									
								}
							
							
							break;
							
						}  // End of if						
						
						
					
				} // End of for
					
					
		Create.write(doc, configure);
			
		configure.setReadOnly();
		
		} else {
			
			
			///// insert dialog here /////
			///// insert dialog here /////
			///// insert dialog here /////
			
		}
		
	} // End of edit
	
	
	
	public void erase(String origin) {
		
		
		File configure = new File("config.xml");
		
		if(configure.exists()) {		
			configure.setWritable(true);
			
			/* Loads Configuration file into memory.  Finds open entry field and adds new project name which is taken from new project holder Main.newProj.  
			 * Main.newProj is then reset to default value */
			
			DocLoad Create = new DocLoad();
			
			Document doc = Create.load(configure);  
			
			
	
				NodeList itemsList = doc.getElementsByTagName("item");


				
					for(int i = 0; i < Main.projects.length; i++) {  // Goes through projects array
					
						
						if( ((Element) itemsList.item(i)).getAttribute("name").equals(origin) ) {  // If projects array has an entry, write it into DOM
						
							((Element) itemsList.item(i)).setAttribute("name", "");  // Set new name
						
							break;
						
						}  // End of if						
						
				
					} // End of for					
						
						
					
		Create.write(doc, configure);
			
		configure.setReadOnly();
		
		} else {
			
			
			///// insert dialog here /////
			///// insert dialog here /////
			///// insert dialog here /////
			
		}
		
	} // End of delete
	

} // End of Class
