/**
 * Snake 2D
 *  Game panel
 */

package gamedata;






import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;







/**
 *
 */
public class Container extends JPanel implements ActionListener, MouseListener {

    /**
     * Menu panel
     */
    private JPanel menu_panel = new JPanel();


    /**
     * Scores label
     */
    private JLabel scores_lbl = new JLabel();


    /**
     * Menu bar
     */
    private JMenuBar menu_bar = new JMenuBar();


    /**
     * Main menu
     */
    private JMenu menu = new JMenu("Menu");


    /**
     * new game
     */
    private JMenuItem new_game = new JMenuItem("new game");


    /**
     * Screen width
     */
    private static final int SCREEN_WIDTH = 600;


    /**
     * Screen height
     */
    private static final int SCREEN_HEIGHT = 600;


    /**
     * Size of one game unit
     */
    private static final int UNIT_SIZE = 20;


    /**
     * Game units(quantity)
     */
    private static final int UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;


    /**
     * Snake speed
     */
    private static final int SPEED = 120;


    /**
     * x velocity
     */
    private final int[] x_axis = new int[UNITS];


    /**
     * y velocity
     */
    private final int[] y_axis = new  int[UNITS];


    /**
     * Apple on x velocity
     */
    private int appleX;


    /**
     * Apple on y velocity
     */
    private int appleY;


    /**
     * Starting body parts of the snake
     */
    private int body_parts = 6;


    /**
     * Scores
     */
    private int scores = 0;


    /**
     * Main boolean
     */
    private boolean isRunning = false;


    /**
     * Direction of the snake
     *  starts moving right in the beginning
     */
    private char direction = 'R';


    /**
     * Timer
     */
    private Timer tmr;


    /**
     * Random number to spawn an apple
     */
    private Random rand_num;





    /**
     *
     */
    Container(){
        //Initialize random number
        rand_num = new Random();

        //Menu bar and panel
        menu_panel.setBounds(-1,-1,600,35);
        menu_panel.setBackground(new Color(32,30,34));
        menu_panel.setBorder(BorderFactory.createLineBorder(new Color(78,34,160)));
        menu_panel.setLayout(null);

        //scores label
        scores_lbl.setBounds(220,4,200,30);
        scores_lbl.setFont(new Font("Fira Code",Font.ITALIC,27));
        scores_lbl.setText("Scores " + scores);
        scores_lbl.setForeground(Color.white);
        scores_lbl.addMouseListener(this);
        menu_panel.add(scores_lbl);

        //menu bar
        menu_bar.setBounds(0,0,50,33);
        menu_bar.setBackground(new Color(32,30,34));
        menu_bar.setBorder(null);
        menu_panel.add(menu_bar);

        //Main menu
        menu.setFont(new Font("Fira Code",Font.PLAIN,16));
        menu.setForeground(Color.white);
        menu.setBackground(menu_bar.getBackground());
        menu.setBorder(null);
        menu.addMouseListener(this);
        menu_bar.add(menu);

        //new game
        new_game.setFont(new Font("Fira Code",Font.PLAIN,15));
        new_game.setForeground(Color.white);
        new_game.setBackground(new Color(32,30,34));
        new_game.setBorder(null);
        new_game.addActionListener(this);
        new_game.addMouseListener(this);
        menu.add(new_game);

        //setting up the container
        this.setBackground(new Color(32,30,34));
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(new ControlAdapter());
        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        this.add(menu_panel);

        //start the game
        start_game();

    }




