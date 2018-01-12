
package cellautomata.input;

import cellautomata.CellAutomata;
import cellautomata.logic.LifeLogic;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyManager implements KeyListener{
    private CellAutomata cellAutomata;
    private LifeLogic lifeLogic;
    
    public KeyManager(CellAutomata cellAutomata, LifeLogic lifeLogic){
        this.cellAutomata = cellAutomata;
        this.lifeLogic = lifeLogic;
        
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
        switch(e.getKeyChar()){
            case 'p':
                System.out.println("P Pressed.");
                cellAutomata.revertPlaying();
                if(cellAutomata.isPlaying()){ //If it was previously paused, so it is now running, then update
                    //UPDATING TO THE NEW RULE
                    String newRule = cellAutomata.getDisplay().getsRuleTextField().getText()+ "/" +cellAutomata.getDisplay().getbRuleTextField().getText();
                    lifeLogic.setLogic(newRule);
                    cellAutomata.getDisplay().getPlayButton().setText("Pause");
                }
                else {
                    cellAutomata.getDisplay().getPlayButton().setText("Play");
                }
            break;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Premuto qualcosa");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
}
