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
public class Unit_Dwarf extends Unit{
    public Unit_Dwarf() {
        super(Constants.archaicAge, 4, new int[]{Constants.myth, Constants.giantKiller},
                new int[]{2,0,0,2}, new boolean[]{false,false,false,false,false,true,false},
                Constants.norse, "Unit_Dwarf");
    }

    @Override
    protected int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.giant)
                bonusDice+=7;
        }
        return bonusDice;
    }
}
