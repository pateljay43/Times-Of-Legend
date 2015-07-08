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
import java.util.Collections;
import java.util.Random;
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
public class Return_Card_Selection extends JFrame implements ActionListener, MouseListener{
    private JPanel root;
    private JLayeredPane[] cardHolder;
    private JLabel[] circleImg;     // image to be used for marking
    private JButton finishbtn;     // button to play selected card
    private ArrayList<Integer> choice;
    private int width;
    private int height;
    private int cols;
    private int rows;
    private boolean finish;
    
    public Return_Card_Selection(){
        finish = false;
        choice = new ArrayList<>();
        // all player selects cards to be returned
        for(int i=0;i<PlayerHolder.size();i++){
            if(PlayerHolder.getCurrentPlayer().isHuman()){
                if(PlayerHolder.getCurrentPlayer().getCardsinhand().size()>0){
                    init();
                    try{    while(!finish) { Thread.sleep(1);}  }
                    catch(Exception ex){ ex.printStackTrace(); }
                    Collections.sort(choice);
                    if(!choice.isEmpty()){
                        int offset = 0;
                        for(int ch : choice){
                            PlayerHolder.getCurrentPlayer().getDeck().returnCard(
                                PlayerHolder.getCurrentPlayer().removeCardInHand(ch-offset));
                            offset++;
                        }
                    }
                }
            }else{      // AI Player
                int max = PlayerHolder.getCurrentPlayer().getCardsinhand().size();
                max = new Random().nextInt(max+1);
                while(max>0){
                    PlayerHolder.getCurrentPlayer().getDeck().returnCard(
                            PlayerHolder.getCurrentPlayer().removeCardInHand(
                                new Random().nextInt(
                                        PlayerHolder.getCurrentPlayer().getCardsinhand().size())));
                    max--;
                }
            }
            PlayerHolder.nextPlayer();
        }
    }
    
    private void init(){
        cols = PlayerHolder.getCurrentPlayer().getCardsinhand().size();
        rows = PlayerHolder.getCurrentPlayer().getCardsinhand().size()>0?1:0;
        width = cols * 150; // 150 -> width of each image
        height = rows * 200;    // 200 -> height of each image
        if(root!=null){     // initialize all components
            remove(root);
            remove(finishbtn);
            root = null;
        }
        root = new JPanel(new GridLayout(rows, cols));
        root.setOpaque(false);
        root.setBounds(0, 0, width, height);
        cardHolder = new JLayeredPane[cols];
        circleImg = new JLabel[cols];
        for(int i=0;i<PlayerHolder.getCurrentPlayer().getCardsinhand().size();i++){
            JLabel img = new JLabel(PlayerHolder.getCurrentPlayer().
                    getCardsinhand().get(i).getImage());
            img.setOpaque(false);
            img.setBounds(0, 0, 150, 200);
            cardHolder[i] = new JLayeredPane();
            cardHolder[i].add(img, JLayeredPane.DEFAULT_LAYER, 0);
            circleImg[i] = new JLabel(new ImageIcon(Constants.IF + "circle.png"));
            circleImg[i].setOpaque(false);
//            circleImg[i].setVisible(circleImg[i].isVisible());
            circleImg[i].setVisible(false);
            circleImg[i].setBounds(0,0,150,200);
//            cardHolder[i].addMouseListener(circleImg[i].isVisible()?null:this);
            cardHolder[i].addMouseListener(this);
            cardHolder[i].add(circleImg[i], JLayeredPane.PALETTE_LAYER, 0);
            cardHolder[i].setOpaque(false);
            cardHolder[i].setName(""+i);
            cardHolder[i].setBounds(0, 0, 150, 200);
            root.add(cardHolder[i]);
        }
        add(root);
        finishbtn = new JButton("Finish");
        finishbtn.setName("Finish");
        finishbtn.setBounds((width/2)-50, height+10, 100, 30);
        finishbtn.addActionListener(this);
        add(finishbtn);
        setTitle("Select Card(s) to Return");
        setSize(width<450?450:width,height+70);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        this.revalidate();
        this.repaint();
    }
    
    private void changeMark(int pos){
        if(choice.contains(pos)){       // clear mark
            choice.remove(choice.indexOf(pos));
            circleImg[pos].setVisible(false);
        }else{      // add mark
            choice.add(pos);
            circleImg[pos].setVisible(true);
        }
        revalidate();
        repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JButton)e.getSource()).getName();
        if(name.equalsIgnoreCase("Finish")){
            finish = true;  
        }        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Integer pos = Integer.valueOf(((JLayeredPane)e.getSource()).getName());
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
