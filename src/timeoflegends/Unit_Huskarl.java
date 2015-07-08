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
 * @author Eric
 */
public class Unit_Huskarl extends Unit{
    public Unit_Huskarl() {
        super(Constants.archaicAge, 3, new int[]{Constants.mortal, Constants.warrior},
                new int[]{1,0,0,2}, new boolean[]{false,false,false,false,false,false,false},
                Constants.norse, "Unit_Huskarl");
    }

    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.cavalry)
                bonusDice+=4;
        }
        if(getAbility(Constants.loosesDraws))
            bonusDice+=2;
        return bonusDice;
    }
    
    @Override
    public void preFight(Player p, Unit enemy){
        setAbility(Constants.loosesDraws, false);
        int choice;
        if(p.isHuman()){
            choice = JOptionPane.showConfirmDialog(null, "Do you want to go berzerk?", "Berzerk", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        }
        else{
            Random r = new Random();
            choice = r.nextInt(2);
        }
        if(choice==JOptionPane.YES_OPTION){
            setAbility(Constants.loosesDraws, true);
        }
    }
}
