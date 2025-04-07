import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends JPanel implements KeyListener {

    public static final int CELL_SIZE = 20;
    public static int width = 400;
    public static int height = 400;
    public static int row = height / CELL_SIZE;
    public static int column = width / CELL_SIZE;
    private Snake snake;
    private Fruit fruit;
    private Timer time;
    private int speed = 100;
    private static String direction;
    private boolean allowKeyPress;
    private static int score;
    private JFrame window;
    private static int highest_score;
    String desktop = System.getProperty("user.home") + "/Desktop/";
    String myFile = desktop + "fileName.txt";


    public Main(JFrame window) {
        readHighestScore();
        this.window = window;
        reset();
        addKeyListener(this);
    }

    public void readHighestScore() {
        try{
            File my_score = new File(myFile);
            Scanner myReader = new Scanner(my_score);
            highest_score = myReader.nextInt();
            myReader.close();
        } catch(FileNotFoundException e){
            highest_score = 0;
            try{
                File my_score = new File(myFile);
                if (my_score.createNewFile()) {
                    System.out.println("File created:" + my_score.getName());
                }
                FileWriter myWriter = new FileWriter(my_score.getName());
                myWriter.write("" + 0);
            }catch (IOException err) {
                System.out.println("An error occurred");
                err.printStackTrace();
            }
        }
    }

    public void write_a_file(int score){
        try {
            FileWriter myWriter = new FileWriter(myFile);
            if (score > highest_score) {
                myWriter.write("" + score);
                highest_score = score;
            }else{
                myWriter.write(""+ highest_score);
            }
            myWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reset() {
        score = 0;
        if (snake != null && snake.getSnakeBody() != null) {
            snake.getSnakeBody().clear();
        }

        allowKeyPress = true;
        direction = "Right";
        snake = new Snake();
        fruit = new Fruit();
        setTimer();
        updateTitle();
    }

    private void updateTitle() {
        window.setTitle("Snake Game | Score: " + score + "| Highest Score: " + highest_score);
    }

    private void setTimer() {
        time = new Timer();
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        },0,speed);
    }

    @Override
    public void paintComponent(Graphics g){
        //check if the snake bites itself
        ArrayList<Node> snakeBody = snake.getSnakeBody();
        Node head = snakeBody.get(0);
        for (int i = 1; i < snakeBody.size(); i++){
            if (snakeBody.get(i).x == head.x && snakeBody.get(i).y == head.y){
                allowKeyPress = false;
                time.cancel();
                time.purge();
                int response = JOptionPane.showOptionDialog(
                        this,
                        "Game Over!! Would you like to start again!",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        null,
                        JOptionPane.YES_OPTION
                );
                write_a_file(score);
                switch (response) {
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.YES_OPTION:
                        reset();
                        return;

                }
            }
        }
        //draw a black background
        g.fillRect(0,0, width, height);
        fruit.drawFruit(g);
        snake.drawSnake(g);


        //remove snake tail and put in head
        int snakeX = snake.getSnakeBody().getFirst().x;
        int snakeY = snake.getSnakeBody().getFirst().y;

        switch (direction) {
            case "Left" -> snakeX -= CELL_SIZE;
            case "Right" -> snakeX += CELL_SIZE;
            case "Up" -> snakeY -= CELL_SIZE;
            case "Down" -> snakeY += CELL_SIZE;
        }
        Node newHead = new Node(snakeX, snakeY);

        //check if the snake eats the fruit
        if(snake.getSnakeBody().getFirst().x == fruit.getX() &&
                snake.getSnakeBody().getFirst().y == fruit.getY()){
            fruit.setNewLocation(snake);
            fruit.drawFruit(g);
            score++;
            updateTitle();
        } else {
            snake.getSnakeBody().removeLast();
        }

        snake.getSnakeBody().addFirst(newHead);

        allowKeyPress = true;
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width,height);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Snake Game | Score: 0");
        Main game = new Main(window);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(allowKeyPress){
            if(e.getKeyCode() == 37 && !direction.equals("Right")){
                direction = "Left";
            } else if (e.getKeyCode() == 38 && !direction.equals("Down")) {
                direction = "Up";
            } else if (e.getKeyCode() == 39 && !direction.equals("Left")) {
                direction = "Right";
            } else if (e.getKeyCode() == 40 && !direction.equals("Up")) {
                direction = "Down";
            }
        }
        allowKeyPress = false;

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}