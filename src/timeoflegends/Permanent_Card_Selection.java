package timeoflegends;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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
public class Permanent_Card_Selection extends JFrame implements ActionListener, MouseListener{
    private JPanel root;
    private int count;      // number of selected cards
    private static boolean finish;  // selection finished or not
    private JLayeredPane[] cardHolder;
    private JLabel[] circleImg;     // image to be used for marking
    private JButton done;     // button to finish selection
    private int width;
    private int height;
    private int cols;
    private int rows;
    
    public Permanent_Card_Selection() {
        count = 0;
        for(int i=0;i<PlayerHolder.size();i++){
            if(PlayerHolder.getCurrentPlayer().isHuman()){
                // just mark human position
                if((PlayerHolder.getCurrentPlayer().getAge()
                    -PlayerHolder.getCurrentPlayer().getCardsinhand().size())!=0){
                    // draw frame for selection
                    redraw();
                    try{
                        while(!finish){Thread.sleep(1);}
                    }catch (Exception ex){ex.printStackTrace();}
                    finish = false;
                }
            }else{
                processAI();
            }
            PlayerHolder.nextPlayer();
        }
    }

    private void redraw() {
        cols = PlayerHolder.getHumanPlayer().getDeck().permSize()>=4?
                4:PlayerHolder.getHumanPlayer().getDeck().permSize();
        rows = (PlayerHolder.getHumanPlayer().getDeck().permSize()/4) +
                    (PlayerHolder.getHumanPlayer().getDeck().permSize()%4>0?1:0);
        width = cols * 150; // 150 -> width of each image
        height = rows * 200;    // 200 -> height of each image
        if(root==null){     // initialize all components
            root = new JPanel(new GridLayout(0, cols));
            root.setOpaque(false);
            root.setBounds(0, 0, width, height);
            cardHolder = new JLayeredPane[PlayerHolder.getHumanPlayer().getDeck().permSize()];
            circleImg = new JLabel[PlayerHolder.getHumanPlayer().getDeck().permSize()];
            for(int i=0;i<PlayerHolder.getHumanPlayer().getDeck().permSize();i++){
                circleImg[i] = new JLabel(new ImageIcon(Constants.circleImg));
                circleImg[i].setOpaque(false);
                circleImg[i].setVisible(false);
                circleImg[i].setBounds(0,0,150,200);
                cardHolder[i] = new JLayeredPane();
                cardHolder[i].addMouseListener(this);
                JLabel img = new JLabel(PlayerHolder.getHumanPlayer().
                        getDeck().getPermCard(i).getImage());
                img.setOpaque(false);
                img.setBounds(0, 0, 150, 200);
                cardHolder[i].add(img, JLayeredPane.DEFAULT_LAYER, 0);
                cardHolder[i].add(circleImg[i], JLayeredPane.PALETTE_LAYER, 0);
                cardHolder[i].setOpaque(false);
                cardHolder[i].setName(""+i);
                cardHolder[i].setBounds(0, 0, 150, 200);
                root.add(cardHolder[i]);
            }
            add(root);
            done = new JButton("Done");
            done.setBounds((width/2)-50, height+10, 100, 30);
            done.addActionListener(this);
            add(done);
            setTitle("Permanent Card selection");
            setSize(width, height+70);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setResizable(false);
            setLocationRelativeTo(null);
            setLayout(null);
            setVisible(true);
        }
        this.revalidate();
        this.repaint();
    }
    private void processAI() {
        Random r = new Random();
        /**
         * select cards depending on ai's age and 
         * previously held cards
         */
        int loop = PlayerHolder.getCurrentPlayer().getAge()
                -PlayerHolder.getCurrentPlayer().getCardsinhand().size();
        for(int i=0;i<loop;i++){
            if(r.nextInt(2)==0){
                PlayerHolder.getCurrentPlayer()
                    .addCardInHand(PlayerHolder.getCurrentPlayer().getDeck().permDraw(
                            r.nextInt(PlayerHolder.getCurrentPlayer().getDeck().permSize())));
            }
        }
    }
    private void changeMark(int pos){
        if(circleImg[pos].isVisible()){     // remove mark
            count = (circleImg[pos].isVisible())?(count-1):(count+1);
            circleImg[pos].setVisible(!circleImg[pos].isVisible());
        }else{      // add mark
            if(count<PlayerHolder.getHumanPlayer().getAge()-
                PlayerHolder.getHumanPlayer().getCardsinhand().size()){
                count = (circleImg[pos].isVisible())?(count-1):(count+1);
                circleImg[pos].setVisible(!circleImg[pos].isVisible());
            }
        }
        revalidate();
        repaint();
    }
    private void check(){    // finish = true if all selection are valid
            // add selected cards to cardsinhand
        if(PlayerHolder.getHumanPlayer().getAge()
                -PlayerHolder.getHumanPlayer().getCardsinhand().size()==0){
            System.out.println("You cannot select any card");
        }else if(count<=PlayerHolder.getHumanPlayer().getAge()
                -PlayerHolder.getHumanPlayer().getCardsinhand().size()){
            ArrayList<Integer> positions = new ArrayList<>();
            for(int i=0;i<circleImg.length;i++){
                if(circleImg[i].isVisible()){
                    positions.add(i);
                    System.out.println(""+i);
                }
            }
            PlayerHolder.getHumanPlayer().addCardInHand(
                PlayerHolder.getHumanPlayer().getDeck().permDraw(positions));
            finish = true;
        }else if(count>PlayerHolder.getHumanPlayer().getAge()
                -PlayerHolder.getHumanPlayer().getCardsinhand().size()){
            System.out.println("Select at most "+
                    (PlayerHolder.getHumanPlayer().getAge()
                            -PlayerHolder.getHumanPlayer().getCardsinhand().size())
                    +" cards");
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        check();
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
