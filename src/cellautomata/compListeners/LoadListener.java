

package cellautomata.compListeners;

import cellautomata.logic.LifeLogic;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        //CreatING a file chooser
        final JFileChooser loadFileChooser = new JFileChooser();
        loadFileChooser.setCurrentDirectory(new File("."));
        //In response to a button click
        int returnVal = loadFileChooser.showOpenDialog((Component) e.getSource());
        File file = loadFileChooser.getSelectedFile();
        
        StringBuilder builder = new StringBuilder();
        String newRuleString = "";
        String line;
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            
            newRuleString = reader.readLine(); //Read the first line and save it separately, as it stores the rulestring.
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
    
}
