/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.Random;

/**
 *
 * @author Eric
 */
public class Unit_Frost_Giant extends Unit{
    public Unit_Frost_Giant() {
        super(Constants.archaicAge, 7, new int[]{Constants.myth, Constants.giant},
                new int[]{4,2,0,0}, new boolean[]{false,false,false,false,false,false,false},
                Constants.norse, "Unit_Frost_Giant");
    }

    @Override
    protected int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.warrior)
                bonusDice+=2;
            if(u.getType(i)==Constants.mortal)
                bonusDice+=3;
        }
        return bonusDice;
    }
}
