package mold;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import application.DocLoad;
import application.Main;

public class ConfigureEdit implements Runnable {
	
	
	public void run() {
		
		
		File configure = new File("config.xml");
		
		if(configure.exists()) {		
			configure.setWritable(true);
			
			/* Loads Configuration file into memory.  Finds open entry field and adds new project name which is taken from new project holder Main.newProj.  
			 * Main.newProj is then reset to default value */
			
		    
			DocLoad Create = new DocLoad();
			
			Document doc = Create.load(configure);  
			
			
				NodeList itemsList = doc.getElementsByTagName("item");


							
					for(int i = 0; i < Main.projects.length; i++) {  // Goes through projects array
										
						
						if(((Element) itemsList.item(i)).getAttribute("name").equals("")  && !Main.projects[i].equals("")) {  // If projects array has an entry and configure file doesn't, write it into DOM
							
							((Element) itemsList.item(i)).setAttribute("name", Main.newProj);  // Set new name
							
							Main.newProj = ""; // default value
							
							break;
							
						}  // End of if						
						
					
					} // End of for
					
					
					
			Create.write(doc, configure);
			
			configure.setReadOnly();
			
		} // End of if
		
	}// End of run
	
	

} // End of Class
