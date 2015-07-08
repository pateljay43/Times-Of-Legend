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
public class TradeUnit extends JFrame implements MouseListener, ActionListener{
//Production_Tile_Pool pool = new Production_Tile_Pool();
    private boolean finish;
    private final int max_unit;
    private int max_avail;
    private int count;
    private JLayeredPane[] cardHolder;
    private final JLabel[] circleImg;
    private final JButton done;
    private ArrayList<Unit> unitDeck;
    private final int srcType;
    private final String destType;
    public TradeUnit(int max, int _srcType, String name, int _max_avail) {
        max_avail = _max_avail;
        srcType = _srcType;
        destType = name;
        Resource r = PlayerHolder.getCurrentPlayer().getResource();
        finish = false;
        unitDeck  = PlayerHolder.getCurrentPlayer().getArmy().getUnits();
        max_unit = Math.min(max, max_avail);
        count = 0;
        cardHolder = new JLayeredPane[unitDeck.size()];
        circleImg = new JLabel[unitDeck.size()];
        int cards = 0;
        for(int i=0;i<unitDeck.size();i++){
            if(unitDeck.get(i).isType(srcType)){
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
        setTitle("Trade Unit for Mummy");
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
                boolean proceed = true;
                if(proceed){
                    count = count + 1;
                    circleImg[pos].setVisible(!circleImg[pos].isVisible());
                }
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
                Unit mummy = null;
                if(max_avail>0){
                    ArrayList<Unit> unitDeck1 = PlayerHolder.getCurrentPlayer().getDeck().getUnitDeck();
                    for(int j=0;j<unitDeck1.size();j++){
                        if(unitDeck1.get(j).getName().equalsIgnoreCase(destType)){
                            mummy = PlayerHolder.getCurrentPlayer().getDeck().getUnitDeck().remove(j);
                        }
                    }
                    Unit removedUnit = PlayerHolder.getCurrentPlayer().getArmy().removeUnit(i-offset);
                    PlayerHolder.getCurrentPlayer().getDeck().addUnit(removedUnit);
                    PlayerHolder.getCurrentPlayer().getArmy().addUnit(mummy);
                    offset++;
                    max_avail--;
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
