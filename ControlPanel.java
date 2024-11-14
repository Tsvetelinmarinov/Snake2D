/**
 * Snake2D
 *  Control panel
 */

package gamedata;





import java.awt.EventQueue;






/**
 *
 */
public class ControlPanel {


    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        /**
         * Launch the application
         */
        EventQueue.invokeLater(new Runnable(){

            /**
             *
             */
            @Override
            public void run() {
                Window app = new Window();
            }
        });
    }

}
