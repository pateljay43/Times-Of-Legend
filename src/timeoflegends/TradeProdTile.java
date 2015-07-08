/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author JAY
 */
public class TradeProdTile extends JFrame implements MouseListener, ActionListener{
    //Production_Tile_Pool pool = new Production_Tile_Pool();
    private boolean finish;
    private JLayeredPane[] tileHolder1;
    private JLayeredPane[] tileHolder2;
    private final JLabel[] circleImg1;
    private final JLabel[] circleImg2;
    private final JButton done;
    private Production_Tile[] tiles1;
    private Production_Tile[] tiles2;
    private final int playerpos;
    private int choice1;
    private int choice2;
    private final JPanel panel1;
    private final JPanel panel2;
    private final JPanel root;
    
    public TradeProdTile(int pos) {
        root = new JPanel(new GridLayout(0, 2));
        root.setBounds(0, 0, 800, 400);
        panel1 = new JPanel(new GridLayout(0, 4));
        panel1.setBounds(0, 0, 400, 400);
        panel2 = new JPanel(new GridLayout(0, 4));
        panel2.setBounds(0, 0, 400, 400);
        choice1 = choice2 = -1;
        playerpos = pos;
        finish = false;
        tiles1  = PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().getTiles();
        tiles2  = PlayerHolder.getPlayer(playerpos).getBoard().getProductionArea().getTiles();
        tileHolder1 = new JLayeredPane[tiles1.length];
        tileHolder2 = new JLayeredPane[tiles2.length];
        circleImg1 = new JLabel[tiles1.length];
        circleImg2 = new JLabel[tiles2.length];
//        int cards = 0;
        for(int i=0;i<tiles1.length;i++){
            circleImg1[i] = new JLabel(new ImageIcon(Constants.circleImg));
            circleImg1[i].setOpaque(false);
            circleImg1[i].setVisible(false);
            circleImg1[i].setBounds(0,0,100,100);
            circleImg2[i] = new JLabel(new ImageIcon(Constants.circleImg));
            circleImg2[i].setOpaque(false);
            circleImg2[i].setVisible(false);
            circleImg2[i].setBounds(0,0,100,100);
            tileHolder1[i] = new JLayeredPane();
            tileHolder2[i] = new JLayeredPane();
            if(tiles1[i] != null){
                JLabel img = new JLabel(tiles1[i].getImage());
                img.setOpaque(false);
                img.setBounds(0, 0, 100, 100);
                tileHolder1[i].add(img, JLayeredPane.DEFAULT_LAYER, 0);
                tileHolder1[i].addMouseListener(this);
                tileHolder1[i].setName(""+i);
//                cards++;
            }
            tileHolder1[i].setOpaque(false);
            tileHolder1[i].add(circleImg1[i], JLayeredPane.PALETTE_LAYER, 0);
            tileHolder1[i].setSize(100, 100);
            panel1.add(tileHolder1[i]);
            if(tiles2[i] != null){
                JLabel img = new JLabel(tiles2[i].getImage());
                img.setOpaque(false);
                img.setBounds(0, 0, 100, 100);
                tileHolder2[i].add(img, JLayeredPane.DEFAULT_LAYER, 0);
                tileHolder2[i].addMouseListener(this);
                tileHolder2[i].setName(""+(i+100));
//                cards++;
            }
            tileHolder2[i].setOpaque(false);
            tileHolder2[i].add(circleImg2[i], JLayeredPane.PALETTE_LAYER, 0);
            tileHolder2[i].setSize(100, 100);
            panel2.add(tileHolder2[i]);
        }
        root.add(panel1);
        root.add(panel2);
        add(root);
        done = new JButton("Done");
        done.setBounds(100, 430, 70, 30);
        done.setMaximumSize(new Dimension(70, 30));
        done.addActionListener(this);
        add(done);
        setTitle("Trade Unit for Mummy");
        setSize(800,400+70);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        revalidate();
        repaint();
        try{
            while(!finish){     Thread.sleep(1);    }
        }catch(Exception ex){   ex.printStackTrace();   }
    }
    
    private void changeMark(int player, int pos){
        if(player==1){
            if(choice1!=-1){
                if(choice1!=pos){
                    circleImg1[choice1].setVisible(false);   // reset old choice
                    choice1 = pos;
                    circleImg1[choice1].setVisible(true);   // set new choice (pos)
                }else if(choice1 == pos) {
                    circleImg1[choice1].setVisible(false);
                    choice1=-1;
                }
            }else{
                choice1 = pos;
                circleImg1[pos].setVisible(!circleImg1[pos].isVisible());
            }
        }else if(player==2){
            if(choice2!=-1){
                if(choice2!=pos){
                    circleImg2[choice2].setVisible(false);   // reset old choice
                    choice2 = pos;
                    circleImg2[choice2].setVisible(true);   // set new choice (pos)
                }else if(choice2 == pos) {
                    circleImg2[choice2].setVisible(false);
                    choice2=-1;
                }
            }else{
                choice2 = pos;
                circleImg2[pos].setVisible(!circleImg2[pos].isVisible());
            }
        }
        
        revalidate();
        repaint();
    }
    private void check(){    // finish = true add all valid selection
        if(choice1!=-1 && choice2!=-1){
            Production_Tile[] t1 = PlayerHolder.getCurrentPlayer().getBoard().
                    getProductionArea().getTiles();
            Production_Tile[] t2 = PlayerHolder.getPlayer(playerpos).getBoard().
                    getProductionArea().getTiles();
            Production_Tile tile1 = t1[choice1];
            Production_Tile tile2 = t2[choice2];
            if(tile1.getType()==tile2.getType()){
                tile1 = PlayerHolder.getCurrentPlayer().getBoard().
                        getProductionArea().destroy(tile1);
                tile2 = PlayerHolder.getPlayer(playerpos).getBoard().
                        getProductionArea().destroy(tile2);
                PlayerHolder.getCurrentPlayer().getBoard().
                        getProductionArea().add(tile2);
                PlayerHolder.getPlayer(playerpos).getBoard().
                        getProductionArea().add(tile1);
            }            
        }
        finish = true;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Integer pos = Integer.valueOf(((JLayeredPane)e.getSource()).getName());
        if(pos>100){
            changeMark(2,pos%100);
        }else{
            changeMark(1,pos);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        check();
    }
    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}