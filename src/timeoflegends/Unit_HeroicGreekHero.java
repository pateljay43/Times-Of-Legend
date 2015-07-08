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
public class Unit_HeroicGreekHero extends Unit{

    public Unit_HeroicGreekHero() {
        super(Constants.heroicAge, 6, new int[]{Constants.hero}, new int[]{3,0,0,4},
                new boolean[]{false,false,true,false,false,false,false},
                Constants.greek, "Unit_HeroicGreekHero");
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
