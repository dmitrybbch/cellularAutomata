
package cellautomata.logic;

import cellautomata.display.Display;
import java.util.Arrays;
import java.util.Random;

/*
A common notation used to describe these automata is referred to as "S/B", which is known as its rule (or rulestring).
S (for survival) is a list of all the numbers of ON cells that cause an ON cell to remain ON.
B (for birth) is a list of all the numbers of ON cells that cause an OFF cell to turn on.
If 0 is in the list, then blank regions of the universe will turn on in one generation.
S/B is often referred to as the rule or rulestring of the given cellular automata.

Cells are integers wich are of value 1+ if dead and been alive, and if value 0 if alive, -1 if dead and never been alive.
Their value indicates the ticks passed since the last tick in wich they were alive.
*/
public class LifeLogic {
    private Display display;
    
    private String ruleString;
    private String stringState;
    private boolean[] sArray = new boolean[9]; //Array of S cases, in which cells remain ON.
    private boolean[] bArray = new boolean[9]; //Array of B cases, in which cells remain OFF
    private int[][] currentState;
    private int[][] nextState;
    private final int rows;
    private final int cols;
    
    private int grayScaleThreshold = 50;  // If a pixel's greyScale number is less than (darker than) this number, the corresponding cell will be alive.
    
    public LifeLogic(String ruleString, int cols, int rows){
        currentState = new int[rows][cols];
        nextState = new int[rows][cols];
        for(int i=0; i<rows; i++)
            for(int j=0; j<cols; j++){
                currentState[i][j] = -1;
        }
        this.rows = rows;
        this.cols = cols;
        this.ruleString = ruleString;
        stringToCases();
    }
    
    public final void stringToCases(){ ///Extract the rules from the rulestring
        String[] beforeAfter = ruleString.split("/");
        if(display != null) display.getsRuleTextField().setText(beforeAfter[0]);
        if(display != null) display.getbRuleTextField().setText(beforeAfter[1]);
        
        char[] sDigits = beforeAfter[0].toCharArray();
        char[] bDigits = beforeAfter[1].toCharArray();
        int[] sDigitInt = new int[9];
        int[] bDigitInt = new int[9];
        
        Arrays.fill(sArray, false);
        for(int i=0; i<sDigits.length; i++){
            sArray[Character.getNumericValue(sDigits[i])] = true;
        }
        Arrays.fill(bArray, false);
        for(int i=0; i<bDigits.length; i++){
            bArray[Character.getNumericValue(bDigits[i])] = true;
        }
        //for(int i=0; i<9; i++) System.out.println(s/b Array[i]);
        
        //System.out.println(ruleString);
        System.out.print("\nS:");
        for(int i=0; i<9; i++) System.out.print(sArray[i]? i : 0);
        System.out.print("\nB:");
        for(int i=0; i<9; i++) System.out.print(bArray[i]? i : 0);
        
    }
    
    public void stringToState(){ //Extract the current state form the stringstate
        String[] singleLines = stringState.split("\n");
        int numbers[][] = new int[singleLines.length][];
        
        for(int i=0; i<singleLines.length; i++){
            String[] singleNumbers = singleLines[i].split(" ");
            
            numbers[i] = new int[singleNumbers.length];
            for(int j=0; j<singleNumbers.length; j++){
                numbers[i][j] = Integer.parseInt(singleNumbers[j]);
            }
        }
        
        for(int i=0; i<numbers.length; i++)
            for(int j=0; j<numbers[i].length; j++){
                if(numbers[i][j] >= 0){
                    nextState[i][j] = numbers[i][j];
                    currentState[i][j] = numbers[i][j];
                    if(numbers[i][j] < 50){
                        nextState[i][j] = 0;
                        currentState[i][j] = 0;
                    }
                }
                else{
                    nextState[i][j] = -1;
                    currentState[i][j] = -1;
                }
            }    
    }
    
    
    public void tick(){
        
        //WARNING: Can't merge the two loops together because of the decide method being influenced by nearby cells.
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                nextState[i][j] = decide(i,j);
                if(nextState[i][j] > 0) nextState[i][j]++;
                if(nextState[i][j] > 255) nextState[i][j] = -1;
            }
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                currentState[i][j] = nextState[i][j];
            }
        }
    }
    
    public int decide(int i, int j){
        int neighbors = 0;
        
        ///////// COUNTING THE NEIGHBOURS /////
        if(j > 0){
            if(currentState[i][j-1] == 0) neighbors++;
            if(i>0) if(currentState[i-1][j-1] == 0) neighbors++;
            if(i<rows-1) if(currentState[i+1][j-1] == 0) neighbors++;
        }
        if(j < cols-1){
            if(currentState[i][j+1] == 0) neighbors++;
            if(i>0) if(currentState[i-1][j+1] == 0) neighbors++;
            if(i<rows-1) if(currentState[i+1][j+1] == 0) neighbors++;
        }
        if(i>0) if(currentState[i-1][j] == 0) neighbors++;
        if(i<rows-1) if(currentState[i+1][j] == 0) neighbors++;
        
        ///APPLYING THE DETERMINED RULES
        //S (for survival) is a list of all the numbers of ON cells that cause an ON cell to remain ON.
        //if the number of neighbours the current alive cell has is one of the 'S numbers' in the S array then stay ON
        if((currentState[i][j] == 0) && sArray[neighbors]) return 0;
        //B (for birth) is a list of all the numbers of ON cells that cause an OFF cell to turn ON.
        //if the number of neighbours the current dead cell has is one of the 'B numbers' in the B array then turn ON
        else if(currentState[i][j] != 0 && bArray[neighbors]) return 0;
        //if else, death.
        else if(currentState[i][j] == 0 && !sArray[neighbors]) return 1;
        else if(currentState[i][j] > 0 && !bArray[neighbors]) return currentState[i][j];
        
        else return -1;
    }
    
    public void randomize(int percent){
        if(percent>100) percent = 100; //No more than a hundred percent
        Random rand = new Random();
        int min = 1, max = 100;
        
        for(int i=0; i<rows; i++)
            for(int j=0; j<cols; j++)
                if(percent > rand.nextInt(100))
                    currentState[i][j] = 0;
                else
                    currentState[i][j] = -1;
    }
    
    public void clear(){
        for(int i=0; i<rows; i++)
            for(int j=0; j<cols; j++)
                currentState[i][j] = -1;
    }
    
    public int[][] getCurrentState() {
        return currentState;
    }
    
    public int[][] getNextState() {
        return nextState;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public String getRuleString() {
        return ruleString;
    }
    
    ///LOADERS
    public void setLogic(String newRuleString){
        ruleString = newRuleString;
        stringToCases();
    }
    public void setCurrentState(String stringState){
        this.stringState = stringState;
        stringToState();
    }
    
    public void setCurrentState(int[][] pixels){
        for(int i=0; i< Math.min(currentState.length, pixels.length); i++)
            for(int j=0; j< Math.min(currentState[i].length, pixels[i].length); j++)
                if(pixels[i][j] < grayScaleThreshold) currentState[i][j] = 0;
                else currentState[i][j] = pixels[i][j];
    }
    
    public void setDisplay(Display display){
        this.display = display;
    }
    
    public void testPrint(){
        for(int i=0; i<currentState.length; i++){
            System.out.print("\n");
            for(int j=0; j<currentState[i].length; j++){
                System.out.print(currentState[i][j]+" ");
            }   
        }
    }
}
