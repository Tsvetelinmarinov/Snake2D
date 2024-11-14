/**
 * Snake2D
 *  Game frame
 */

package gamedata;






import javax.swing.JFrame;






/**
 *
 */
public class Window extends JFrame {


    /**
     *
     */
    Window(){

        this.add(new Container());
        this.setTitle("Snake2D");
        this.setResizable(false);
        this.setSize(600,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

    }

}
