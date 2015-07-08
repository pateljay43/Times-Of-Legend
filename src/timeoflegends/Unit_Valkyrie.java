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
 * @author Eric
 */
public class Unit_Valkyrie extends Unit{
    public Unit_Valkyrie() {
        super(Constants.archaicAge, 5, new int[]{Constants.myth, Constants.cavalry},
                new int[]{1,0,0,1}, new boolean[]{false,false,false,false,false,false,false},
                Constants.norse, "Unit_Valkyrie");
    }

    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.archer)
                bonusDice+=4;
        }
        return bonusDice;
    }
    
    @Override
    public boolean onVictory(Player p, ArrayList<Unit> temp){
        p.getResource().addFavor(Bank.getInstance().getResource().removeFavor(2));
        return true;
    }
}
