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
public class Unit_Troll extends Unit{
    public Unit_Troll() {
        super(Constants.archaicAge, 6, new int[]{Constants.myth, Constants.warrior},
                new int[]{3,0,2,0}, new boolean[]{false,false,false,false,false,false,false},
                Constants.norse, "Unit_Troll");
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
