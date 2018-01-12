
package cellautomata.compListeners;

import cellautomata.logic.LifeLogic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Dima
 */
public class ResetListener implements ActionListener{
    
    private LifeLogic lifeLogic;
    
    public ResetListener(LifeLogic lifeLogic) {
        this.lifeLogic = lifeLogic;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        lifeLogic.clear();
        /*
        for(int i=0; i<lifeLogic.getRows(); i++)
            for(int j=0; j<lifeLogic.getCols(); j++)
                lifeLogic.getCurrentState()[i][j] = -1;
        */
    }
    
}
