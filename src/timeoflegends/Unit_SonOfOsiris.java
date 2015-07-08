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
public class Unit_SonOfOsiris extends Unit{

    public Unit_SonOfOsiris() {
        super(Constants.mythicAge, 8, new int[]{Constants.hero}, new int[]{0,4,0,4},
                new boolean[]{false,false,false,false,false,false,false},
                Constants.egyptian, "Unit_SonOfOsiris");
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
    public boolean onDefeat(Player p, ArrayList<Unit> u){
        boolean priest = false;
        for(Unit i : u){
            if(i.getName().equals("Unit_Priest")){
                priest = true;
            }
        }
        if(priest&&p.getResource().getNumOfFavor()>=4){
            int choice = JOptionPane.showConfirmDialog(null, "Do you revive the Son of Osiris for 4 favor?", "Revive", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(choice == JOptionPane.YES_OPTION){
                Bank.getInstance().getResource().addFavor(p.getResource().removeFavor(4));
                p.getArmy().addUnit(this);
                return false;
            }
        }
        return true;
    }
}
