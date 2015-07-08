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
public class UnitSelector extends JFrame implements MouseListener, ActionListener{
    //Production_Tile_Pool pool = new Production_Tile_Pool();
    private boolean finish;
    private final int max_unit;
    private int count;
    private JLayeredPane[] cardHolder;
    private final JLabel[] circleImg;
    private final JButton done;
    private int[] res;
    private ArrayList<Unit> unitDeck;
    private final int unitType;
    private final int resType;
    private final int numCost;
    private final boolean recruit;
    /**
     *
     * @param _recruit recruit new unit?
     * @param max # of units to be recruit
     * @param _unitType type of unit to apply constrain on, -1 for no constrain on units
     * @param _resType resource type to be used as cost
     * @param _numCost # of resources charged as cost to every unitType
     */
    public UnitSelector(boolean _recruit, int max, int _unitType, int _resType, int _numCost) {
        recruit = _recruit;
        unitType = _unitType;
        resType = _resType;
        numCost = _numCost;
        Resource r = PlayerHolder.getCurrentPlayer().getResource();
        res = new int[]{r.getNumOfFood(), r.getNumOfFavor(), r.getNumOfWood(), r.getNumOfGold()};
        finish = false;
        max_unit = max;
        count = 0;
        if(recruit){
            unitDeck  = PlayerHolder.getCurrentPlayer().getDeck().getUnitDeck();
        }else{
            unitDeck = PlayerHolder.getCurrentPlayer().getArmy().getUnits();
        }
        cardHolder = new JLayeredPane[unitDeck.size()];
        circleImg = new JLabel[unitDeck.size()];
        int cards=0;
        for(int i=0;i<unitDeck.size();i++){
            cardHolder[i] = new JLayeredPane();
            circleImg[i] = new JLabel(new ImageIcon(Constants.circleImg));
            circleImg[i].setOpaque(false);
            circleImg[i].setVisible(false);
            circleImg[i].setBounds(0,0,150,200);
            if(unitDeck.get(i)!=null){
                JLabel img = new JLabel(unitDeck.get(i).getImage());
                img.setOpaque(false);
                img.setBounds(0, 0, 150, 200);
                cardHolder[i].add(img, JLayeredPane.DEFAULT_LAYER, 0);
                cardHolder[i].addMouseListener(this);
                cardHolder[i].add(circleImg[i], JLayeredPane.PALETTE_LAYER, 0);
                cardHolder[i].setOpaque(false);
                cardHolder[i].setName(""+i);
                cardHolder[i].setSize(150, 200);
                add(cardHolder[i]);
                cards++;
            }
        }
        done = new JButton("Done");
        done.setSize(70,30);
        done.setMaximumSize(new Dimension(70, 30));
        done.addActionListener(this);
        add(done);
        setTitle("Select Unit");
        setSize((cards<5)?150*cards:600, 200*((cards/4)+1));
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
        final int[] cost;
        if((unitType!=-1)&&(unitDeck.get(pos).isType(unitType))){
            cost = new int[4];
            if(resType == Constants.food){
                cost[0]=numCost;
            }else if(resType == Constants.favor){
                cost[1]=numCost;
            }else if(resType == Constants.wood){
                cost[2]=numCost;
            }else if(resType == Constants.gold){
                cost[3]=numCost;
            }
        }else{
            cost = unitDeck.get(pos).getCost();
        }
        if(!circleImg[pos].isVisible()){
            if(count<max_unit){      // add mark
                boolean proceed = true;
                if(recruit){
                    for(int i=0;i<cost.length;i++){
                        if(res[i]<cost[i]){
                            proceed=false;
                            break;
                        }
                    }
                }
                if(proceed){
                    count = count + 1;
                    circleImg[pos].setVisible(!circleImg[pos].isVisible());
                    if(recruit){
                        for(int i=0;i<cost.length;i++){
                           res[i]-=cost[i];
                        }
                    }
                }
            }
        }else{      // remove mark
            if(recruit){
                for(int i=0;i<cost.length;i++){
                   res[i]+=cost[i];
                }
            }
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
                if(recruit){
                    if(PlayerHolder.getCurrentPlayer().getAge()>=unit.getAgeNeeded()){
                // add unit to player
                        final int[] cost;
                        if((unitType!=-1)&&(unit.isType(unitType))){
                            cost = new int[4];
                            if(resType == Constants.food){
                                cost[0]=numCost;
                            }else if(resType == Constants.favor){
                                cost[1]=numCost;
                            }else if(resType == Constants.wood){
                                cost[2]=numCost;
                            }else if(resType == Constants.gold){
                                cost[3]=numCost;
                            }
                        }else{
                            cost = unit.getCost();
                        }
                        Bank.getResource().add(PlayerHolder.getCurrentPlayer().getResource().draw(cost));
                        PlayerHolder.getCurrentPlayer().getArmy().addUnit(
                            PlayerHolder.getCurrentPlayer().getDeck().drawUnitAt(i-offset));
                        offset++;
                    }
                }else{
                    if(unitType!=-1){
                        if(PlayerHolder.getCurrentPlayer().getArmy().getUnits().get(i-offset).isType(unitType)){
                            PlayerHolder.getCurrentPlayer().getDeck().addUnit(
                                PlayerHolder.getCurrentPlayer().getArmy().removeUnit(i-offset));
                        }
                    }else{
                        PlayerHolder.getCurrentPlayer().getDeck().addUnit(
                            PlayerHolder.getCurrentPlayer().getArmy().removeUnit(i-offset));
                    }
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
