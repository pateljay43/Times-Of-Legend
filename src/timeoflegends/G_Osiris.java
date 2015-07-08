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
public class G_Osiris extends Recruit{

    public G_Osiris() {
        super(Constants.egyptian, "God-Osiris-Card", 3);
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
            if(charge(Constants.favor, 2)){
                if(PlayerHolder.getCurrentPlayer().getAge()!=Constants.archaicAge){
                    // gain 1 hero
                    gainUnit(Constants.hero, 1);
                }
            }
        }
        if(PlayerHolder.getCurrentPlayer().isHuman()){
            humanRecruit(4, -1,-1,-1);
        }else{
            processAI(4,-1,-1,-1);
        }
        return false;
    }
    
}

