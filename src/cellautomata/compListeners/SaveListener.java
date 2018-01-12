package cellautomata.compListeners;

import cellautomata.CellAutomata;
import cellautomata.display.Display;
import cellautomata.logic.LifeLogic;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveListener implements ActionListener{
    private LifeLogic lifeLogic;
    private int[][] currentState;
    private CellAutomata cellAutomata;
    
    public SaveListener(LifeLogic lifeLogic, CellAutomata cellAutomata) {
        this.lifeLogic = lifeLogic;
        currentState = lifeLogic.getCurrentState();
        this.cellAutomata = cellAutomata;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<String> lines = new ArrayList<>();
        File file;
        
        ///ADDING THE RULESTRING AND CURRENT STATUS TO THE ARRAY LINE BY LINE
        lines.add(cellAutomata.getDisplay().getsRuleTextField().getText()+"/"+cellAutomata.getDisplay().getbRuleTextField().getText());
        
        for(int i=0; i<currentState.length; i++){
            String digits="";
            for(int j=0; j<currentState[i].length;j++){
                digits += (currentState[i][j] == 0 ? "1" : "0");
            }
            lines.add(digits);
        }
        
        ///SELECTING A FILE TO WRITE IN THROUGH A JFileChooser
        //Creating a file chooser
        final JFileChooser saveFileChooser = new JFileChooser();
        saveFileChooser.setCurrentDirectory(new File("."));
        saveFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        saveFileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".txt", "txt"));
        //In response to a button click
        int returnVal = saveFileChooser.showSaveDialog((Component) e.getSource());
        file = saveFileChooser.getSelectedFile();
        
        ///WRITING IN THE SELECTED FILE
        FileWriter fileWriter;
        if(file!=null) //Only proceed if the file has been given a name
            try {
                fileWriter = new FileWriter(file.getAbsoluteFile()+".txt", false); //false to erase content and rewrite everything
                for(int i=0; i<lines.size(); i++){
                    fileWriter.write(lines.get(i)+"\n");
                }
                fileWriter.close();
            } catch (IOException ex) {
                System.err.println("Error in the file saving method.");
            }
        
    }
    
}
