/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eric
 */
public class Unit_MythicNorseHero extends Unit{
    public Unit_MythicNorseHero() {
        super(Constants.mythicAge, 8, new int[]{Constants.hero}, new int[]{4,4,0,0},
                new boolean[]{false,false,false,false,false,false,false},
                Constants.norse, "Unit_MythicNorseHero");
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
    
    @Override
    public boolean onVictory(Player p, ArrayList<Unit> squad){
        if(p.getArmy().getSize()!=0){
            SelectUnit su = new SelectUnit(p.getArmy().getUnits(), 1, p.isHuman(), true);
            while(!su.isSelected()){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Unit addUnit = su.getChoice().remove(0);
            squad.add(addUnit);
        }
        return true;
    }
}
