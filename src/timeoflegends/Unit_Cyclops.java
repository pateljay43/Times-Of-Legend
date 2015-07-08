/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author JAY
 */
public class Unit_Cyclops extends Unit{

    public Unit_Cyclops() {
        super(Constants.archaicAge, 6, new int[]{Constants.giant, Constants.myth},
                new int[]{3,3,0,0}, new boolean[]{false,false,false,false,false,true,false},
                Constants.greek, "Unit_Cyclops");
    }

    @Override
    protected int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.mortal)
                bonusDice+=4;
        }
        return bonusDice;
    }
    
    @Override
    public void preFight(Player p, Unit enemy){
        setAbility(Constants.throwUnit, false);
        boolean giant = false;
        for(int i = 0; i < enemy.getNumOfTypes(); i++){
            if(enemy.getType(i)==Constants.giant)
                giant = true;
        }
        if(!giant){
            int choice = JOptionPane.showConfirmDialog(null, "Do you want to throw the enemy?", "Throw", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(choice==JOptionPane.YES_OPTION){
                setAbility(Constants.throwUnit, true);
            }
        }
    }
}
