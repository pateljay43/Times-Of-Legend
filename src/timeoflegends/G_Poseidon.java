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
public class G_Poseidon extends Gather{

    public G_Poseidon() {
        super(Constants.greek, "God-Poseidon-Card", 3);
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
    // gain +5 food
        if(response == JOptionPane.YES_OPTION){
            if(charge(Constants.favor, 1)){
                PlayerHolder.getCurrentPlayer().getResource().add(Constants.food, 5);
            }
        }
        placeVillagers();
        select();
        System.out.println("Gather choice: "+(choice==0?"All resource":choice));
        evaluateBoard();
        gather(true, false);        // gather from all tiles = false;

        return false;
    }
    
}
