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
public class G_Bast extends Recruit{

    public G_Bast() {
        super(Constants.egyptian, "God-Bast-Card", 3);
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
        int unitType = -1;
        int resType = -1;
        int numCost = -1;
        if(response == JOptionPane.YES_OPTION){
            if(charge(Constants.favor, 1)){
                unitType = Constants.mortal;
                resType = Constants.food;
                numCost = 1;
            }
        }
        if(PlayerHolder.getCurrentPlayer().isHuman()){
            humanRecruit(6, unitType, resType, numCost);   // max 6 units allowed for permenant card
        }else{      // process two units
            processAI(6,unitType, resType, numCost);
        }
        return false;
    }
    
}
