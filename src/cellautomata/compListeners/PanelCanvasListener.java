/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cellautomata.compListeners;

import cellautomata.CellAutomata;
import cellautomata.input.MouseManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PanelCanvasListener implements ComponentListener{
    
    private CellAutomata cellAutomata;
    
    public PanelCanvasListener(CellAutomata cellAutomata) {
        this.cellAutomata = cellAutomata;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if(!MouseManager.isLeftMouseButton()){
            JPanel panelCanvas = cellAutomata.getDisplay().getPanelCanvas();
            System.err.println(panelCanvas.getWidth() + " " + panelCanvas.getHeight());

            //cellAutomata.offScrImg = cellAutomata.getDisplay().getCanvas().createImage(panelCanvas.getWidth(), panelCanvas.getHeight());
            cellAutomata.offScrImg = panelCanvas.createImage(panelCanvas.getWidth(), panelCanvas.getHeight());
            cellAutomata.setWidth(panelCanvas.getWidth());
            cellAutomata.setHeight(panelCanvas.getHeight());
            System.err.println("-- " + cellAutomata.getWidth() + " " + cellAutomata.getHeight());
            cellAutomata.offScrGra = cellAutomata.offScrImg.getGraphics();
        }
        //cellAutomata.render();
        
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        
    }

    @Override
    public void componentShown(ComponentEvent e) {
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        
    }
    
}
