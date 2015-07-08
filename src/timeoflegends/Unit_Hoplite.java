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
public class Unit_Hoplite extends Unit{

    public Unit_Hoplite() {
        super(Constants.archaicAge, 3, new int[]{Constants.mortal, Constants.warrior},
                new int[]{1,0,1,0}, new boolean[]{false,false,false,false,false,false,false},
                Constants.greek, "Unit_Hoplite");
    }

    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.cavalry)
                bonusDice+=3;
            if(u.getType(i)==Constants.mortal)
                bonusDice+=1;
        }
        return bonusDice;
    }
}
