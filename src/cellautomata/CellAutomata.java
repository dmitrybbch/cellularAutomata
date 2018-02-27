
package cellautomata;

import cellautomata.compListeners.*;
import cellautomata.display.Display;
import cellautomata.input.*;
import cellautomata.logic.LifeLogic;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentListener;
import java.awt.KeyboardFocusManager;


/**
 *
 * @author DzmitryBabich
 */
public class CellAutomata implements Runnable{
    
    //Stuff
    public int fps;
    public float fadeSpeed;
    
    //Logic
    private final int COLS, ROWS;
    private int startingSRule;
    private int startingBRule;
    private int RANDOMIZER_PERCENT;
    
    private Color ONCELL_COLOR;
    private final Color START_FADE_COLOR;
    private final Color END_FADE_COLOR;
    private final Color BACKGROUND_COLOR;
    private final Color LINES_COLOR;
    
    //Window specs
    private Display display;
    private int width, height;
    private String title;
    
    public Image offScrImg;
    public Graphics offScrGra;
    
    //Status, Thread
    private boolean running = false;
    private Thread mainThread;
    private boolean playing = false;
    private LifeLogic lifeLogic;
    
    //Device Input
    private KeyboardFocusManager kFocManager;
    private KeyDispatcher keyDispatcher;
    private KeyManager keyManager;
    private MouseManager mouseManager;
    
    //Component Listeners
    private LoadListener loadListener;
    private SaveListener saveListener;
    private ResetListener resetListener;
    private SliderListener sliderListener;
    private RandomListener randomListener;
    private PlayListener playListener;
    private ComponentListener panelCanvasListener;
    private CheckBoxListener checkBoxListener;
    private TailCheckBoxListener tailCheckBoxListener;
    
    //Constructor
    public CellAutomata(String title, int width, int height, int fps, int cols, int rows, int startingSRule, int startingBRule, Color bgColor, Color onCellColor, Color lineColor, Color startFadeColor, Color endFadeColor, float fadeSpeed, int randomizerPercent){
        this.height = height;
        this.width = width;
        this.title = title;
        this.fps = fps;
        this.RANDOMIZER_PERCENT = randomizerPercent;
        
        this.COLS = cols;
        this.ROWS = rows;
        this.startingSRule = startingSRule;
        this.startingBRule = startingBRule;
        this.BACKGROUND_COLOR = bgColor;
        this.ONCELL_COLOR = onCellColor;
        this.START_FADE_COLOR = startFadeColor;
        this.END_FADE_COLOR = endFadeColor;
        this.LINES_COLOR = lineColor;
        this.fadeSpeed = fadeSpeed;
        
        lifeLogic = new LifeLogic(startingSRule +"/"+ startingBRule, COLS, ROWS);
        
        keyDispatcher = new KeyDispatcher(this, lifeLogic);
        keyManager = new KeyManager(this, lifeLogic);
        mouseManager = new MouseManager(this, lifeLogic);
        
        checkBoxListener = new CheckBoxListener(this);
        tailCheckBoxListener = new TailCheckBoxListener(this);
        loadListener = new LoadListener(lifeLogic);
        saveListener = new SaveListener(lifeLogic, this);
        resetListener = new ResetListener(lifeLogic);
        sliderListener = new SliderListener(this);
        randomListener  = new RandomListener(lifeLogic, RANDOMIZER_PERCENT);
        playListener = new PlayListener(this, lifeLogic); //CallBack
        
        panelCanvasListener = new PanelCanvasListener(this);
        
    }
    
    public synchronized void start(){
        if(running)
            return;
	running = true;
        mainThread = new Thread(this);
	mainThread.start();
    }
    
