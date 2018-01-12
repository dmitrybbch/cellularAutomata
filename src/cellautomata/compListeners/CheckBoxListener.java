
package cellautomata.compListeners;

import cellautomata.CellAutomata;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CheckBoxListener implements ActionListener{
    
    private CellAutomata cellAutomata;
    
    public CheckBoxListener(CellAutomata cellAutomata){
        this.cellAutomata = cellAutomata;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        cellAutomata.revertDrawLines();
    }
    
}
