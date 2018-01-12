
package cellautomata.input;

import cellautomata.CellAutomata;
import cellautomata.logic.LifeLogic;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;


public class MouseManager implements MouseListener, MouseMotionListener{
    
    private CellAutomata cellAutomata;
    private LifeLogic lifeLogic;
    private static boolean isLeftMouseButton;
    
    public MouseManager(CellAutomata cellAutomata, LifeLogic lifeLogic) {
        this.cellAutomata = cellAutomata;
        this.lifeLogic = lifeLogic;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(SwingUtilities.isMiddleMouseButton(e))
            lifeLogic.testPrint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setIsLeftMouseButton(true);
        //System.out.println("Blub");
        
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        setIsLeftMouseButton(false);
        //System.out.println("Blub");
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //System.err.println("FLUF");
        
        int j = cellAutomata.getCOLS() * e.getX() / cellAutomata.getWidth();
        int i = cellAutomata.getROWS() * e.getY() / cellAutomata.getHeight();
        if(SwingUtilities.isLeftMouseButton(e)){
            lifeLogic.getCurrentState()[i][j] = 0;
        } else if(SwingUtilities.isRightMouseButton(e))
            lifeLogic.getCurrentState()[i][j] = -1;
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    public synchronized static boolean isLeftMouseButton() {
        return isLeftMouseButton;
    }

    public synchronized void setIsLeftMouseButton(boolean isLeftMouseButton) {
        this.isLeftMouseButton = isLeftMouseButton;
    }
    
    
    
}
