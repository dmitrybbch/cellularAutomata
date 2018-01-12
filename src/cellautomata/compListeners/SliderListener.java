
package cellautomata.compListeners;

import cellautomata.CellAutomata;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SliderListener implements ChangeListener{
    
    private CellAutomata cellAutomata;
    
    public SliderListener(CellAutomata cellAutomata) {
        this.cellAutomata = cellAutomata;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        int fps;
        //if (!source.getValueIsAdjusting())
        fps = (int)source.getValue();
        
        cellAutomata.fps = fps;
    }
}
