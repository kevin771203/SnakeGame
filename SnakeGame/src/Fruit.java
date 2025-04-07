import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 */
public class Fruit {
    private int x;
    private int y;
    private ImageIcon img;

    /**
     *
     */
    public Fruit(){
//        this.img = new ImageIcon("fruit.png");
        this.img = new ImageIcon(getClass().getResource("fruit.png"));
        this.x = (int) (Math.floor(Math.random() * Main.column) * Main.CELL_SIZE);
        this.y = (int) (Math.floor(Math.random() * Main.row) * Main.CELL_SIZE);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    /**
     * 
     * @param g
     */
    public void drawFruit(Graphics g) {
        img.paintIcon(null, g, this.x, this.y);
    }

    /**
     *
     * @param snake
     */

    public void setNewLocation(Snake snake){
        int new_x;
        int new_y;
        boolean overlapping;
        do{
            new_x = (int) (Math.floor(Math.random() * Main.column) * Main.CELL_SIZE);
            new_y = (int) (Math.floor(Math.random() * Main.row) * Main.CELL_SIZE);
            overlapping = check_overlap(new_x,new_y,snake);
        }while(overlapping);

        this.x = new_x;
        this.y = new_y;
    }

    private boolean check_overlap(int newX, int newY, Snake snake) {
        ArrayList<Node> snakeBody = snake.getSnakeBody();

        for (int j = 0; j< snakeBody.size(); j++) {

            if (newX == snakeBody.get(j).x &&
                    newY == snakeBody.get(j).y){
                return true;
            }
        }
        return false;
    }

}
