
package cellautomata.logic;

/**
 *
 * @author babich.d
 */
public class Cell{
    private boolean alive;
    private int age;

    public Cell(){
        alive = false;
        age = 0;
    }
    
    public Cell(boolean alive){
        this.alive = alive;
        age = 0;
    }
    
    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
}
