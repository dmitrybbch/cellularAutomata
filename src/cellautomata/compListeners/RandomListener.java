package cellautomata.compListeners;

import cellautomata.logic.LifeLogic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author babich.d
 */
public class RandomListener implements ActionListener{
    private final int PERCENT;
    private LifeLogic lifeLogic;
    
    public RandomListener(LifeLogic lifeLogic, int percent){
        this.lifeLogic = lifeLogic;
        PERCENT = percent;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        lifeLogic.randomize(PERCENT);
    }
    
}
