/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;

/**
 *
 * @author JAY
 */
public class R_Recruit extends Recruit{
    private final int max;
    public R_Recruit(int c, int _max) {
        super(c, (_max==3?"Random-Recruit3-Card":
                    (_max==4?"Random-Recruit4-Card":
                        "Random-Recruit5-Card")), 2);
        max = _max;
    }
    
    @Override
    public boolean play() {
        if(PlayerHolder.getCurrentPlayer().isHuman()){
            humanRecruit(max,-1,-1,-1);        // max 2 units allowed for permenant card, no extra actions = -1
        }else{      // process two units
            processAI(max,-1,-1,-1);
        }
        
        // just prints name of recruited units
        ArrayList<Unit> units = PlayerHolder.getCurrentPlayer().getArmy().getUnits();
        for (Unit unit : units) {
            System.out.println(unit.getName());
        }
        return false;
    }
}
