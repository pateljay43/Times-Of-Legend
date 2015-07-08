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
public class G_Hephaestos extends NextAge{

    public G_Hephaestos() {
        super(Constants.greek, "God-Hephaestos-Card", 3);
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
                final int currentPos = PlayerHolder.getCurrentPlayerPosition();
                final int nextPos = (currentPos+1)%PlayerHolder.size();
                final int prevPos = (currentPos+PlayerHolder.size()-1)%PlayerHolder.size();
                if(PlayerHolder.getCurrentPlayer().isHuman()){
                    String[] options = new String[]{PlayerHolder.getCurrentPlayer().getCultureName(),
                        PlayerHolder.getPlayer(nextPos).getCultureName(),
                        PlayerHolder.getPlayer(prevPos).getCultureName()};
                    response = JOptionPane.showOptionDialog(null, "Whose production Tile you want to destroy?",
                            "Choose Target", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                            null, options, null);
                }else{
                    response = (new Random().nextInt(3));
                }
                destroyProd(response, 0);  // 0 = destroy any tile
            }
        }
        boolean advance = setCost(3);
        advance(advance);
        
        return false;
    }
    
}
