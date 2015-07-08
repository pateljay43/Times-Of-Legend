/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.Random;

/**
 *
 * @author JAY
 */
public class Unit_Scorpion_Man extends Unit{

    public Unit_Scorpion_Man() {
        super(Constants.archaicAge, 5, new int[]{Constants.myth, Constants.giant},
                new int[]{4,0,0,2}, new boolean[]{false,false,false,false,false,false,false},
                Constants.egyptian, "Unit_Scorpion_Man");
    }

    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.mortal)
                bonusDice+=4;
        }
        return bonusDice;
    }
}
