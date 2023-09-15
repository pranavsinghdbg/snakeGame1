import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener, KeyListener {

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;

    private boolean gameOver = false;


    final int Enemy_Size=25;
    private int xlen[] = new int[750];
    private int ylen[] = new int[750];
    private int moves = 0;

    private int score = 0;

    private int[] xpos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,800,825,850};
    private int[] ypos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
    private Random random = new Random();
    private Random random1 = new Random();
    private int enemy_x,enemy_y;
    private int enemy1_x,enemy1_y;
    private int lenOfSnake = 3;
    int maxsize;

    private ImageIcon leftMouth = new ImageIcon("src/resources/leftmouth.png");

    private ImageIcon rightMouth = new ImageIcon("src/resources/rightmouth.png");

    private ImageIcon  upMouth = new ImageIcon("src/resources/upmouth.png");

    private ImageIcon downMouth = new ImageIcon("src/resources/downmouth.png");

    private ImageIcon snakeBody = new ImageIcon("src/resources/snakeimage.png");

    ImageIcon enemy = new ImageIcon("src/resources/enemy.png");
    ImageIcon enemy1 = new ImageIcon("src/resources/enemy.png");

    private Timer timer;
    private int delay = 100;

    private ImageIcon snakeTitle = new ImageIcon("src/resources/snaketitle.jpg");
    Board(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(delay,this);
        timer.start();

        newEnemy();
    }

    private void newEnemy() {
        enemy_x = xpos[random.nextInt(32)];
        enemy_y = ypos[random.nextInt(23)];

        enemy1_x = xpos[random.nextInt(32)];
        enemy1_y = ypos[random.nextInt(23)];

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);
        g.drawRect(24,10,851,55);
        g.drawRect(24,74,851,576);

        snakeTitle.paintIcon(this,g,25,11);
        g.setColor(Color.BLACK);
        g.fillRect(25,75,850,575);

        if(moves==0){
            xlen[0] = 100;
            xlen[1] = 75;
            xlen[2] = 50;


            ylen[0] = 100;
            ylen[1] = 100;
            ylen[2] = 100;
        }
        if(left){
            leftMouth.paintIcon(this,g,xlen[0],ylen[0]);
        }
        if(right){
            rightMouth.paintIcon(this,g,xlen[0],ylen[0]);
        }
        if(up){
            upMouth.paintIcon(this,g,xlen[0],ylen[0]);
        }
        if(down){
            downMouth.paintIcon(this,g,xlen[0],ylen[0]);
        }

        for(int i=1; i<lenOfSnake; i++){
            snakeBody.paintIcon(this,g,xlen[i],ylen[i]);
        }
        enemy.paintIcon(this,g,enemy_x,enemy_y);
        enemy1.paintIcon(this,g,enemy1_x,enemy1_y);

        if(gameOver){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD,50));
            g.drawString("Game Over",300,300);

            g.setFont(new Font("Arial", Font.PLAIN,20));
            g.drawString("Press SPACE to Restart",320,350);

        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.PLAIN,14));
        g.drawString("Score : "+score,750,30);
        g.drawString("Length : "+lenOfSnake,750,50);

        g.dispose();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i=lenOfSnake-1;i>0; i--){
            xlen[i] = xlen[i-1];
            ylen[i] = ylen[i-1];
        }
        if(left){
            xlen[0] -= Enemy_Size;
        }
        if(right){
            xlen[0] += Enemy_Size;
        }
        if(up){
            ylen[0] -= Enemy_Size;
        }
        if(down){
            ylen[0] += Enemy_Size;
        }
        if(xlen[0]>850)xlen[0]=25;
        if(xlen[0]<25)xlen[0]=850;
        if(ylen[0]>625)ylen[0]=75;
        if(ylen[0]<75)ylen[0]=625;

        fooding();
        collision();
        repaint();
    }

    private void collision() {
        for(int i=1; i<lenOfSnake; i++){
            if(lenOfSnake>=4 && xlen[0]==xlen[i] && ylen[0]==ylen[i]){
                gameOver=true;
            }
        }
    }

    private void fooding() {
        //i am incresing score without increasing length it is lucky for player minimise risk of from length
        if(enemy1_x==xlen[0] && ylen[0]==enemy1_y){
            score++;
            newEnemy();
        }else if(enemy_x==xlen[0] && ylen[0]==enemy_y){
            //i am incresing score with increasing length it is not lucky for player because maximise risk of from length
            newEnemy();
            lenOfSnake++;
            score++;
        }

    }


    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            restart();
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT && !right){
            left = true;
            up = false;
            down = false;
            moves++;

        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && !left){
            right = true;
            up = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && !down){
            up = true;
            right = false;
            left = false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && !up){
            down = true;
            right = false;
            left = false;
            moves++;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void restart(){
        gameOver = false;
        moves = 0;
        score =0;
        lenOfSnake=3;

        right=true;
        left = false;
        up = false;
        down = false;

        timer.start();
        repaint();
    }

}