    /**
     * ------------------------ ActionListener ----------------------
     * @param actionEvent
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(isRunning){
            move();
            check_coll();
            check_apple();
            scores_lbl.setText("Scores " + scores);
        }
        repaint();


        /**
         * new game
         */
        if(actionEvent.getSource() == new_game){
            //Warning
            JOptionPane.showMessageDialog(null,"Scores will be lost!");

            //reset the scores
            scores = 0;

            //center the snake
            x_axis[0] = getWidth() / 2;
            y_axis[0] = getHeight() / 2;

            //set direction to the right
            direction = 'R';

            //start the game
            isRunning = true;
            new_apple();
        }

    }






    /**
     * ------------------ Mouse Listener ------------------------
     * @param mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    /**
     * @param mouseEvent
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    /**
     * @param mouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    /**
     * @param mouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        /**
         * Scores label
         */
        if(mouseEvent.getSource() == scores_lbl) {
            scores_lbl.setForeground(new Color(78,34,160));
        }

        /**
         * Main menu
         */
        if(mouseEvent.getSource() == menu) {
            menu.setForeground(new Color(78,34,160));
        }

        /**
         * new game
         */
        if(mouseEvent.getSource() == new_game){
            new_game.setFont(new Font((String)new_game.getFont().getFamily(),Font.PLAIN,17));
        }

    }

    /**
     * @param mouseEvent
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        /**
         * Scores label
         */
        if(mouseEvent.getSource() == scores_lbl) {
            scores_lbl.setForeground(Color.white);
        }

        /**
         * Main menu
         */
        if(mouseEvent.getSource() == menu) {
            menu.setForeground(Color.white);
        }

        /**
         * new game
         */
        if(mouseEvent.getSource() == new_game){
            new_game.setFont(new Font((String)new_game.getFont().getFamily(),Font.PLAIN,15));
        }

    }





    /**
     *  Start the game
     */
    public void start_game(){
        //run the game
        isRunning = true;

        //spawn an apple
        new_apple();

        //set the timer
        tmr = new Timer(SPEED,this);
        tmr.start();
    }



    /**
     * Spawn an apple in the beginning and every time when the old one is eaten
     */
    public void new_apple(){
        //x coordinates of the apple
        appleX = rand_num.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;

        //y coordinates of the apple
        appleY = rand_num.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
    }




    /**
     * Draw the graphics
     */
    public void draw(Graphics gr) {
        if(isRunning){
            //draw the apple
            gr.setColor(Color.RED);
            gr.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

            //draw the snake
            for(int i = 0; i < body_parts; i++){
                //the head
                if(i == 0){
                    gr.setColor(Color.GREEN);
                    gr.fill3DRect(x_axis[i],y_axis[i],UNIT_SIZE,UNIT_SIZE,true);
                }else{
                    //the body
                    gr.setColor(new Color(140,44,40));
                    gr.fill3DRect(x_axis[i],y_axis[i],UNIT_SIZE,UNIT_SIZE,false);
                }
            }
        }else{
            gameOver(gr);
        }
    }



    /**
     * Paint components in the super class
     */
    @Override
    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        draw(gr);
    }



    /**
     * Game over
     */
    public void gameOver(Graphics gr) {
        //cast the graphics to 2D graphics
        Graphics2D grp = (Graphics2D)gr;

        //draw "Game over!"
        grp.setFont(new Font("Fira Code",Font.PLAIN,28));
        grp.setColor(Color.RED);
        FontMetrics over_mtrcs = getFontMetrics(grp.getFont());
        grp.drawString("Game over!",(SCREEN_WIDTH - over_mtrcs.stringWidth("Game over!")) / 2, SCREEN_HEIGHT / 2);
    }



    /**
     * Move
     */
    public void move(){
        for(int i = body_parts; i > 0; i--){
            x_axis[i] = x_axis[i - 1];
            y_axis[i] = y_axis[i - 1];
        }

        //change direction
        switch(direction){
            case 'R':
                x_axis[0] += UNIT_SIZE;
                break;
            case 'L':
                x_axis[0] -= UNIT_SIZE;
                break;
            case 'U':
                y_axis[0] -= UNIT_SIZE;
                break;
            case 'D':
                y_axis[0] += UNIT_SIZE;
            break;
        }
    }


    /**
     * Check for collisions
     */
    public void check_coll(){
        for(int i = body_parts; i > 0; i--){
            if((x_axis[i] == x_axis[0]) && (y_axis[i] == y_axis[0])){
                isRunning = false;
            }
        }

        if(x_axis[0] < 0) {
            x_axis[0] = getWidth() - UNIT_SIZE;
        }

        if(x_axis[0] > getWidth()) {
            x_axis[0] = 0;
        }

        if(y_axis[0] < 0) {
            y_axis[0] = getWidth() - UNIT_SIZE - menu_panel.getHeight();
        }

        if(y_axis[0] > getWidth() - menu_panel.getHeight()){
            y_axis[0] = 0;
        }
    }




    /**
     * Check for eaten apples
     */
    public void check_apple(){
        if((x_axis[0] == appleX) && (y_axis[0] == appleY)){
            scores++;
            body_parts++;

            new_apple();
        }
    }





    /**
     * Key adapter for control keys
     */
    public class ControlAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent control) {
            switch(control.getKeyCode()){
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
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
