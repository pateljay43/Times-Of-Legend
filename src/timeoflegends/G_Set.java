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
public class G_Set extends NextAge{

    public G_Set() {
        super(Constants.egyptian, "God-Set-Card", 3);
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
                    // trade prod-tile with anothor player
                final int currentPos = PlayerHolder.getCurrentPlayerPosition();
                final int nextPos = (currentPos+1)%PlayerHolder.size();
                final int prevPos = (currentPos+PlayerHolder.size()-1)%PlayerHolder.size();
                if(PlayerHolder.getCurrentPlayer().isHuman()){
                    String[] options = new String[]{PlayerHolder.getPlayer(nextPos).getCultureName(),
                        PlayerHolder.getPlayer(prevPos).getCultureName()};
                    response = JOptionPane.showOptionDialog(null, "Who do you want to trade production with?",
                            "Choose Player", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                            null, options, null);
                    TradeProdTile tp = new TradeProdTile(PlayerHolder.getCurrentPlayerPosition()+response+1);
                    tp.dispose();
                }else{      // AI player
                    int playerpos = (new Random().nextInt(2))+1;
                    playerpos = (PlayerHolder.getCurrentPlayerPosition()+playerpos)%3   ;
                    Production_Tile[] t1 = PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().getTiles();
                    Production_Tile[] t2 = PlayerHolder.getPlayer(playerpos).getBoard().getProductionArea().getTiles();
                    int r1 = new Random().nextInt(16);
                    int r2 = new Random().nextInt(16);
                    if(t1[r1].getType()==t2[r2].getType()){
                        Production_Tile tile1 = t1[r1];
                        Production_Tile tile2 = t2[r2];
                        PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().destroy(tile1);
                        PlayerHolder.getPlayer(playerpos).getBoard().getProductionArea().destroy(tile2);
                        PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().add(tile2);
                        PlayerHolder.getPlayer(playerpos).getBoard().getProductionArea().add(tile1);
                    }
                }
            }
        }
        boolean advance = setCost(3);       // classic age cost = 3
        advance(advance);
        return false;
    }
    
}
