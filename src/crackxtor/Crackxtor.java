package crackxtor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * the CRACKXTOR. (Crawler + Extractor)
 * shoulda named it Crackxator. sounds cooler...
 * 
 * TODO: 
 * 	- GUI
 * 	- Check if PNG or JPEG
 * 
 * @author Jerr
 *
 */
public class Crackxtor {
	private UI ui;
	
	public Crackxtor(UI ui) {this.ui = ui;}
	
	public void crackxate(String URL) throws IOException {
		
		Document doc = Jsoup.connect(URL).get();
		
		// ========================================== //
		
		//Begin Crackxation operation
		String destination = createDestinationFolder();
		
		Elements images = doc.getElementsByTag("img");
		int counter = 0;
//		for(Element image: images) {
//			counter++;
//			downloadImage(image.attr("abs:src"), destination, counter);
//		}
		
		// ========================================== //
		
		//Get LINE photos
		ArrayList<String> seen = new ArrayList<>();
		
		Elements elements = doc.getElementsByTag("span");
		for(Element e: elements) {
			if(e.hasAttr("style")) {
				String rawInput = e.attr("style");
				int begining = rawInput.indexOf("http");
				int end = rawInput.indexOf(".png")+4;
				
				String link = rawInput.substring(begining, end);
				if(!seen.contains(link)) {
					downloadImage(link, destination, counter);
					seen.add(link);
					counter++;
				}
			}
		}
		
		printReciept(URL, counter, destination);
		
		ui.reportUpdate("Crackxtracted images: " + counter);
	}
	
	
	/**
	 * Download one singular image. url to file.
	 * @param strImageURL
	 */
    private void downloadImage(String strImageURL, String destination, int counter) {
        
        //get file name from image path
        String strImageName = "image" + counter;
        
        ui.reportUpdate("Saving: " + strImageName + ", from: " + strImageURL);
        
        try {
            //open the stream from URL
            URL urlImage = new URL(strImageURL);
            InputStream in = urlImage.openStream();
            
            byte[] buffer = new byte[4096];
            int n = -1;
            
            OutputStream os = new FileOutputStream(destination + "/" + strImageName + ".png");
            
            //write bytes to the output stream
            while ( (n = in.read(buffer)) != -1 ){
                os.write(buffer, 0, n);
            }
            
            //close the stream
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * creates a destination folder next to this jar.
     * output is like "crackxation1, crackxaction2 ... "
     * will check how many crackxation folders exist.
     */
    private String createDestinationFolder() {
    	try {
    		CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
    		File jarFile = new File(codeSource.getLocation().toURI().getPath());
    		String jarLocation = jarFile.getParentFile().getPath();
    	
    		//find a valid folder name
    		int folderID = 0;
    		Path destinationFolder = Paths.get(jarLocation + "/Crackxation" + folderID);
    		while(!Files.notExists(destinationFolder)) {
    			folderID++;
    			destinationFolder = Paths.get(jarLocation + "/Crackxation" + folderID);
    			ui.reportUpdate("Path: " + destinationFolder);
    		}
    		
    		//Now CREATE!!!!
            Files.createDirectories(destinationFolder);
            ui.reportUpdate("Destination folder succesfully created: Crackxation" + folderID);
            ui.reportUpdate("Path: " + destinationFolder);
    		
    		return destinationFolder.toString();
    		
    	} catch (IOException e) {
    		ui.reportUpdate("WARNING: Destination folder creation failure!!!");
        	ui.reportUpdate(e.toString());
    		e.printStackTrace();
        } catch (URISyntaxException e) {
        	ui.reportUpdate("WARNING: Destination folder creation failure!!!");
        	ui.reportUpdate(e.toString());
    		e.printStackTrace();
        }
    	
    	return null; //Failure
    }
    
    /**
     * A thank you letter!!!
     */
    private static void printReciept(String targetURL, int imagesRecieved, String destination) {
    	StringBuilder message = new StringBuilder();
    	message.append("Thank you for using the CRACKXATOOOOOOR!!~~~ \n");
    	message.append("Target link: \t" + targetURL + "\n");
    	message.append("No of images: \t" + imagesRecieved + "\n");
    	message.append("Heres a cookie: \t (::) \n");

    	try {
			FileWriter reciept = new FileWriter(destination + "/Thank You.txt");
			reciept.write(message.toString());
			reciept.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}
