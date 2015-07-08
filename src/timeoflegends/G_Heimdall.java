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
public class G_Heimdall extends Attack{
    
    private boolean godPower;
    
    public G_Heimdall(){
        super(Constants.norse, 3, "God-Heimdall-Card", 4, 4);
    }
    
    @Override
    public boolean play(){
        init();
        activateGodPower();
        getTargetPlayer();
        if(godPower){
            aAllowedUnits = attacker.getArmy().getSize();
            dAllowedUnits = target.getArmy().getSize();
        }
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
        if(attacker.getResource().getNumOfFavor()>3){
            int response;
            if(attacker.isHuman()){
                response = JOptionPane.showConfirmDialog(null, "Do you want to activate the Heimdall god power for 4 favor?", "God Power", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }else{
                Random r = new Random();
                response = r.nextInt(2);
            }
            if(response == JOptionPane.YES_OPTION){
                Bank.getResource().addFavor(attacker.getResource().removeFavor(4));
                godPower = true;
            }
        }
    }
}
