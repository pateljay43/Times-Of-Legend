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
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author JAY
 */
public class TileSelector extends JFrame implements MouseListener, ActionListener{
    //Production_Tile_Pool pool = new Production_Tile_Pool();
    private ArrayList<Production_Tile> tiles;// = pool.getTop_N_Tiles(num_tiles);;
    private boolean finish;

    public ArrayList<Production_Tile> getPool() {
        return tiles;
    }
    private JLabel[] labels;
    private JButton passbtn;
    private JPanel root;
    private Production_Tile t;
    private boolean pass;
    
    public TileSelector(ArrayList<Production_Tile> _tiles) {
        pass = false;
        tiles = _tiles;
        if(PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().getNumberOfTiles()<16){
            if(PlayerHolder.getCurrentPlayer().isHuman()){
                init();
                try{
                    while(t==null&&!pass){  Thread.sleep(10);   };
                    if(!pass){
//                        PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().add(t);
                        tiles.remove(t);
                    }
                    finish = true;
                }catch(Exception ex){ex.printStackTrace();}
            }
        }
    }
    final void init(){
        if(root!=null){
            remove(root);
            root=null;
        }
        root = new JPanel(new FlowLayout());
        labels = new JLabel[tiles.size()];
        
        for(int i=0;i<tiles.size();i++){    // reallocate labels with tiles list
            labels[i] = new JLabel();
            labels[i].addMouseListener(this);
            labels[i].setIcon(tiles.get(i).getImage());
            labels[i].setName(""+i);
            labels[i].setOpaque(false);
            labels[i].setSize(100,100);
//            labels[i].setBounds(0, 0, 100, 100);
            root.add(labels[i]);
        }
        int width = tiles.size()>=4?400:tiles.size()*100;
        int height = tiles.size()<=4?100:(((tiles.size()-1)/4)+1)*100;
        root.setBounds(0, 0, width, height);
        add(root);
        passbtn = new JButton("Pass");
        passbtn.setBounds(10, height + 10, 80, 30);
        passbtn.setName("Pass");
        passbtn.addActionListener(this);
        add(passbtn);
        setTitle("Select Your Tile");
        setLayout(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(400,height+70);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private boolean check(Production_Tile tile){        // tries to add tile to current player
        System.out.println(tile.getTypeName()+" clicked");
        int[] states = PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().getState();
        Production_Tile[] tiles = PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().getTiles();
        for(int i=0;i<16;i++){
            if(tiles[i]==null&&states[i]==tile.getType()){
                PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().add(tile);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        Integer pos = Integer.valueOf(((JLabel)e.getSource()).getName());
        if(check(tiles.get(pos))){
            t=tiles.get(pos);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JButton)e.getSource()).getName();
        if(name.equalsIgnoreCase("Pass")) {
            pass = true;
        }
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

    boolean isfinish() {
        if(finish){
            finish = false;
            return true;
        }
        return false;
    }
}
