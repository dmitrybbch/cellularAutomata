package cellautomata.display;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;


public class Display {
    //Initial values of JSwing elements
    private boolean drawTails = true;   // Determines if cells will leave trails after dying
    private boolean drawLines = false;  // Determines if lines will be drawn between cells
    private final byte TEXT_COLS = 6;
    private final short SLIDER_MIN_SPEED = 1;
    private final short SLIDER_MAX_SPEED = 200;
    private final short PANEL_MAX_WIDTH = 2000;
    
    //Window
    private JFrame frame;
    private String title;
    private int width, height;
        
    //Elements
    private JPanel panelCanvas;
    private JPanel panelButtons;

    private JSlider speedSlider;
    private JButton loadButton;
    private JButton saveButton;
    private JButton resetButton;
    private JButton randomButton;
    private JButton playButton;
    private JTextField sRuleTextField;
    private JTextField bRuleTextField;
    private JLabel sLabel;
    private JLabel bLabel;
    private JCheckBox linesCheckBox;
    private JCheckBox tailCheckBox;
	
    public Display(String title, int width, int height){
	this.title = title;
	this.width = width;
	this.height = height;
		
	createDisplay();
    }
	
    private void createDisplay(){
	frame = new JFrame(title);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS)); //Vertical Layout
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setResizable(true);
	frame.setLocationRelativeTo(null);
                
        panelCanvas = new JPanel();
	panelCanvas.setSize(new Dimension(width, height));
	panelCanvas.setFocusable(true);
                
        panelButtons = new JPanel();
        tailCheckBox = new JCheckBox("Draw tail");
        tailCheckBox.setSelected(drawTails);
         panelButtons.add(tailCheckBox);
        linesCheckBox = new JCheckBox("Draw lines");
        linesCheckBox.setSelected(drawLines);
         panelButtons.add(linesCheckBox);
        sLabel = new JLabel("S");
         panelButtons.add(sLabel);
        sRuleTextField = new JTextField();
        sRuleTextField.setText("");
        sRuleTextField.setColumns(TEXT_COLS);
         panelButtons.add(sRuleTextField);
        bLabel = new JLabel("B");
         panelButtons.add(bLabel);
        bRuleTextField = new JTextField();
        bRuleTextField.setText("");
        bRuleTextField.setColumns(TEXT_COLS);
         panelButtons.add(bRuleTextField);
        speedSlider = new JSlider(SLIDER_MIN_SPEED, SLIDER_MAX_SPEED);   //or use speedSlider.setValue();
         panelButtons.add(speedSlider);
        loadButton = new JButton("Load");
         panelButtons.add(loadButton);
        saveButton = new JButton("Save");
         panelButtons.add(saveButton);
        resetButton = new JButton("Reset");
         panelButtons.add(resetButton);
        randomButton = new JButton("Random");
         panelButtons.add(randomButton);
        playButton = new JButton("Play");
         panelButtons.add(playButton);
               
        frame.add(panelCanvas);
        frame.add(panelButtons);
                
        panelButtons.setMaximumSize(new Dimension(PANEL_MAX_WIDTH, panelButtons.getHeight())); // NO MAGIC NUMBERS!
        panelButtons.setAlignmentY(Component.BOTTOM_ALIGNMENT);
                
        frame.setVisible(true);
	frame.pack();
                
    }

    public boolean getDrawTails() {
        return drawTails;
    }

    public void revertDrawTails(){
        drawTails = !drawTails;
    }
    public boolean getDrawLines() {
        return drawLines;
    }
    
    public void revertDrawLines(){
        drawLines = !drawLines;
    }
	
    public JFrame getFrame(){
        return frame;
    }
        
    public JSlider getSpeedSlider() {
        return speedSlider;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JButton getPlayButton() {
        return playButton;
    }
    
    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
   
    public JPanel getPanelCanvas() {
        return panelCanvas;
    }

    public JPanel getPanelButtons() {
        return panelButtons;
    }
        
    public JTextField getsRuleTextField() {
        return sRuleTextField;
    }

    public JTextField getbRuleTextField() {
        return bRuleTextField;
    }

    public JButton getRandomButton() {
        return randomButton;
    }

    public JCheckBox getLinesCheckBox() {
        return linesCheckBox;
    }
        
    public JCheckBox getTailCheckBox() {
        return tailCheckBox;
    }
    
    //Static methods
    
    public static void alert(String msg){
        JOptionPane.showMessageDialog(null, msg);
    }
        
}
