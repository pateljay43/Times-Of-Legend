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
public class G_Forseti extends Trade{

    public G_Forseti() {
        super(Constants.norse, "God-Forseti-Card", 3);
    }

    @Override
    public boolean play() {
        int player;
        if(PlayerHolder.getCurrentPlayer().isHuman()){
            player = JOptionPane.showOptionDialog(null, "Do you want to use God Power?",
                    "God Power", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
                    null, null, null);
        }else{
            player = new Random().nextInt(2);
        }
        if(player == JOptionPane.YES_OPTION){
            if(charge(Constants.favor, 2)){
                final int currentPos = PlayerHolder.getCurrentPlayerPosition();
                final int nextPos = (currentPos+1)%PlayerHolder.size();
                final int prevPos = (currentPos+PlayerHolder.size()-1)%PlayerHolder.size();
                if(PlayerHolder.getCurrentPlayer().isHuman()){
                    String[] options = new String[]{PlayerHolder.getPlayer(nextPos).getCultureName(),
                        PlayerHolder.getPlayer(prevPos).getCultureName()};
                    player = JOptionPane.showOptionDialog(null, "Whose production tile you want to destroy?",
                            "Choose Target", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                            null, options, null);
                }else{
                    player = (new Random().nextInt(2));
                }
                player = player==0?nextPos:prevPos;
                int response;
                if(PlayerHolder.getCurrentPlayer().isHuman()){
                    String[] options = new String[]{"Food", "Favor", "Wood", "Gold"};
                    response = JOptionPane.showOptionDialog(null, "What do you want to gain?",
                            "Choose Target", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                            null, options, null);    
                }else{
                    response = new Random().nextInt(4);
                }
                response++;
                PlayerHolder.getCurrentPlayer().getResource().add(response, 
                        PlayerHolder.getPlayer(player).
                                getResource().draw(response, 5));
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
