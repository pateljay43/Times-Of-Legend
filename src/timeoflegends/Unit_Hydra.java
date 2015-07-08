/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author JAY
 */
public class Unit_Hydra extends Unit{

    private int battles;
    
    public Unit_Hydra() {
        super(Constants.archaicAge, 6, new int[]{Constants.myth, Constants.giant},
                new int[]{0,2,0,2}, new boolean[]{false,false,false,false,false,false,false},
                Constants.greek, "Unit_Hydra");
        battles = 0;
    }

    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = battles;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.warrior)
                bonusDice+=4;
        }
        return bonusDice;
    }
    
    @Override
    public void newBattle(){
        battles = 0;
    }
    
    @Override
    public boolean onVictory(Player p, ArrayList<Unit> temp){
        battles++;
        return true;
    }
}
