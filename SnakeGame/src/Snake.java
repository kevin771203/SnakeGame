import java.awt.*;
import java.util.ArrayList;

public class Snake {

    private ArrayList<Node> snakeBody;

    public Snake(){
        snakeBody = new ArrayList<>();
        snakeBody.add(new Node(80,0));
        snakeBody.add(new Node(60,0));
        snakeBody.add(new Node(40,0));
        snakeBody.add(new Node(20,0));
    }

    public void drawSnake(Graphics g){

        for (int i =0; i < snakeBody.size(); i++){
            setSnakeBodyColor(g, i);
            setSnakeBodySize(g, i);
        }

    }

    private void setSnakeBodySize(Graphics g, int i) {
        Node n = snakeBody.get(i);

        if (n.x >= Main.width) {
            n.x = 0;
        }
        if (n.x < 0) {
            n.x = Main.width - Main.CELL_SIZE;
        }
        if (n.y >= Main.height) {
            n.y = 0;
        }
        if (n.y < 0) {
            n.y = Main.height - Main.CELL_SIZE;
        }

        g.fillOval(n.x, n.y, Main.CELL_SIZE, Main.CELL_SIZE);
    }

    private static void setSnakeBodyColor(Graphics g, int i) {
        if (i ==0) {
            g.setColor(Color.RED);
        }else {
            g.setColor(Color.ORANGE);
        }
    }

    public ArrayList<Node> getSnakeBody() {
        return this.snakeBody;
    }
}
