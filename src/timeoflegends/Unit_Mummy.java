/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author JAY
 */
public class Unit_Mummy extends Unit{

    public Unit_Mummy() {
        super(Constants.archaicAge, 5, new int[]{Constants.myth}, new int[]{0,2,0,3},
                new boolean[]{false,false,false,false,false,false,false},
                Constants.egyptian, "Unit_Mummy");
    }

    @Override
    public int getBonusDice(Unit u) {
        return 0;
    }
    
    @Override
    public boolean onVictory(Player p, ArrayList<Unit> temp){
        int position = p.getDeck().getUnitPosition(this);
        if(position!=-1){
            p.getArmy().addUnit(p.getDeck().drawUnitAt(position));
        }
        return true;
    }
}