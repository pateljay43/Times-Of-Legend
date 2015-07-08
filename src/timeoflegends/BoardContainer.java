/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class BoardContainer extends JFrame implements ActionListener, MouseListener{
    private JLayeredPane frame; // a layeredpanel containing other panels at different levels
    private JLayeredPane holdingArea;
    private JLayeredPane productionArea;
    private JLayeredPane cityArea;
    private JPanel productionTiles;
    private JPanel cityTiles;
    private JLabel bgpanel;
    private JPanel container;
    private JLabel ha;
    private JPanel subcontainer;
    private JLabel player;
    private JPanel bottompanel;
    private JPanel tileselectpanel;
    private JLabel[] tile_pool;
    private JLabel[] productionLabels;
    private JLabel[] cityLabels;
    private JButton passbtn;
    private JButton VC;
    private JButton previous;
    private JButton next;
    private JLabel age;
    private int num_tiles = 18;
    private Production_Tile_Pool pool;
    private ArrayList<Production_Tile> tiles;
    private static boolean pass;
    private static boolean activeListener = true;
    private static boolean selection_not_done = false;
    private static int point;
    private static Victory_Cube_Placement VCFrame;
    private static Production_Tile t;
    private JPanel cubePanel;
    private JPanel vcPanel;
    private JPanel foodPanel;
    private JPanel goldPanel;
    private JPanel woodPanel;
    private JPanel favorPanel;
    
    public BoardContainer(Production_Tile_Pool p){
        pass = false;
        selection_not_done = false;
        pool = p;
        
        tileselectpanel = new JPanel(new GridLayout(0, 3));
        tileselectpanel.setBounds(805,0, 295, 600);
        add(tileselectpanel);
        this.tiles = Production_Tile_Pool.getTop_N_Tiles(num_tiles);
        tile_pool = new JLabel[18];
        redraw(PlayerHolder.getPlayer(point));
        t=null;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < PlayerHolder.size(); j++){
                if(PlayerHolder.getPlayer(j).isHuman()&&!pass){
                    activeListener = true;
                    redraw(PlayerHolder.getHumanPlayer());
                    while(!process(null, j)){}
                }else if(!PlayerHolder.getPlayer(j).isHuman()){
                    activeListener = false;
                    for(Production_Tile tile : tiles){
                        if(process(tile, j)){
                            break;
                        }
                    }
                }
            }
            for(int j = PlayerHolder.size()-1; j >= 0; j--){
                if(PlayerHolder.getPlayer(j).isHuman()&&!pass){
                    activeListener = true;
                    redraw(PlayerHolder.getHumanPlayer());
                    while(!process(null, j)){}
                }else if(!PlayerHolder.getPlayer(j).isHuman()){
                    activeListener = false;
                    for(Production_Tile tile : tiles){
                        if(process(tile, j)){
                            break;
                        }
                    }
                }
            }
        }
        selection_not_done=pass=true;
        Production_Tile_Pool.putBackTiles(tiles);
        System.out.println(""+Production_Tile_Pool.getSize());
        point=PlayerHolder.getHumanPlayerPosition();
        redraw(PlayerHolder.getPlayer(point));
    }
    /**
     * redraw will update all elements on bc according to the cp's
     * contents
     * 
     * @param cp reference to current player's object
     */
    public final void redraw(Player cp){
        productionLabels = new JLabel[16];
        cityLabels = new JLabel[16];
        frame = getLayeredPane();
        if(cp.getCulture()==Constants.greek){
            if(bgpanel!=null){
               frame.remove(bgpanel);
               frame.remove(container);
            }
            bgpanel = new JLabel(new ImageIcon(Constants.greekBoard));
        }else if(cp.getCulture()==Constants.egyptian){
            if(bgpanel!=null){
               frame.remove(bgpanel);
               frame.remove(container);
            }
            bgpanel = new JLabel(new ImageIcon(Constants.egyptBoard));
        }else{
            if(bgpanel!=null){
               frame.remove(bgpanel);
               frame.remove(container);
            }
            bgpanel = new JLabel(new ImageIcon(Constants.norseBoard));
        }
        bgpanel.setOpaque(true);
        bgpanel.setBounds(0, 0, 800, 600);
        
        container = new JPanel(new GridLayout(2, 1, 0, 0));
        container.setOpaque(false);
        container.setBounds(0, 0, 800, 600);
        
        holdingArea = new JLayeredPane();
        holdingArea.setOpaque(false);
        cubePanel = new JPanel(new GridLayout(6, 1));
        cubePanel.setOpaque(false);
        favorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        favorPanel.setOpaque(false);
        woodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        woodPanel.setOpaque(false);
        goldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        goldPanel.setOpaque(false);
        foodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        foodPanel.setOpaque(false);
        vcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        vcPanel.setOpaque(false);
        Resource res = cp.getResource();
        for(int i=0;i<res.getNumOfFavor();i++){
            JLabel l = new JLabel(new ImageIcon(Constants.IF + Constants.favorimg));
            l.setOpaque(false);
//            l.setBounds(0, 0, 35, 35);
            favorPanel.add(l);
        }
        for(int i=0;i<res.getNumOfWood();i++){
            JLabel l = new JLabel(new ImageIcon(Constants.IF + Constants.woodimg));
            l.setOpaque(false);
            woodPanel.add(l);
        }
        for(int i=0;i<res.getNumOfGold();i++){
            JLabel l = new JLabel(new ImageIcon(Constants.IF + Constants.goldimg));
            l.setOpaque(false);
            goldPanel.add(l);
        }
        for(int i=0;i<res.getNumOfFood();i++){
            JLabel l = new JLabel(new ImageIcon(Constants.IF + Constants.foodimg));
            l.setOpaque(false);
            foodPanel.add(l);
        }
        for(int i=0;i<res.getNumOfVictoryCubes();i++){
            JLabel l = new JLabel(new ImageIcon(Constants.IF + Constants.vcimg));
            l.setOpaque(false);
            vcPanel.add(l);
        }
        cubePanel.add(foodPanel);
        cubePanel.add(favorPanel);
        cubePanel.add(woodPanel);
        cubePanel.add(goldPanel);
        cubePanel.add(vcPanel);
        age = new JLabel("  "+cp.getAgeName());
        age.setSize(100, 30);
        age.setForeground(Color.green);
        cubePanel.add(age);
        cubePanel.setSize(800, 150);
        holdingArea.add(cubePanel, JLayeredPane.DEFAULT_LAYER);
        container.add(holdingArea);
        
        subcontainer = new JPanel(new GridLayout(1, 2, 0, 0));
        subcontainer.setOpaque(false);
        
        productionArea = new JLayeredPane();
        productionArea.setOpaque(false);
        productionTiles = new JPanel(new GridLayout(4, 4, 0, 0));
        productionTiles.setOpaque(false);
        Production_Tile[] cptiles = cp.getBoard().getProductionArea().getTiles();
        for(int i=0;i<cptiles.length;i++){
            if(cptiles[i]!=null){
                productionLabels[i] = new JLabel(cptiles[i].getImage());
            }else{
                productionLabels[i] = new JLabel();
            }
            productionLabels[i].setOpaque(false);
            productionLabels[i].setLocation(0, 0);
            productionTiles.add(productionLabels[i]);
        }
        productionTiles.setSize(400, 300);
        productionArea.add(productionTiles, JLayeredPane.DEFAULT_LAYER);
        subcontainer.add(productionArea);
        
        cityArea = new JLayeredPane();
        cityArea.setOpaque(false);
        cityTiles = new JPanel(new GridLayout(4, 4, 0, 0));
        cityTiles.setOpaque(false);
        Building_Tile[] citytiles;
//        citytiles = cp.getBuildingsPool().toArray(new Building_Tile[cp.getBuildingsPool().size()]);
        citytiles = cp.getBoard().getCityArea().getTiles();
        for(int i=0;i<citytiles.length;i++){
            if(citytiles[i]!=null){
                cityLabels[i] = new JLabel(citytiles[i].getImg());
            }else{
                cityLabels[i] = new JLabel();
            }
            cityLabels[i].setOpaque(false);
            cityLabels[i].setLocation(0, 0);
            cityTiles.add(cityLabels[i]);
        }
        cityTiles.setSize(400, 300);
        cityArea.add(cityTiles, JLayeredPane.DEFAULT_LAYER);
        subcontainer.add(cityArea);
        
        container.add(subcontainer);
        
        this.setTitle("Time Of Legends");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(!pass)
            this.setSize(1100,670);      // error of 20 pixel more required by image and 50 for bottom panel
        else
            this.setSize(800, 670);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        
        frame.setBounds(0, 0, 800, 600);
        frame.add(bgpanel, JLayeredPane.DEFAULT_LAYER);
        frame.add(container, JLayeredPane.PALETTE_LAYER);

        bottompanel = new JPanel(null);
        bottompanel.setBounds(0, 600, 800, 50);
        player = new JLabel("Switch Player: ");
        player.setBounds(10, 10, 100, 30);
        previous = new JButton("Previous");
        previous.setBounds(120, 10, 100, 30);
        previous.addActionListener(this);
        next = new JButton("Next");
        next.setBounds(230, 10, 100, 30);
        next.addActionListener(this);
        VC = new JButton("Victory Cards");
        VC.setBounds(340, 10, 150, 30);
        VC.addActionListener(this);
        bottompanel.add(VC);
        bottompanel.add(player);
        bottompanel.add(previous);
        bottompanel.add(next);
        this.add(bottompanel);
        if(!selection_not_done){
            if(pool==null){
                tileselectpanel = new JPanel(new GridLayout(0, 3));
                tile_pool = new JLabel[18];
                pool = new Production_Tile_Pool();
                this.tiles = Production_Tile_Pool.getTop_N_Tiles(num_tiles);
                tileselectpanel.setBounds(810,0, 290, 600);
                this.add(tileselectpanel);
            }
            refresh();
            passbtn = new JButton("Pass");
            passbtn.setBounds(900, 610, 100, 30);
            passbtn.addActionListener(this);
            this.add(passbtn);
        }else{
            pass=true;
            if(tileselectpanel!=null)
                this.remove(tileselectpanel);
        }
        this.setVisible(true);
        this.revalidate();
        this.repaint();
    }
    final void refresh(){
        for(int i=0;i<18;i++){
            if(tile_pool[i]!=null){ // remove all tiles from select area
                tileselectpanel.remove(tile_pool[i]);
                tile_pool[i]=null;
            }
        }
        for(int i=0;i<tiles.size();i++){    // reallocate labels with tiles list
            if(tile_pool[i]==null){
                tile_pool[i] = new JLabel();
                tile_pool[i].addMouseListener(this);
            }
            tile_pool[i].setIcon(tiles.get(i).getImage());
            tile_pool[i].setName(""+i);
            tile_pool[i].setOpaque(false);
            this.tileselectpanel.add(tile_pool[i]);
        }
        this.tileselectpanel.validate();
        this.tileselectpanel.repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JButton)e.getSource()).getText();
