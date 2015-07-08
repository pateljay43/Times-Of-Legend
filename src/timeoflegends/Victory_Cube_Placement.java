/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JPanel;

/**
 *
 * @author Eric
 */
public class Victory_Cube_Placement extends JFrame implements ActionListener,MouseListener{
    private JPanel root;
    private int choice;      // position of choosen card to place cube -> -1 (nothing chosen)
    private JLayeredPane[] cardHolder;
    private JLabel[] circleImg;     // image to be used for marking
    private JButton place;     // button to place cube on selected card
    private final boolean active;
    private static boolean finish;
    private int width;
    private int height;
    private int cols;
    private int rows;
    
    public Victory_Cube_Placement(boolean _active){
        finish = false;
        active = _active;
        choice = -1;    // nothing choosen
        if(active){
            // each player place victory cubes
            for(int i=0;i<PlayerHolder.size();i++){
                if(Bank.getResource().getNumOfVictoryCubes()>0){
                    if(PlayerHolder.getCurrentPlayer().isHuman()){
                        play();
                        try{
                            while(!finish){Thread.sleep(1);}
                        }catch (Exception ex){ex.printStackTrace();}
                        PlayerHolder.nextPlayer();
                    }else{      // randomly place VCs
                        VictoryCards.getCard((new Random()).nextInt(4)+1).addCube(
                            Bank.getResource().removeVictoryCube(1));
                        PlayerHolder.nextPlayer();
                    }
                }
            }
        }else{
            play();
        }
    }
    private void play(){
        cols = 4;
        rows = 1;
        width = cols * 150; // 150 -> width of each image
        height = rows * 200;    // 200 -> height of each image
        if(root!=null){     // initialize all components
            root = null;
        }
        root = new JPanel(new GridLayout(rows, cols));
        root.setOpaque(false);
        cardHolder = new JLayeredPane[4];
        circleImg = new JLabel[4];
        for(int i=0;i<4;i++){
            JLabel img = new JLabel(VictoryCards.getCard(i+1).getImage());
            img.setOpaque(false);
            img.setBounds(0, 0, 150, 200);
            cardHolder[i] = new JLayeredPane();
            cardHolder[i].add(img, JLayeredPane.DEFAULT_LAYER, 0);
            circleImg[i] = new JLabel(new ImageIcon(Constants.IF + "circle.png"));
            circleImg[i].setOpaque(false);
            circleImg[i].setVisible(false);
            circleImg[i].setBounds(0,0,150,200);
            cardHolder[i].addMouseListener(active?this:null);
            cardHolder[i].add(circleImg[i], JLayeredPane.MODAL_LAYER, 0);
            JPanel cubepanel = new JPanel();
//            cubepanel.setLayout(new FlowLayout());
            cubepanel.setBounds(0, 30, 150, 170);
            cubepanel.setOpaque(false);
            for(int j=0;j<VictoryCards.getCard(i+1).getCubes();j++){
                JLabel cubes = new JLabel(new ImageIcon(Constants.IF+Constants.vcimg));
                cubes.setSize(20, 20);
                cubes.setOpaque(false);
                cubepanel.add(cubes);
                cubes.setLocation(j*20, 0);
            }
            cardHolder[i].add(cubepanel, JLayeredPane.PALETTE_LAYER, 0);
            cardHolder[i].setOpaque(false);
            cardHolder[i].setName(""+i);
            cardHolder[i].setBounds(0, 0, 150, 200);
            cardHolder[i].setLayout(null);
            root.add(cardHolder[i]);
        }
        root.setBounds(0,0,width,height);
        add(root);
        if(active){
            setTitle("Place Victory Cube");
            setSize(width,height+70);
            place = new JButton("Place");
            place.setBounds((width/2)-50, height+10, 100, 30);
            place.addActionListener(this);
            place.setVisible(active);
            add(place);
        }
        else{
            setTitle("Victory Cards");
            setSize(width,height+20);
        }
        setDefaultCloseOperation(active?JFrame.DO_NOTHING_ON_CLOSE:JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
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
        revalidate();
        repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(choice==-1)
            return;
        VictoryCards.getCard(choice+1).addCube(Bank.getResource().removeVictoryCube(1));
        finish = true;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        Integer pos = Integer.valueOf(((JLayeredPane)e.getSource()).getName());
        System.out.println("mouse clicked at"+pos);
        changeMark(pos);
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
