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
public class Unit_Minotaur extends Unit{

    public Unit_Minotaur() {
        super(Constants.archaicAge, 5, new int[]{Constants.myth, Constants.warrior},
                new int[]{2,0,2,0}, new boolean[]{false,false,false,false,true,false,false},
                Constants.greek, "Unit_Minotaur");
    }

    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.cavalry)
                bonusDice+=4;
        }
        return bonusDice;
    }
}
