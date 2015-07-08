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
public class G_Thoth extends Attack{
    
    private boolean godPower;
    
    public G_Thoth(){
        super(Constants.egyptian, 3, "God-Thoth-Card", 6, 6);
    }
    
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
        return godPower;
    }
    
    private void activateGodPower(){
        if(attacker.getResource().getNumOfFavor()>0){
            int response;
            if(attacker.isHuman()){
                response = JOptionPane.showConfirmDialog(null, "Do you want to activate the Thoth god power for 1 favor?", "God Power", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }else{
                Random r = new Random();
                response = r.nextInt(2);
            }
            if(response == JOptionPane.YES_OPTION){
                Bank.getResource().addFavor(attacker.getResource().removeFavor(1));
                godPower = true;
            }else{
                godPower = false;
            }
        }
    }
}
