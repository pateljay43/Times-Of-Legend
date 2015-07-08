/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author JAY
 */
public class Unit_Phoenix extends Unit{

    public Unit_Phoenix() {
        super(Constants.archaicAge, 6, new int[]{Constants.myth,Constants.flyer},
                new int[]{0,3,2,0}, new boolean[]{false,false,false,false,false,false,false},
                Constants.egyptian, "Unit_Phoenix");
    }

    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.giantKiller)
                bonusDice+=4;
        }
        return bonusDice;
    }
    
    @Override
    public boolean onDefeat(Player p, ArrayList<Unit> u){
        boolean priest = false;
        for(Unit i : u){
            if(i.getName().equals("Unit_Priest")){
                priest = true;
            }
        }
        if(priest&&p.getResource().getNumOfFavor()>=2){
            int choice = JOptionPane.showConfirmDialog(null, "Do you revive your Pheonix for 2 favor?", "Revive", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(choice == JOptionPane.YES_OPTION){
                Bank.getResource().addFavor(p.getResource().removeFavor(2));
                p.getArmy().addUnit(this);
                return false;
            }
        }
        return true;
    }
}
