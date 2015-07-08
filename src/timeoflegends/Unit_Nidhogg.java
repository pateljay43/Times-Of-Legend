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
public class Unit_Nidhogg extends Unit{
    public Unit_Nidhogg() {
        super(Constants.archaicAge, 7, new int[]{Constants.myth, Constants.flyer},
                new int[]{0,1,0,4}, new boolean[]{false,false,false,false,true,false,false},
                Constants.norse, "Unit_Nidhogg");
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
