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
public class G_Isis extends Attack{

    private boolean godPower;
    
    public G_Isis() {
        super(Constants.egyptian, 3, "God-Isis-Card", 7, 7);
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
        return false;
    }
    
    private void activateGodPower(){
        if(attacker.getResource().getNumOfFavor()>0){
            int response;
            if(attacker.isHuman()){
                response = JOptionPane.showConfirmDialog(null, "Do you want to activate the Isis god power for 1 favor?", "God Power", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
    
    @Override
    protected void postFight(int a6, int d6){
        if(a6>d6){
            if(attackUnit.getAbility(Constants.throwUnit)){//attacker won and throws unit
                attackUnit.onVictory(attacker, attackers);
                attackers.add(attackUnit);
                target.getArmy().addUnit(defendUnit);
            }else if(attackUnit.getAbility(Constants.forcedRetreat)&&defendUnit.isType(Constants.hero)){//attacker won, forces a retreat, and fought a hero
                attackUnit.onVictory(attacker, attackers);
                attackers.add(attackUnit);
                if(defendUnit.onDefeat(target, defenders))
                    target.getDeck().addUnit(defendUnit);
                while(!defenders.isEmpty()){
                    target.getArmy().addUnit(defenders.remove(0));
                }
            }else{//attacker won no special ability
                attackUnit.onVictory(attacker, attackers);
                attackers.add(attackUnit);
                if(defendUnit.onDefeat(target, defenders))
                    target.getDeck().addUnit(defendUnit);
            }
        }else if(d6>a6){//same as above but defender won
            if(defendUnit.getAbility(Constants.throwUnit)){
                defendUnit.onVictory(target, defenders);
                defenders.add(defendUnit);
                attacker.getArmy().addUnit(attackUnit);
            }else if(defendUnit.getAbility(Constants.forcedRetreat)&&attackUnit.isType(Constants.hero)){
                defendUnit.onVictory(target, defenders);
                defenders.add(defendUnit);
                if(godPower){
                    attackers.add(attackUnit);
                    godPower = false;
                }else if(attackUnit.onDefeat(target, attackers)){
                    attacker.getDeck().addUnit(attackUnit);
                }
                while(!attackers.isEmpty()){
                    attacker.getArmy().addUnit(attackers.remove(0));
                }
            }else{
                defendUnit.onVictory(target, defenders);
                defenders.add(defendUnit);
                if(godPower){
                    attackers.add(attackUnit);
                    godPower = false;
                }else if(attackUnit.onDefeat(attacker, attackers)){
                    attacker.getDeck().addUnit(attackUnit);
                }
            }
        }
    }
}
