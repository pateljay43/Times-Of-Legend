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

/**
 *
 * @author JAY
 */
class BuildSelector extends JFrame implements MouseListener, ActionListener{
    private final ArrayList<Building_Tile> tiles;
    private JPanel root;
    private boolean finish;
    private final int maxcount;
    private int count;      // num of tiles selected to build
    private JLayeredPane[] tileHolder;
    private JLabel[] circleLabels;
    private JButton done;
    private boolean[] choice;
    private int[] res;
    private boolean build;
    private final int reduce;
    
    BuildSelector(boolean _build, int num, int _reduce) {    // max num of building to be seleted
        build = _build;
        reduce = _reduce;
        Resource r = PlayerHolder.getCurrentPlayer().getResource();
        res = new int[]{r.getNumOfFood(), r.getNumOfFavor(), r.getNumOfWood(), r.getNumOfGold()};
        maxcount = num;
        count=0;
        finish = false;
        if(build){
            tiles = PlayerHolder.getCurrentPlayer().getBuildingsPool();
        }else{
            Building_Tile[] tls = PlayerHolder.getCurrentPlayer().getBoard().getCityArea().getTiles();
            tiles = new ArrayList<>();
            tiles.addAll(Arrays.asList(tls));
//            tiles = (ArrayList<Building_Tile>) Arrays.asList(PlayerHolder.getCurrentPlayer().getBoard().getCityArea().getTiles());
        }
        
        choice = new boolean[tiles.size()];
        init();
        try{while(!finish){    Thread.sleep(1);   }       // wait till selection done
        }catch(Exception ex){   ex.printStackTrace();  }
    }
    private void init(){
        root = new JPanel(new GridLayout(4, 4));
        root.setOpaque(false);
        root.setBounds(0, 0, 400, 400);
        tileHolder = new JLayeredPane[16];
        circleLabels = new JLabel[16];
        for(int i=0;i<tiles.size();i++){
            tileHolder[i] = new JLayeredPane();
            JLabel img = new JLabel(tiles.get(i)==null?null:tiles.get(i).getImg());
            img.setOpaque(false);
            img.setBounds(0, 0, 100, 100);
            tileHolder[i].addMouseListener(tiles.get(i)==null?null:this);
            tileHolder[i].add(img,JLayeredPane.DEFAULT_LAYER,0);
            tileHolder[i].setName(""+i);
            circleLabels[i] = new JLabel(new ImageIcon(Constants.circleImg));
            circleLabels[i].setVisible(false);
            circleLabels[i].setBounds(0, 0, 100, 100);
            tileHolder[i].add(circleLabels[i],JLayeredPane.PALETTE_LAYER,0);
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
        int[] cost = tiles.get(pos).getCost();
        if(!circleLabels[pos].isVisible()){
            if(count<maxcount){      // add mark
                boolean proceed = true;
                if(build){
                    for(int i=0;i<cost.length;i++){
                        if(res[i]<cost[i]-reduce){
                            proceed=false;
                            break;
                        }
                    }
                }
                if(proceed){
                    count = count + 1;
                    circleLabels[pos].setVisible(!circleLabels[pos].isVisible());
                    choice[pos] = true;
                    for(int i=0;i<cost.length;i++){
                        res[i]-=cost[i];
                    }
                    if(tiles.get(pos).getType()!=Constants.house){
                        if(reduce>0){
                            int red = reduce;
                            for(int i=0;i<cost.length;i++){
                                if(cost[i]>=red){
                                    res[i]=res[i]+red;
                                    red=0;
                                }else if(cost[i]>0){
                                    res[i]=res[i]+cost[i];
                                    red = red-cost[i];
                                }
                                if(red==0){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }else{      // remove mark
            if(build){
                for(int i=0;i<cost.length;i++){
                    res[i]+=cost[i];
                }
                if(tiles.get(pos).getType()!=Constants.house){
                    if(reduce>0){
                        int red = reduce;
                        for(int i=0;i<cost.length;i++){
                            if(cost[i]>=red){
                                res[i]=res[i]-red;
                                red=0;
                            }else if(cost[i]>0){
                                res[i]=res[i]-cost[i];
                                red = red-cost[i];
                            }
                            if(red==0){
                                break;
                            }
                        }
                    }
                }
            }
            count = count - 1;
            choice[pos] = false;
            circleLabels[pos].setVisible(!circleLabels[pos].isVisible());
        }
        this.revalidate();
        this.repaint();
    }
    private void evaluate(){
        int offset = 0;
        for(int i=0;i<choice.length;i++){
            if(choice[i]){
                if(build){
                    Building_Tile tile = PlayerHolder.getCurrentPlayer().getBuildingsPool().get(i-offset);
                    int[] cost = tile.getCost();
                    int[] added = new int[4];
                    if(tile.getType()!=Constants.house){
                        if(reduce>0){
                            int red = reduce;
                            for(int j=0;j<cost.length;j++){
                                if(cost[j]>=red){
                                    PlayerHolder.getCurrentPlayer().getResource().add(j+1, red);
                                    added[j]=red;
                                    red=0;
                                }else if(cost[j]>0){
                                    PlayerHolder.getCurrentPlayer().getResource().add(j+1, cost[j]);
                                    red = red-cost[j];
                                    added[j]=cost[j];
                                }
                                if(red==0){
                                    break;
                                }
                            }
                        }
                    }
                    if(!PlayerHolder.getCurrentPlayer().getBoard().getCityArea().buyBuilding(tile)){    // return tile
                        PlayerHolder.getCurrentPlayer().addBuildingToPool(tile);
                        PlayerHolder.getCurrentPlayer().getResource().draw(added);
                    }else{      // tile added successfully
                        Bank.getResource().draw(added);
//                        int red = reduce;
//                        for(int j=0;j<cost.length;j++){
//                            if(cost[j]>=red){
//                                Bank.getResource().draw(j+1, red);
//                                red=0;
//                            }else if(cost[j]>0){
//                                Bank.getResource().draw(j+1, cost[j]);
//                                red = red-cost[j];
//                            }
//                            if(red==0){
//                                break;
//                            }
//                        }
                        PlayerHolder.getCurrentPlayer().drawBuildingfromPool(i-offset);
                        if(tiles.get(i).getType()!=Constants.house){
                            offset++;
                        }else if(PlayerHolder.getCurrentPlayer().getHouseinpool()==0){  // check if last house
                            offset++;
                        }    
                    }
                }else{      // destroy
                    PlayerHolder.getCurrentPlayer().addBuildingToPool(
                        PlayerHolder.getCurrentPlayer().getBoard().getCityArea().removeBuilding(
                                tiles.get(i).getType()));
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // finish
        evaluate();
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