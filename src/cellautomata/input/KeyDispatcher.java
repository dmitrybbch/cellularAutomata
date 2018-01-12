
package cellautomata.input;

import cellautomata.CellAutomata;
import cellautomata.logic.LifeLogic;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

/**
 *
 * @author babich
 */
public class KeyDispatcher implements KeyEventDispatcher{
    private CellAutomata cellAutomata;
    private LifeLogic lifeLogic;

    public KeyDispatcher(CellAutomata cellAutomata, LifeLogic lifeLogic) {
        this.cellAutomata = cellAutomata;
        this.lifeLogic = lifeLogic;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent key) {
        if(key.getID() == KeyEvent.KEY_RELEASED){
            switch(key.getKeyChar()){
                case 'p':
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
                case 'r':
                    lifeLogic.clear();
                break;
                case 'd':
                break;
                    
            }
        }
        return false;
    }
    
}