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
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 *
 * @author JAY
 */
public class GainUnit extends JFrame implements MouseListener, ActionListener{
    private boolean finish;
    private JLayeredPane[] cardHolder;
    private final JLabel[] circleImg;
    private final JButton done;
    private ArrayList<Unit> unitDeck;
    private final int max_unit;
    private int count;
    public GainUnit(int unitType, int max) {
        max_unit = max;
        count = 0;
        finish = false;
        unitDeck  = PlayerHolder.getCurrentPlayer().getArmy().getUnits();
        cardHolder = new JLayeredPane[unitDeck.size()];
        circleImg = new JLabel[unitDeck.size()];
        int cards = 0;
        for(int i=0;i<unitDeck.size();i++){
            if(unitDeck.get(i).isType(unitType)){
                circleImg[i] = new JLabel(new ImageIcon(Constants.circleImg));
                circleImg[i].setOpaque(false);
                circleImg[i].setVisible(false);
                circleImg[i].setBounds(0,0,150,200);
                JLabel img = new JLabel(unitDeck.get(i).getImage());
                img.setOpaque(false);
                img.setBounds(0, 0, 150, 200);
                cardHolder[i] = new JLayeredPane();
                cardHolder[i].add(img, JLayeredPane.DEFAULT_LAYER, 0);
                cardHolder[i].add(circleImg[i], JLayeredPane.PALETTE_LAYER, 0);
                cardHolder[i].addMouseListener(this);
                cardHolder[i].setOpaque(false);
                cardHolder[i].setName(""+i);
                cardHolder[i].setSize(150, 200);
                add(cardHolder[i]);
                cards++;
            }else{
                circleImg[i]=null;
            }
        }
        done = new JButton("Done");
        done.setSize(70,30);
        done.setMaximumSize(new Dimension(70, 30));
        done.addActionListener(this);
        add(done);
        setTitle("Select any "+max+" unit");
        setSize((cards<5)?150*cards:600, 200*((cards/5)+1)+70);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new GridLayout(0, 4));
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        revalidate();
        repaint();
        try{
            while(!finish){     Thread.sleep(1);    }
        }catch(Exception ex){   ex.printStackTrace();   }
    }
    private void changeMark(int pos){
        if(!circleImg[pos].isVisible()){
            if(count<max_unit){      // add mark
                count = count + 1;
                circleImg[pos].setVisible(!circleImg[pos].isVisible());
            }
        }else{      // remove mark
            count = count - 1;
            circleImg[pos].setVisible(!circleImg[pos].isVisible());
        }
        revalidate();
        repaint();
    }
    private void check(){    // finish = true add all valid selection
        int offset=0;
        for(int i=0;i<circleImg.length;i++){
            if(circleImg[i]!=null && circleImg[i].isVisible()){
                circleImg[i].setVisible(!circleImg[i].isVisible());
                Unit unit = unitDeck.get(i-offset);
                if(PlayerHolder.getCurrentPlayer().getAge()>=unit.getAgeNeeded()){
            // add unit to player
                    PlayerHolder.getCurrentPlayer().getArmy().addUnit(
                        PlayerHolder.getCurrentPlayer().getDeck().drawUnitAt(i-offset));
                    offset++;
                }
            }
        }
        finish = true;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Integer pos = Integer.valueOf(((JLayeredPane)e.getSource()).getName());
        changeMark(pos);
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
