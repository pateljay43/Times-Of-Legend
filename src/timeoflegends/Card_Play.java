package timeoflegends;

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
 * @author JAY
 */
public class Card_Play extends JFrame implements ActionListener, MouseListener{
    private JPanel root;
    private int choice;      // position of choosen card to play
    private JLayeredPane[] cardHolder;
    private JLabel[] circleImg;     // image to be used for marking
    private JButton play;     // button to play selected card
    private JButton pass;
    private int width;
    private int height;
    private int cols;
    private int rows;
    private boolean finish;
    
    public Card_Play(){
        finish = false;
        choice = -1;    // nothing choosen
        // three play out of hand_held cards by each player at a time
        play();
    }
    
    private void play(){
        cols = PlayerHolder.getCurrentPlayer().getCardsinhand().size();
        rows = PlayerHolder.getCurrentPlayer().getCardsinhand().size()>0?1:0;
        width = cols * 150; // 150 -> width of each image
        height = rows * 200;    // 200 -> height of each image
        if(root!=null){     // initialize all components
            remove(root);
            remove(play);
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
        play = new JButton("Play");
        play.setName("Play");
        play.setBounds((width/2)-50, height+10, 100, 30);
        play.addActionListener(this);
        add(play);
        pass = new JButton("Pass");
        pass.setName("Pass");
        pass.setBounds((width/2)-50, height+60, 100, 30);
        pass.addActionListener(this);
        add(pass);
        setTitle("Select Card to PLAY");
        setSize(width<450?450:width,height+70+50);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        this.revalidate();
        this.repaint();
    }
    private void processAI() {
        int x = (new Random()).nextInt(PlayerHolder.getCurrentPlayer().getCardsinhand().size());
        choice = x;
    }
    private void check(){    // check if play is valid
        if(choice!=-1){
            finish = true;
        }
    }
    private void changeMark(int pos){
        if(choice!=-1 && choice!=pos){
            circleImg[choice].setVisible(false);   // reset old choice
            choice = pos;
            circleImg[choice].setVisible(true);   // set new choice (pos)
        }else{
            choice = pos;
            circleImg[pos].setVisible(!circleImg[pos].isVisible());
        }
        revalidate();
        repaint();
    }
    public boolean isfinish(){
        return finish;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = ((JButton)e.getSource()).getName();
        if(name.equalsIgnoreCase("Play")){
            check();    
        }else if(name.equals("Pass")){
            choice = -1;
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

    public Card getCard() {
        if(choice==-1){
            return null;
        }else{
            return PlayerHolder.getCurrentPlayer().getCardsinhand().remove(choice);
        }
    }
}
