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
public class Unit_Elephant extends Unit{

    public Unit_Elephant() {
        super(Constants.archaicAge, 3, new int[]{Constants.mortal, Constants.giant},
                new int[]{2,0,0,1}, new boolean[]{false,false,false,false,false,true,false},
                Constants.egyptian, "Unit_Elephant");
    }
    
    @Override
    protected int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.mortal)
                bonusDice+=2;
        }
        return bonusDice;
    }
}
