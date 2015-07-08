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
public class Unit_Priest extends Unit{

    public Unit_Priest() {
        super(Constants.classicalAge, 4, new int[]{Constants.hero}, new int[]{2,0,0,4},
                new boolean[]{false,false,false,false,false,false,false},
                Constants.egyptian, "Unit_Priest");
    }

    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.myth)
                bonusDice+=5;
        }
        return bonusDice;
    }
}