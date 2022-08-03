package application;

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

public class ItemErase {
	
	
	
	public void erase(String product) {
		
		
		File configure = new File("config.xml");
		
		if(configure.exists()) {		
			configure.setWritable(true);
			
			/* Loads Configuration file into memory.  Finds open entry field and adds new project name which is taken from new project holder Main.newProj.  
			 * Main.newProj is then reset to default value */
			
			try {
				
				DocumentBuilderFactory foundry = DocumentBuilderFactory.newInstance();
				DocumentBuilder build = foundry.newDocumentBuilder();
		    
				Document doc = build.parse(configure);		    
				NodeList itemsList = doc.getElementsByTagName("item");


							
					for(int i = 0; i < Main.projects.length; i++) {  // Goes through projects array
										
						if( ((Element) itemsList.item(i)).getAttribute("name").equals(product) ) {  // If projects array has an entry, write it into DOM
							
							((Element) itemsList.item(i)).setAttribute("name", "");  // Set new name
							
							break;
							
						}  // End of if						
						
						
					
				} // End of for
					
					
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
					
				Transformer transformer = transformerFactory.newTransformer();
					
				DOMSource domSource = new DOMSource(doc);
					
				StreamResult streamResult = new StreamResult(configure);
				
				transformer.transform(domSource, streamResult);
	
			
			} catch (IOException e) {
				
	    		System.out.println(getClass().getName() + " - Error");
	    		System.out.println(e.toString());
	    		e.printStackTrace();
				
			} catch (SAXException e) {
				
	    		System.out.println(getClass().getName() + " - Error");
	    		System.out.println(e.toString());
	    		e.printStackTrace();
				
			} catch (ParserConfigurationException e) {
				
	    		System.out.println(getClass().getName() + " - Error");
	    		System.out.println(e.toString());
	    		e.printStackTrace();
			
			} catch (TransformerConfigurationException e) {
				
	    		System.out.println(getClass().getName() + " - Error");
	    		System.out.println(e.toString());
	    		e.printStackTrace();
				
			} catch (TransformerException e) {
				
	    		System.out.println(getClass().getName() + " - Error");
	    		System.out.println(e.toString());
	    		e.printStackTrace();
				
			} finally {
				
				
				
			} // End of catch blocks 
			
			
		configure.setReadOnly();
		
		} // End of if
		

		
	} // End of erase

} // End of Class
