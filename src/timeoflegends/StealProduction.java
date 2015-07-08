/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Eric
 */
public class StealProduction extends JFrame{
    private final Player p;
    private Production_Tile t;
    
    public StealProduction(Player attacker, Player p){
        this.p = p;
        t= null;
        if(attacker.isHuman()){
            setTitle("Steal");
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            setLayout(new FlowLayout());
    //        setSize(200, 200);

            initComponents();
            pack();
            setVisible(true);
            try{ while(t==null){ Thread.sleep(1);} }
            catch(Exception ex) {ex.printStackTrace();}
        }else{
            Production_Tile[] pool = p.getBoard().getProductionArea().getTiles();
            Random r = new Random();
            while(t==null){
                int i = r.nextInt(16);
                t = pool[i];
                pool[i] = null;
            }
            
            attacker.getBoard().getProductionArea().add(t);
        }
    }
    
    private void initComponents(){
        Production_Tile[] tiles = p.getBoard().getProductionArea().getTiles();
        for(int i = 0; i < tiles.length; i++){
            final int j = i;
            if(tiles[i]!=null){
                JLabel label = new JLabel(tiles[i].getImage());
                label.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        removeTile(j);
                        setVisible(false);
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {}
                    @Override
                    public void mouseReleased(MouseEvent e) {}
                    @Override
                    public void mouseEntered(MouseEvent e) {}
                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
                add(label);
            }
        }
    }
    
    public Production_Tile getTile(){
        return t;
    }
    
    private void removeTile(int i){
        Production_Tile[] tiles = p.getBoard().getProductionArea().getTiles();
        t = tiles[i];
        tiles[i] = null;
    }
}