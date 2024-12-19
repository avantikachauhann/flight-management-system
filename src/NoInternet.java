import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Saksham
 */
public class NoInternet extends JPanel {

        private ImageIcon image = null;

        public NoInternet(int n) {
            if(n == 1){
                image = new ImageIcon(getClass().getResource("Images/NoInternetN.gif"));
            }else if(n == 2){
                image = new ImageIcon(getClass().getResource("Images/NoInternet.gif"));
            }
            Timer timer = new Timer(40, (ActionEvent e) -> {
                repaint();
            });
            
            timer.setRepeats(false);
            timer.start();
        }

        @Override
        public Dimension getPreferredSize() {
            return image == null ? new Dimension(460, 344) : new Dimension(image.getIconWidth(), image.getIconHeight());

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // This is very important!
            int x = (getWidth() - image.getIconWidth()) / 2;
            int y = (getHeight() - image.getIconHeight()) / 2;
            image.paintIcon(this, g, x, y);
        }

}

