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
public class Unit_Manticore extends Unit{

    public Unit_Manticore() {
        super(Constants.archaicAge, 5, new int[]{Constants.myth, Constants.flyer},
                new int[]{2,2,0,0}, new boolean[]{false,false,false,false,false,false,false},
                Constants.greek, "Unit_Manticore");
    }

    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.giantKiller)
                bonusDice+=4;
        }
        return bonusDice;
    }
}
