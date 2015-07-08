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
public class G_Baldr extends Explore{

    public G_Baldr() {
        super(Constants.norse, "God-Baldr-Card", 3);
    }

    @Override
    public boolean play() {
        getTiles(PlayerHolder.size());
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
                    selectTile();
                }else{
                    processAI();
                }
            }
        }else{
            for(int i=0;i<PlayerHolder.size();i++){
                if(PlayerHolder.getCurrentPlayer().isHuman()){
                    selectTile();
                }else{
                    processAI();
                }
                PlayerHolder.nextPlayer();
            }
        }
        Production_Tile_Pool.putBackTiles(pool);
        return false;
    }
    
}
