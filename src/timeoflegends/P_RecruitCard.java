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
public class P_RecruitCard extends Recruit{

    public P_RecruitCard(int c) {
        super(c, "Permanent-Recruit", 1);
    }
    
    @Override
    public boolean play() {
        if(PlayerHolder.getCurrentPlayer().isHuman()){
            humanRecruit(2,-1,-1,-1);        // max 2 units allowed for permenant card, no extra actions = -1
        }else{      // process two units
            processAI(2,-1,-1,-1);
        }
        
        // just prints name of recruited units
        ArrayList<Unit> units = PlayerHolder.getCurrentPlayer().getArmy().getUnits();
        for (Unit unit : units) {
            System.out.println(unit.getName());
        }
        return false;
    }
}
