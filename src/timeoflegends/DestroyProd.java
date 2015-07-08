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
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author JAY
 */
public class DestroyProd extends JFrame implements MouseListener, ActionListener{
    private ArrayList<Production_Tile> tiles;
    private JPanel root;
    private boolean finish;
    private final int maxtiles;
    private int count;      // num of tiles selected to build
    private JLayeredPane[] tileHolder;
    private JLabel[] circleImg;
    private JButton done;
    private int choice;
    private int[] res;
    private final int playerpos;
    private final int tiletype;
    DestroyProd(int max, int _tiletype, int pos) {    // max num of building to be seleted
        playerpos = pos;
        tiletype = _tiletype;
        maxtiles = max;
        count=0;
        finish = false;
        tiles.addAll(Arrays.asList(PlayerHolder.getPlayer(playerpos).getBoard().getProductionArea().getTiles()));
//        tiles = (ArrayList<Production_Tile>) Arrays.asList();
        choice = -1;
        init();
        try{while(!finish){    Thread.sleep(1);   }       // wait till selection done
        }catch(Exception ex){   ex.printStackTrace();  }
    }
    private void init(){
        root = new JPanel(new GridLayout(4, 4));
        root.setOpaque(false);
        root.setBounds(0, 0, 400, 400);
        tileHolder = new JLayeredPane[16];
        circleImg = new JLabel[16];
        for(int i=0;i<tiles.size();i++){
            tileHolder[i] = new JLayeredPane();
            JLabel img = new JLabel(tiles.get(i)==null?null:
                    (tiles.get(i).getType()==tiletype)?tiles.get(i).getImage():null);
            img.setOpaque(false);
            img.setBounds(0, 0, 100, 100);
            tileHolder[i].addMouseListener(tiles.get(i)==null?null:
                    (tiles.get(i).getType()==tiletype)?this:null);
            tileHolder[i].add(img,JLayeredPane.DEFAULT_LAYER,0);
            tileHolder[i].setName(""+i);
            circleImg[i] = new JLabel(new ImageIcon(Constants.circleImg));
            circleImg[i].setVisible(false);
            circleImg[i].setBounds(0, 0, 100, 100);
            tileHolder[i].add(circleImg[i],JLayeredPane.PALETTE_LAYER,0);
            tileHolder[i].setOpaque(false);
            root.add(tileHolder[i]);
        }
        this.add(root);
        done = new JButton("Done");
        done.setBounds(10, 410, 70, 30);
        done.addActionListener(this);
        this.add(done);
        this.setTitle("Select your building tile(s)");
        this.setSize(400, 400+70);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }
    private void changeMark(int pos){
        if(choice!=-1){
            if(choice!=pos){
                circleImg[choice].setVisible(false);   // reset old choice
                choice = pos;
                circleImg[choice].setVisible(true);   // set new choice (pos)
            }else if(choice == pos) {
                circleImg[choice].setVisible(false);
                choice=-1;
            }
        }else{
            choice = pos;
            circleImg[pos].setVisible(!circleImg[pos].isVisible());
        }
        this.revalidate();
        this.repaint();
    }
    private void check(){
        if(choice!=-1){
            Production_Tile tile = PlayerHolder.getPlayer(playerpos).getBoard().
                    getProductionArea().destroy(tiles.get(choice));
            Production_Tile_Pool.putBackTiles(new ArrayList<>(Arrays.asList(tile)));
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // finish
        check();
        finish = true;
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