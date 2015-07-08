/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.HeadlessException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JProgressBar;

/**
 *
 * @author JAY
 */
public class SplashScreen extends JFrame{
    
    public SplashScreen() throws HeadlessException {
        JLayeredPane pane = getLayeredPane();
        JLabel image = new JLabel(new ImageIcon(Constants.gameImage));
        JProgressBar pb = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        JLabel loading = new JLabel("Get Ready for Adventure!!");
        
        image.setOpaque(false);
        pb.setOpaque(false);
        loading.setOpaque(false);
        
        image.setBounds(0, 0, 350, 350);
        pb.setBounds(70, 310, 200, 30);
        loading.setBounds(90,312,160,26);
        
        pane.add(image, JLayeredPane.DEFAULT_LAYER, 0);
        pane.add(pb, JLayeredPane.PALETTE_LAYER,0);
        pane.add(loading, JLayeredPane.MODAL_LAYER, 0);
        
        pane.setBounds(0,0,350,350);
        
        pb.setValue(0);
        setLayout(null);
        setUndecorated(true);
        setSize(350,350);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        while(pb.getValue()!=100){
            try{
                Thread.sleep(pb.getValue()>15?pb.getValue()>30?pb.getValue()>60?20:50:70:100); 
                pb.setValue(pb.getValue()+1);
            }
            catch(Exception ex) { ex.printStackTrace(); }
        }
    }
}
