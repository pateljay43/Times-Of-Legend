/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Eric
 */
public class G_Aphrodite extends Attack{
    
    private boolean godPower;
    
    public G_Aphrodite(){
        super(Constants.greek, 3, "God-Aphrodite-Card", 6, 6);
    }
    
    @Override
    public boolean play(){
        init();
        activateGodPower();
        getTargetPlayer();
        getTargetBoard();
        attackerChoosesSquad();
        defenderChoosesSquad();
        if(godPower){
            returnDefendingUnit();
        }
        alertPlayer();
        newBattle();
        battle();
        afterBattle();
        returnUnits();
        awardVictoryCubes();
        return false;
    }
    
    private void activateGodPower(){
        if(attacker.getResource().getNumOfFavor()>1){
            int response;
            if(attacker.isHuman()){
                response = JOptionPane.showConfirmDialog(null, "Do you want to activate the Aphrodite god power for 2 favor?", "God Power", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }else{
                Random r = new Random();
                response = r.nextInt(2);
            }
            if(response == JOptionPane.YES_OPTION){
                Bank.getResource().addFavor(attacker.getResource().removeFavor(2));
                godPower = true;
            }else{
                godPower = false;
            }
        }
    }
    
    private void returnDefendingUnit(){
        if(!defenders.isEmpty()){
            SelectUnit su = new SelectUnit(defenders, 1, attacker.isHuman(), true);
            while(!su.isSelected()){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Unit retUnit = su.getChoice().remove(0);
            su.dispose();
            target.getArmy().addUnit(retUnit);
        }
    }
}
