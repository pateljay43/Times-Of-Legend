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
public class Unit_MythicalGreekHero extends Unit{

    public Unit_MythicalGreekHero() {
        super(Constants.mythicAge, 5, new int[]{Constants.hero}, new int[]{0,4,0,4},
                new boolean[]{false,false,false,false,false,false,false},
                Constants.greek, "Unit_MythicalGreekHero");
    }
    
    @Override
    public int getBonusDice(Unit u) {
        return u.getBaseDice();
    }
}
