/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

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
public class PlaceVillagers extends JFrame implements ActionListener, MouseListener{
    private JLayeredPane[] tileHolder;
    private JLabel[] circleLabels;
    private JButton done;
    private int count;      // number of villagers placed
    private JPanel root;
    private final int numofhouses;
    private JLabel text;
    private boolean[] villagers;
    public PlaceVillagers() {
        count = 0;
        villagers = PlayerHolder.getCurrentPlayer().getBoard().getVillagerOnProductionArea();
//        for(boolean b : villagers){
//            if(b){
//                count++;
//            }
//        }
        numofhouses = PlayerHolder.getCurrentPlayer().getBoard().getCityArea().numOfHouses();
        init();
        try{
            while(PlayerHolder.getCurrentPlayer().canChangeVillager()){
                Thread.sleep(1);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void init(){
        text = new JLabel("text");
        text.setBounds(100, 100, 100, 30);
        root = new JPanel(new GridLayout(4, 4));
        root.setOpaque(false);
        root.setBounds(0, 0, 400, 400);
        tileHolder = new JLayeredPane[16];
        circleLabels = new JLabel[16];
        Production_Tile[] tiles = PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().getTiles();
        for(int i=0;i<16;i++){
            tileHolder[i] = new JLayeredPane();
            JLabel img = new JLabel(tiles[i]==null?null:tiles[i].getImage());
            img.setOpaque(false);
            img.setBounds(0, 0, 100, 100);
            tileHolder[i].addMouseListener(tiles[i]==null?null:this);
            tileHolder[i].add(img,JLayeredPane.DEFAULT_LAYER,0);
            tileHolder[i].setName(""+i);
            circleLabels[i] = new JLabel(new ImageIcon(Constants.circleImg));
            if(villagers[i]){
                count++;
            }
            circleLabels[i].setVisible(villagers[i]);
            circleLabels[i].setBounds(0, 0, 100, 100);
            tileHolder[i].add(circleLabels[i],JLayeredPane.PALETTE_LAYER,0);
            tileHolder[i].setOpaque(false);
//            tileHolder[i].setBounds(0,0,100,100);
            root.add(tileHolder[i]);
        }
//        root.add(text);

        this.add(root);
        done = new JButton("Done");
        done.setBounds(10, 410, 70, 30);
        done.addActionListener(this);
        this.add(done);
//        this.add(text);
        this.setTitle("Place Your Villagers");
        this.setSize(400, 400+70);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setVisible(true);
//        this.pack();
        this.revalidate();
        this.repaint();
    }
    private void changeMark(int pos){
        if(!circleLabels[pos].isVisible()){
            if(count<numofhouses){      // add villager to tile
                count = count + 1;
                circleLabels[pos].setVisible(!circleLabels[pos].isVisible());
                PlayerHolder.getCurrentPlayer().getBoard().addVillagerOnProduction(pos);
            }
        }else{      // remove villager
            count = count - 1;
            circleLabels[pos].setVisible(!circleLabels[pos].isVisible());
            PlayerHolder.getCurrentPlayer().getBoard().removeVillagerOnProduction(pos);
        }
        this.revalidate();
        this.repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // finish
        PlayerHolder.getCurrentPlayer().setChangeVillager(false);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Integer pos = Integer.valueOf(((JLayeredPane)e.getSource()).getName());
        System.out.println("Clicked : "+pos);
        changeMark(pos);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
