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
public class G_Nephthys extends Build{

    public G_Nephthys() {
        super(Constants.egyptian, "God-Nephthys-Card", 3);
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
        int reduce=0;
        if(response == JOptionPane.YES_OPTION){
            if(charge(Constants.favor, 2)){
                reduce = 2;
            }
        }
        building(PlayerHolder.getCurrentPlayerPosition(),true, 3, reduce);
        // reduce cost of all newly add tiles by 'reduce' resource (except houses)
        return false;
    }
    
}
