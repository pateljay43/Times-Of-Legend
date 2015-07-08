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
public class G_Ares extends Attack{
    
    public G_Ares(){
        super(Constants.greek, 3, "God-Ares-Card", 6, 6);
    }
    
    @Override
    public boolean play(){
        init();
        activateGodPower();
        getTargetPlayer();
        getTargetBoard();
        attackerChoosesSquad();
        defenderChoosesSquad();
        alertPlayer();
        newBattle();
        battle();
        afterBattle();
        returnUnits();
        awardVictoryCubes();
        return false;
    }
    
    private void activateGodPower(){
        if(attacker.getResource().getNumOfFavor()>2){
            int response;
            if(attacker.isHuman()){
                response = JOptionPane.showConfirmDialog(null, "Do you want to activate the Ares god power for 3 favor?", "God Power", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }else{
                Random r = new Random();
                response = r.nextInt(2);
            }
            if(response == JOptionPane.YES_OPTION){
                Bank.getResource().addFavor(attacker.getResource().removeFavor(3));
                aAllowedUnits = 8;
            }
        }
    }
}
