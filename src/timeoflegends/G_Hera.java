/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author JAY
 */
public class G_Hera extends Build{

    public G_Hera() {
        super(Constants.greek, "God-Hera-Card", 3);
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
                if(PlayerHolder.getCurrentPlayer().getHouseinpool()>0
                        && PlayerHolder.getCurrentPlayer().getBoard().
                                getCityArea().getNumberOfBuildings()<16){
                    // gain one house   
                    ArrayList<Building_Tile> buildingsPool = PlayerHolder.getCurrentPlayer().getBuildingsPool();
                    for(int i=0;i<buildingsPool.size();i++){
                        if(buildingsPool.get(i).getType()==Constants.house){
                            PlayerHolder.getCurrentPlayer().getBoard().getCityArea().addBuilding(
                                PlayerHolder.getCurrentPlayer().drawBuildingfromPool(i));
                            break;
                        }
                    }
                }
            }
        }
        building(PlayerHolder.getCurrentPlayerPosition(),true, 3, 0);
        return false;
    }
    
}
