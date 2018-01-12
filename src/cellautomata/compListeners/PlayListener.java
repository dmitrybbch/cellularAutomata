package cellautomata.compListeners;

import cellautomata.CellAutomata;
import cellautomata.logic.LifeLogic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PlayListener implements ActionListener{
    private CellAutomata cellAutomata;
    private LifeLogic lifeLogic;
    
    public PlayListener(CellAutomata cellAutomata, LifeLogic lifeLogic){
        this.cellAutomata = cellAutomata;
        this.lifeLogic = lifeLogic;
    }
    
    public void actionPerformed(ActionEvent e) {
        
        cellAutomata.revertPlaying();
        if(cellAutomata.isPlaying()){
            //UPDATING TO THE NEW RULE
            String newRule = cellAutomata.getDisplay().getsRuleTextField().getText()+ "/" +cellAutomata.getDisplay().getbRuleTextField().getText();
            lifeLogic.setLogic(newRule);
            cellAutomata.getDisplay().getPlayButton().setText("Pause");
        }
        else {
            cellAutomata.getDisplay().getPlayButton().setText("Play");
        }
        
    }
    
}
