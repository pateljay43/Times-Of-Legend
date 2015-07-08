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
public class Unit_Medusa extends Unit{

    public Unit_Medusa() {
        super(Constants.archaicAge, 5, new int[]{Constants.myth, Constants.giantKiller},
                new int[]{1,3,0,0}, new boolean[]{true,false,false,false,false,false,false},
                Constants.greek, "Unit_Medusa");
    }

    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.giant)
                bonusDice+=6;
        }
        return bonusDice;
    }
}
