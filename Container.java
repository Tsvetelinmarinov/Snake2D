/**
 * Snake2D
 *  Game panel
 */


package gamedata;






import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;






/**
 *
 */
public class Container extends JPanel implements ActionListener {



    /**
     * @params
     */

    /**
     * Screen width
     */
    static final int SCREEN_WIDTH = 600;


    /**
     * Screen height
     */
    static final int SCREEN_HEIGHT = 600;


    /**
     * Size of the one unit
     */
    static final int UNIT_SIZE = 25;


    /**
     * Game units
     */
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;


    /**
     * Snake speed
     */
    static final int SPEED = 75;


    /**
     * x axis
     */
    final int x[] = new int[GAME_UNITS];


    /**
     * y axis
     */
    final int y[] = new int[GAME_UNITS];


    /**
     * starting length of the snake
     */
    int body_parts = 6;


    /**
     * Holds the eaten apples
     */
    int apples_eaten;


    /**
     * Apple on the x axix
     */
    int appleX;


    /**
     * apple in the y axis
     */
    int appleY;


    /**
     * direction of the snake
     * start moving to the right
     */
    char direction = 'R';


    /**
     * main boolean -- true(snake is running) : false(game over)
     */
    boolean is_running = false;


    /**
     * Timer fot the ActionListener
     */
    Timer time;


    /**
     * Random number to spawn new apple random on the screen;
     */
    Random rand_num;






    /**
     *
     */
    Container(){
        rand_num = new Random();

        //setting up the panel
        this.setBackground(new Color(27,27,27));
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new ControlAdapter());
    }




    /**
     *  Start the game
     */
    public void start_game() {
        //Start the game with new apple
        spawn_apple();

        //run the game
        is_running = true;

        //set the timer to start the time
        time = new Timer(10,this);
        time.start();
    }




    /**
     * pain the components in the super class
     */
    public void paintComponent(Graphics gr) {
        //paint the super class components on the screen
        super.paintComponent(gr);

        //draw the grahics
        draw(gr);
    }




    /**
     * Draw the snake and the scores on the screen
     * Draw "Game Over!" when the game is over
     */
    public void draw(Graphics gr) {
        if(is_running) {
            //draw the apple on the screen
            gr.setColor(new Color(78,34,160));
            gr.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

            //draw the snake on the screen
            for(int i = 0; i < body_parts; i++){
                //snake's head
                if(i == 0){
                    gr.setColor(new Color(100,10,80));
                    gr.fillOval(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }else {
                    gr.setColor(new Color(50,150,120));
                    gr.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }
        }else {
            game_over(gr);
        }
    }





    /**
     * Spawns new apple in the beginning of the game
     * Spawns new apple when the old one is eaten
     */
    public void spawn_apple(){
        //spawn apple on the x axis
        appleX = rand_num.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;

        //spawn apple on the y axis
        appleX = rand_num.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }





    /**
     *  Move
     */
    public void move() {
        //main movement of the snake
        for(int i = body_parts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        //switch the direction of the snake by @param char 'direction'
        switch(direction){
            case 'U':
                y[0] -= UNIT_SIZE;
                break;
            case 'D':
                y[0] += UNIT_SIZE;
                break;
            case 'L':
                x[0] -= UNIT_SIZE;
                break;
            case 'R':
                x[0] += UNIT_SIZE;
                break;
        }
    }





    /**
     * Check for eaten apples
     */
    public void check_apple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            apples_eaten++;
            body_parts++;

            spawn_apple();
        }
    }




    /**
     * Check for collisions of the snake head with her body or with borders
     */
    public void check_collision(){
        //check if the snake's head collides with her body
        for(int i = body_parts; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                is_running = false;
            }
        }

        //check if the snake's head collides with the borders
        if(x[0] < 0) {
            is_running = false;
        }

        if(x[0] > SCREEN_WIDTH) {
            is_running = false;
        }

        if(y[0] < 0) {
            is_running = false;
        }

        if(y[0] > SCREEN_HEIGHT) {
            is_running = false;
        }


        //stop the time if the main boolean is false(game should stop running)
        if(!is_running){
            time.stop();
        }
    }




    /**
     * Game over
     */
    public void game_over(Graphics gr) {
        //draw the scores
        gr.setColor(new Color(200,200,200));
        gr.setFont(new Font("Fira Code",Font.PLAIN,25));

        FontMetrics scores_metrics = getFontMetrics(gr.getFont());
        gr.drawString("Scores : " + apples_eaten,(SCREEN_WIDTH - scores_metrics.stringWidth("Scores : " + apples_eaten)) / 2,gr.getFont().getSize());

        //draw "Game over!" on the screen
        gr.setColor(Color.red);
        gr.setFont(new Font("Fira Code",Font.BOLD,30));
        FontMetrics game_over_metrics = getFontMetrics(gr.getFont());
        gr.drawString("Game over!",(SCREEN_WIDTH - game_over_metrics.stringWidth("Game over!")) / 2,SCREEN_HEIGHT / 2);
    }





    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(is_running){
            move();
            check_apple();
            check_collision();
        }
        repaint();
    }






    /**
     * Key adapter fot the control keys
     */
    public class ControlAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent control){
            switch(control.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                break;
            }
        }

    }

}