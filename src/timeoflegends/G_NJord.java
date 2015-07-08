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
public class G_NJord extends Build{

    public G_NJord() {
        super(Constants.norse, "God-Jord-Card", 3);
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
                building(currentPos,false, 1, 0);
                while(PlayerHolder.getCurrentPlayerPosition()!=currentPos){
                    PlayerHolder.nextPlayer();
                }
            }
        }
        building(PlayerHolder.getCurrentPlayerPosition(),true, 4, 0);
        return false;
    }
    
}
