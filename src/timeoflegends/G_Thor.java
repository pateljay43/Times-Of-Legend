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
public class G_Thor extends Gather{

    public G_Thor() {
        super(Constants.norse, "God-Thor-Card", 3);
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
                final int currentPos = PlayerHolder.getCurrentPlayerPosition();
                final int nextPos = (currentPos+1)%PlayerHolder.size();
                final int prevPos = (currentPos+PlayerHolder.size()-1)%PlayerHolder.size();
                if(PlayerHolder.getCurrentPlayer().isHuman()){
                    String[] options = new String[]{PlayerHolder.getPlayer(nextPos).getCultureName(),
                                                    PlayerHolder.getPlayer(prevPos).getCultureName()};
                    response = JOptionPane.showOptionDialog(null, "Select player whose unit you want to eliminate",
                            "Choose Target", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                            null, options, null);
                }else{
                    response = new Random().nextInt(2);
                }
                if(response==0){
                    while(PlayerHolder.getCurrentPlayerPosition()!=nextPos){
                       PlayerHolder.nextPlayer();
                    }
                }else{
                    while(PlayerHolder.getCurrentPlayerPosition()!=prevPos){
                       PlayerHolder.nextPlayer();
                    }
                }
                eliminateUnit(currentPos, Constants.myth);
                while(PlayerHolder.getCurrentPlayerPosition()!=currentPos){
                    PlayerHolder.nextPlayer();
                }
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
