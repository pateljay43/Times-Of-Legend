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
public class G_Odin extends NextAge{

    public G_Odin() {
        super(Constants.norse, "God-Odin-Card", 3);
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
        boolean ret = false; 
        if(response == JOptionPane.YES_OPTION){
            if(charge(Constants.favor, 1)){
                ret = true;
            }
        }
        boolean advance = setCost(3);
        advance(advance);
        return ret;        // current will play extra card in this turn
    }
}
