/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author JAY
 */
public class G_Hermes extends Trade{

    public G_Hermes() {
        super(Constants.greek, "God-Hermes-Card", 3);
    }

    @Override
    public boolean play() {
        int response;
        if(PlayerHolder.getCurrentPlayer().isHuman()){
            response = JOptionPane.showOptionDialog(null, "Do you want to use God Power?",
                    "God Power", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
                    null, null, null);
        }else{
            response = new Random().nextInt(2);
        }
        if(response == JOptionPane.YES_OPTION){
            if(charge(Constants.favor, 1)){
                if(PlayerHolder.getCurrentPlayer().isHuman()){
                    String[] options = new String[]{"Food", "Favor", "Wood", "Gold"};
                    response = JOptionPane.showOptionDialog(null, "What do you want to gain?",
                            "Choose Target", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                            null, options, null);
                }else{
                    response = new Random().nextInt(4);
                }
                response++;
                // add max res available from bank
                PlayerHolder.getCurrentPlayer().getResource().add(response,
                        Bank.getResource().draw(response, 4));
            }
        }
        // no cost to pay
        if(PlayerHolder.getCurrentPlayer().isHuman()){
            humanTrade();
        }else{
            processAI();
        }
        trade(transaction);
        checkTemple();
        return false;
    }
    
}
