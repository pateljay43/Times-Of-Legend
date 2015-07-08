/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 *
 * @author JAY
 */
public class Random_Card_Selection extends JFrame implements ActionListener{
    private JPanel cardPanel;
    private boolean finished;
    private JComboBox cardList;
    private JButton finish;
    private int result;
    
    public Random_Card_Selection(){
        result = - 1;
        finished = false;
        for(int i=0;i<PlayerHolder.size();i++){
            if(PlayerHolder.getCurrentPlayer().isHuman()){
                init();
                try{ while(!finished) {     Thread.sleep(1);    }   }
                catch(Exception ex) {   ex.printStackTrace();   }
                while(result>0){
                    PlayerHolder.getCurrentPlayer().addCardInHand(
                            PlayerHolder.getCurrentPlayer().getDeck().randDraw());
                    result--;
                }
            }else{
                int max = PlayerHolder.getCurrentPlayer().getAge()
                                -PlayerHolder.getCurrentPlayer().getCardsinhand().size();
                max = new Random().nextInt(max+1);
                while(max>0){
                    PlayerHolder.getCurrentPlayer().addCardInHand(
                        PlayerHolder.getCurrentPlayer().getDeck().randDraw());
                    max--;
                }
            }
            PlayerHolder.nextPlayer();
        }
    }

    private void init() {
        cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardList = new JComboBox();
        cardList.setModel(new DefaultComboBoxModel());
        int max = PlayerHolder.getCurrentPlayer().getAge()-PlayerHolder.getCurrentPlayer().getCardsinhand().size();
        if(max<=0){
            finished = true;
        }else{
            for(int i = 0; i <= max; i++){
                cardList.addItem(i);
                cardList.setSelectedIndex(0);
            }
            JLabel cardLbl = new JLabel("Number of Random Cards: ");
            cardPanel.add(cardLbl);
            cardPanel.add(cardList);
            add(cardPanel);
            finish = new JButton("Finish");
            finish.addActionListener(this);
            add(finish);
            setSize(500, 100);
            setLayout(new FlowLayout(FlowLayout.CENTER));
            setTitle("Random Card Selection");
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            setVisible(true);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int num = cardList.getSelectedIndex();
        if(num!=0){
            result = num;
            setVisible(false);
        }else{
            result = -1;
        }
        finished = true;
    }
}
