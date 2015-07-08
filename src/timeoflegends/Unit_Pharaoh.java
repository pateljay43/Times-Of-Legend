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
public class Unit_Pharaoh extends Unit{

    public Unit_Pharaoh() {
        super(Constants.heroicAge, 6, new int[]{Constants.hero}, new int[]{3,0,0,3},
                new boolean[]{false,false,false,false,false,false,true},
                Constants.egyptian, "Unit_Pharaoh");
    }

    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.myth)
                bonusDice+=4;
        }
        return bonusDice;
    }
}
