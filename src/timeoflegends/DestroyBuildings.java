/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 *
 * @author Eric
 */
public class DestroyBuildings extends JFrame implements MouseListener{
    
    private final int limit;
    private final Player p;
    private JLayeredPane[] cardHolder;
    private int destroyed = 0;
    private JLabel[] circle;
    private boolean[] mark;
    private int counts;
    private JButton finishbtn;
    private boolean finish;
    private Building_Tile[] tiles;

    public DestroyBuildings(Player attacker, Player target, int limit){
        this.limit = limit;
        this.p = target;
        tiles = p.getBoard().getCityArea().getTiles();
        if(attacker.isHuman()){
            finish = false;
            initComponents();
            try{    while(!finish){ Thread.sleep(1); } }
            catch(Exception ex) {ex.printStackTrace();}
        }else{
            Random r = new Random();
            limit = Math.min(this.limit, target.getBoard().getCityArea().getNumberOfBuildings());
            
            for(int i = 0; i < limit; i++){
                Building_Tile t = null;
                while(t==null){
                    t = tiles[r.nextInt(tiles.length)];
                }
                p.addBuildingToPool(p.getBoard().getCityArea().removeBuilding(t.getType()));
            }
        }
    }

    private void initComponents() {
        cardHolder = new JLayeredPane[p.getBoard().getCityArea().getNumberOfBuildings()];
        circle = new JLabel[p.getBoard().getCityArea().getNumberOfBuildings()];
        mark = new boolean[p.getBoard().getCityArea().getNumberOfBuildings()];
        int tilecount = 0;
        for(int i = 0; i < tiles.length; i++){
            if(tiles[i]!=null){
                cardHolder[i] = new JLayeredPane();
                JLabel label = new JLabel(tiles[i].getImg());
                label.addMouseListener(this);
                label.setName(""+i);
                label.setBounds(0, 0, 100, 100);
                cardHolder[i].add(label, JLayeredPane.DEFAULT_LAYER, 0);
                circle[i] = new JLabel(new ImageIcon(Constants.circleImg));
                circle[i].setVisible(mark[i]);
                circle[i].setBounds(0, 0, 100, 100);
                cardHolder[i].add(circle[i], JLayeredPane.PALETTE_LAYER, 0);
                cardHolder[i].setBounds(tilecount*100, 0, 100, 100);
                add(cardHolder[i]);
                tilecount++;
            }
        }
        finishbtn = new JButton("Finish");
        finishbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(counts<=limit){
                    for(int i=0;i<mark.length;i++){
                        if(mark[i]){
                            p.addBuildingToPool(p.getBoard().getCityArea().removeBuilding(tiles[i].getType()));
                        }
                    }
                    finish = true;
                }
            }
        });
        finishbtn.setBounds(tilecount*100+10, 0, 70, 30);
        add(finishbtn);
        setTitle("Destroy");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(null);
        setSize(p.getBoard().getCityArea().getNumberOfBuildings()*100 + 90, 120);
        setVisible(true);
    }
    
    private void changeMark(int i){
        if(mark[i]){
            // rmeove mark
            mark[i]=!mark[i];
            counts--;
        }else{
            // add mark
            if(counts<limit){
                mark[i]=!mark[i];
                counts++;
            }
        }
        circle[i].setVisible(mark[i]);
        revalidate();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int pos = Integer.parseInt(((JLabel)e.getSource()).getName());
        changeMark(pos);
        if(++destroyed==2)
            setVisible(false);
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
