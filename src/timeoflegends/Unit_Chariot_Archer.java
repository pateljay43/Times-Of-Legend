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
public class Unit_Chariot_Archer extends Unit{

    public Unit_Chariot_Archer() {
        super(Constants.archaicAge, 3, new int[]{Constants.mortal, Constants.archer, Constants.cavalry},
                new int[]{0,0,1,1}, new boolean[]{false,false,false,false,false,false,false},
                Constants.egyptian, "Unit_Chariot_Archer");
    }

    @Override
    protected int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.flyer||u.getType(i)==Constants.warrior)
                bonusDice+=3;
        }
        return bonusDice;
    }
}
