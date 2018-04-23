

package cellautomata.compListeners;

import cellautomata.display.Display;
import cellautomata.logic.LifeLogic;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;


public class LoadListener implements ActionListener{
    private LifeLogic lifeLogic;
    private int currentState[][];

    public LoadListener(LifeLogic lifeLogic) {
        this.lifeLogic = lifeLogic;
        currentState = lifeLogic.getCurrentState();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //Creating a file chooser
        final JFileChooser loadFileChooser = new JFileChooser();
        loadFileChooser.setCurrentDirectory(new File("."));
        
        //In response to a button click
        int returnVal = loadFileChooser.showOpenDialog((Component) e.getSource());
        File file = loadFileChooser.getSelectedFile();
        
        if(file.getName().endsWith("txt")){
            StringBuilder builder = new StringBuilder();
            String newRuleString = "";
            String line;

            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));

                newRuleString = reader.readLine();  //Read the first line and save it separately, as it stores the rulestring.
                while((line = reader.readLine()) != null) //Read the rest and store them in the builder
                    builder.append(line+"\n");
                reader.close();

            } catch (FileNotFoundException ex) { //File not found
                Logger.getLogger(LoadListener.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {          //Reading exception
                Logger.getLogger(LoadListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //////UPDATING THE ACTUAL LIFELOGIC
            lifeLogic.clear();
            lifeLogic.setLogic(newRuleString);
            lifeLogic.setCurrentState(builder.toString());
        }
        else if(file.getName().endsWith("jpg")){
            
            
            BufferedImage image = loadImage(file);
            int[][] pixels = new int[image.getHeight()][image.getWidth()];
        
            for(int y=0; y<image.getHeight(); y++)
                for(int x=0; x<image.getWidth(); x++){
                
                    int color = image.getRGB(x, y);
                    int  red   = (color & 0x00ff0000) >> 16;
                    int  green = (color & 0x0000ff00) >> 8;
                    int  blue  =  color & 0x000000ff;

                    int numberOfShades = 150;
                    int conversionFactor = 255 / (numberOfShades - 1);
                    int averageValue = (red + green + blue) / 3;
                    pixels[y][x] = (int) (((averageValue / conversionFactor) + 0.5) * conversionFactor);

                    //pixels[y][x] = (red+green+blue) / 3;
            }
            
        }
        else if(file.getName().endsWith("png")){
            Display.alert("File extension not supported. Working on it...");
        }
        else Display.alert("File extension not supported.");
        
    }
    
    
    public BufferedImage loadImage(File file){
        try {
            return ImageIO.read(file);
	} catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
	}
        
        return null;
    }
}