//        System.out.println(""+name+" clicked");
        if(name.equalsIgnoreCase("Next")){
            point=(point+1)%PlayerHolder.size();
            redraw(PlayerHolder.getPlayer(point));
        }else if(name.equalsIgnoreCase("Previous")){
            point=(point+2)%PlayerHolder.size();
            redraw(PlayerHolder.getPlayer(point));
        }else if(name.equalsIgnoreCase("Pass")){    // no more human turn
            pass = true;
            t = new Production_Tile(0, 0, 0, "");
//            redraw(this, this.PlayerHolder.getPlayer(point));
        }else if(name.equalsIgnoreCase("Victory Cards")){
            if(VCFrame!=null)
                VCFrame.dispose();
            VCFrame = new Victory_Cube_Placement(false);
        }
    }
    final boolean process(Production_Tile tile,int pos){
        if(tile==null){
            t=null;
            while(t==null){
                if(pass){
                   return true; 
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    Logger.getLogger(BoardContainer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            tile=t;
            t=null;     // -- extra
        }
//        System.out.println("process running for "+pos);
        boolean ret = PlayerHolder.getPlayer(pos).getBoard().getProductionArea().add(tile);
        if(ret){  // tile added to prod_area
            tiles.remove(tile);
            if(pos==0){
                point=PlayerHolder.getHumanPlayerPosition();
//                redraw(PlayerHolder.getHumanPlayer());     // redraw board -> refresh() is called inside redraw()
            }
            else{
//                refresh();
                point=PlayerHolder.getHumanPlayerPosition();
//                if(!pass)
//                    redraw(PlayerHolder.getHumanPlayer());
            }
            if(tiles.isEmpty()){        // hide tile selection section
                this.setSize(800, 670);
                this.setLocationRelativeTo(null);
            }
        }else{
//            System.out.println("Not Added");
        }
        return ret;
    }
    void change(Production_Tile tile){
        t=tile;
    }
    public Production_Tile_Pool getPool() {
        return pool;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Integer pos = Integer.valueOf(((JLabel)e.getSource()).getName());
        System.out.println(""+tiles.get(pos).getTypeName()+" clicked");
        if(!pass){
            if(activeListener){
    //            process(tiles.get(pos),0);
//                System.out.println("t changed");
                change(tiles.get(pos));
            }
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
}