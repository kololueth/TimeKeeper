package application;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class Configure implements Runnable {
	
	
	
public void run() {  // Fills Config.xml
		
	File configure = new File("config.xml");

			
			int index = 1;
		
		
				try {
					
					DocumentBuilderFactory foundry = DocumentBuilderFactory.newInstance();
					DocumentBuilder build = foundry.newDocumentBuilder();
			    
					Document doc = build.newDocument();
							    
					Element root = doc.createElement("config");
					Element root2 = doc.createElement("projects");
								
					doc.appendChild(root);
					root.appendChild(root2);

					
								
						for(int i = 0; i < Main.projects.length; i++) {  // Goes through projects array
											
							
							if(Main.projects[i].equals("")) {  // If projects array has an entry, write it into DOM
								
								
								Element root3 = doc.createElement("item");
							
								Attr attr = doc.createAttribute("id");
								attr.setValue(Integer.toString(index));  // project id number
								root3.setAttributeNode(attr);
							
								Attr attr2 = doc.createAttribute("name");
								attr2.setValue("");  // project name
								root3.setAttributeNode(attr2);
										
								root2.appendChild(root3);
							
							} // End of if
							
							index++;
							
						} // End of for
						
						index = 1;
						
						Element reserve = doc.createElement("reserve");
						
				for(int i = 0; i < 15; i++) {  // Goes through projects array
																	
								
								Element root3 = doc.createElement("ritem");
							
								Attr attr = doc.createAttribute("id");
								attr.setValue(Integer.toString(index));  // project id number
								root3.setAttributeNode(attr);
							
								Attr attr2 = doc.createAttribute("name");
								attr2.setValue("");  // project name
								root3.setAttributeNode(attr2);
										
								reserve.appendChild(root3);
							
							
							
							index++;
							
						} // End of for
				
				root2.appendChild(reserve);						
						
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource domSource = new DOMSource(doc);
					StreamResult streamResult = new StreamResult(configure);
					transformer.transform(domSource, streamResult);
		
				
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
		    		
				}  // End of catch blocks
				
			configure.setReadOnly();
			
			
		
	} // End of run

} // End of Class
