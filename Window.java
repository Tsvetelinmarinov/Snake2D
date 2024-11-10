/**
 * Snake2D
 *  Game window that contains the game panel
 */


package gamedata;






import javax.swing.*;






/**
 *
 */
public class Window extends JFrame {



    /**
     *
     */
     Window(){
         this.setTitle("Snake2D");
         this.add(new Container());
         this.setResizable(false);
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.pack();
         this.setVisible(true);
         this.setLocationRelativeTo(null);
     }

}