    public synchronized void stop(){
	if(!running)
            return;
        running = false;
	try {
            mainThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        init();
        
	double timePerTick = 1000000000 / fps; //Max nanoSeconds between ticks
	double delta = 0;
	long now;
	long lastTime = System.nanoTime();
	long timer = 0;
	int ticks = 0;
	
        while(running){
            timePerTick = 1000000000 / fps; ///MEH?
            
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            
            if(delta >= 1){
                if (playing) tick();
		render();
		ticks++;
		delta--;
            }
            
            if(timer >= 1000_000_000){
		//System.out.println("Ticks and Frames: "+ ticks + ". Display: "+ display);
		ticks = 0;
		timer = 0;
            }
        }
	stop();
    }
    
    private void init(){
        display = new Display(title, width, height);
        offScrImg = display.getPanelCanvas().createImage(width, height);
        offScrGra = offScrImg.getGraphics();
        
        //Device Input
        kFocManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        kFocManager.addKeyEventDispatcher(keyDispatcher);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        
        //On Frame Input
        display.getPanelCanvas().addMouseMotionListener(mouseManager);
        display.getSpeedSlider().addChangeListener(sliderListener);
        display.getLoadButton().addActionListener(loadListener);
        display.getSaveButton().addActionListener(saveListener);
        display.getResetButton().addActionListener(resetListener);
        display.getRandomButton().addActionListener(randomListener);
        display.getPlayButton().addActionListener(playListener);
        display.getLinesCheckBox().addActionListener(checkBoxListener);
        display.getTailCheckBox().addActionListener(tailCheckBoxListener);
        display.getPanelCanvas().addComponentListener(panelCanvasListener);
        
        lifeLogic.setDisplay(display);
    }
    
    private void tick(){
        lifeLogic.tick();
    }
    
    public void render(){
        offScrGra.setColor(BACKGROUND_COLOR);
        //offScrGra.setColor(display.getPanelCanvas().getBackground());
        offScrGra.fillRect(0, 0, width, height);
        for(int i=0; i < ROWS ; i++){
            for(int j=0; j < COLS; j++){
                if(lifeLogic.getCurrentState()[i][j] < 0){
                    offScrGra.setColor(BACKGROUND_COLOR);
                    int x = j * width/COLS;
                    int y = i * height/ROWS;
                    offScrGra.fillRect(x, y, width/COLS, height/ROWS);
                }
                else if(lifeLogic.getCurrentState()[i][j] == 0){
                    offScrGra.setColor(ONCELL_COLOR);
                    int x = j * width/COLS;
                    int y = i * height/ROWS;
                    offScrGra.fillRect(x, y, width/COLS +1, height/ROWS +1); //WIDEN THE SQUARED WITH +1
                    //offScrGra.drawOval(x, y, width/COLS, height/ROWS);
                }
                
                else if(display.getDrawTails() && lifeLogic.getCurrentState()[i][j] > 0){ //If specified to draw tails
                    //System.err.println(draw_tail);
                    Color currentColor;
                    int r = START_FADE_COLOR.getRed();
                    int g = START_FADE_COLOR.getGreen();
                    int b = START_FADE_COLOR.getBlue();
                    int steps = 255;
                    r += ( (float)(END_FADE_COLOR.getRed() - r) / steps) * Math.min(255, lifeLogic.getCurrentState()[i][j] * fadeSpeed);
                    g += ( (float)(END_FADE_COLOR.getGreen() - g) / steps) * Math.min(255, lifeLogic.getCurrentState()[i][j] * fadeSpeed);
                    b += ( (float)(END_FADE_COLOR.getBlue() - b) / steps) * Math.min(255, lifeLogic.getCurrentState()[i][j] * fadeSpeed);
                    currentColor = new Color(r, g, b);
                    offScrGra.setColor(currentColor);
                    int x = j * width/COLS;
                    int y = i * height/ROWS;
                    offScrGra.fillRect(x, y, width/COLS +1, height/ROWS +1); //WIDEN THE SQUARES WITH +1
                }
            }
        }
        if(display.getDrawLines()){
            offScrGra.setColor(LINES_COLOR);
            for(int i = 1; i < ROWS;i++){
                int y = i * height/ROWS;
                offScrGra.drawLine(0, y, width, y);
            }
            for(int j = 1; j < COLS;j++){
                int x = j * width/COLS;
                offScrGra.drawLine(x, 0, x, height);
            }
        }
        display.getPanelCanvas().getGraphics().drawImage(offScrImg, 0, 0, display.getPanelCanvas());
    }
    
    public Display getDisplay(){
        return display;
    }
    
    public boolean isPlaying() {
        return playing;
    }
    
    public void revertPlaying() {
        playing = !playing;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCOLS() {
        return COLS;
    }

    public int getROWS() {
        return ROWS;
    }
    
    public void revertDrawLines(){
        display.revertDrawLines();
    }
    
    public void revertDrawTail(){
        display.revertDrawTails();
    }
    
    public void setWidth(int width){
        this.width = width;
    }
    
    public void setHeight(int height){
        this.height = height;
    }
}
