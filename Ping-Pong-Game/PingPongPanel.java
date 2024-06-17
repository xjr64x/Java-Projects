import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;

public class PingPongPanel extends JPanel implements Runnable {
    static final int WIDTH  = 1000;
    static final int HEIGHT = (int) (WIDTH * (0.55));
    static final Dimension SIZE = new Dimension(WIDTH, HEIGHT);
    static final int BALL = 20;
    static final int PWIDTH = 25;
    static final int PHEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    PingPongPanel() {
        newPaddles();
        newBall();
        score = new Score(WIDTH, HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SIZE);

        gameThread = new Thread(this);
        gameThread.start();

        
    }

    public void newBall() {
        random = new Random();
        ball = new Ball((WIDTH/2) - (BALL/2), (HEIGHT/2) - (BALL/2), BALL, BALL);
    }

    public void newPaddles() {
        paddle1 = new Paddle(0, (HEIGHT/2) - (PHEIGHT/2), PWIDTH, PHEIGHT, 1);
        paddle2 = new Paddle(WIDTH - PWIDTH, (HEIGHT/2) - (PHEIGHT/2), PWIDTH, PHEIGHT, 2);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {
        //bounces ball
        if(ball.y <= 0 || ball.y >= HEIGHT-BALL){
            ball.yDirection(-ball.yVelocity);
        }

        //bounces off paddles
        if(ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if(ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.xDirection(ball.xVelocity);
            ball.yDirection(ball.yVelocity);
        }
        if(ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity--;
            if(ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.xDirection(-ball.xVelocity);
            ball.yDirection(ball.yVelocity);
        }

        //stops paddles
        if(paddle1.y <= 0)
            paddle1.y = 0;
        if(paddle1.y >= (HEIGHT - PHEIGHT))
            paddle1.y = HEIGHT - PHEIGHT;
        if(paddle2.y <= 0)
            paddle2.y = 0;
        if(paddle2.y >= (HEIGHT - PHEIGHT))
            paddle2.y = HEIGHT - PHEIGHT;

        //point tracker & new ball
        if(ball.x <= 0) {
            score.player2++;
            newPaddles();
            newBall();
            System.out.println("Player 2: " + score.player2);
        }
        if(ball.x >= WIDTH - BALL) {
            score.player1++;
            newPaddles();
            newBall();
            System.out.println("Player 1: " + score.player1);
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if(delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }

    public class Paddle extends Rectangle {
        int id;
        int yVelocity;
        int speed = 5;

        Paddle(int x, int y, int PWIDTH, int PHEIGHT, int id) {
            super(x, y, PWIDTH, PHEIGHT);
            this.id = id;
        }

        public void keyPressed(KeyEvent e) {
            switch (id) {
                case 1:
                    if(e.getKeyCode() == KeyEvent.VK_W) {
                        yDirection(-speed);
                        move();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_S) {
                        yDirection(speed);
                        move();
                    }
                    break;
                case 2:
                    if(e.getKeyCode() == KeyEvent.VK_UP) {
                        yDirection(-speed);
                        move();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                        yDirection(speed);
                        move();
                    }
                    break;
            }
        }
        public void keyReleased(KeyEvent e) {
            switch (id) {
                case 1:
                    if(e.getKeyCode() == KeyEvent.VK_W) {
                        yDirection(0);
                        move();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_S) {
                        yDirection(0);
                        move();
                    }
                    break;
                case 2:
                    if(e.getKeyCode() == KeyEvent.VK_UP) {
                        yDirection(0);
                        move();
                    }
                    if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                        yDirection(0);
                        move();
                    }
                    break;
            }
        }

        public void yDirection(int yDirection) {
            yVelocity = yDirection;
        }

        public void move() {
            y = y + yVelocity;
        }

        public void draw(Graphics g) {
            if(id == 1)
                g.setColor(Color.BLUE);
            else
                g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    public class Ball extends Rectangle {
        Random random;
        int xVelocity;
        int yVelocity;
        int speed = 2;

        Ball(int x, int y, int BWIDTH, int BHEIGHT) {
            super(x, y, BWIDTH, BHEIGHT);
            random = new Random();
            int randomXDirection = random.nextInt(2);
            if(randomXDirection == 0)
                randomXDirection--;
            xDirection(randomXDirection * speed);

            int randomYDirection = random.nextInt(2);
            if(randomYDirection == 0)
                randomYDirection--;
            yDirection(randomYDirection * speed);
        }

        public void xDirection(int randomX) {
            xVelocity = randomX;
        }
        public void yDirection(int randomY) {
            yVelocity = randomY;
        }

        public void move() {
            x += xVelocity;
            y += yVelocity;
        }

        public void draw(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillOval(x, y, height, width);
        }
    }

    public class Score extends Rectangle {
        static int SWIDTH;
        static int SHEIGHT;
        int player1;
        int player2;

        Score(int W, int H) {
            Score.SWIDTH = W;
            Score.SHEIGHT = H;
        }

        public void draw(Graphics g) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Consolas", Font.PLAIN, 60));
            g.drawLine(WIDTH/2, 0, WIDTH/2, HEIGHT);
            g.drawString(String.valueOf(player1/10) + String.valueOf(player1%10), (WIDTH/2) - 85, 50);
            g.drawString(String.valueOf(player2/10) + String.valueOf(player2%10), (WIDTH/2) + 20, 50);
        }
    }
}