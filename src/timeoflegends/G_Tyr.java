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
public class G_Tyr extends Attack{
    
    private boolean godPower;
    private boolean unitBerzerking = false;
    
    public G_Tyr(){
        super(Constants.norse, 3, "God-Tyr-Card", 6, 6);
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
        if(attacker.getResource().getNumOfFavor()>2){
            int response;
            if(attacker.isHuman()){
                response = JOptionPane.showConfirmDialog(null, "Do you want to activate the Tyr god power for 3 favor?", "God Power", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            }else{
                Random r = new Random();
                response = r.nextInt(2);
            }
            if(response == JOptionPane.YES_OPTION){
                Bank.getResource().addFavor(attacker.getResource().removeFavor(3));
                godPower = true;
            }else{
                godPower = false;
            }
        }
    }
    
    protected void battle(){
        while(!attackLost && !defenseLost){
            if(offerSurrender())
                break;
            pickUnitsForBattle();
            if(attacker.isHuman()){
                JOptionPane.showMessageDialog(null, defendUnit.getImage(), "Defending Unit", JOptionPane.PLAIN_MESSAGE);
            }else if(target.isHuman()){
                JOptionPane.showMessageDialog(null, attackUnit.getImage(), "Attacking Unit", JOptionPane.PLAIN_MESSAGE);
            }
            offerWadjetSwitch();
            
            if(godPower){
                offerBerzerk();
            }else{
                attackUnit.preFight(attacker, defendUnit);//berzerk and throw
            }
            defendUnit.preFight(target, attackUnit);
            boolean[] defBuildings = new boolean[]{target.getBoard().getCityArea().find(Constants.wall)!=-1, target.getBoard().getCityArea().find(Constants.tower)!=-1};
            boolean ignore = (attacker.getBoard().getCityArea().find(Constants.siegeEngineWorkshop)!=-1||attackUnit.getAbility(Constants.ignoresWallsAndTowers));
            int a6 = 0;
            int d6 = 0;
            while(a6==d6){//keeps rolling until there isn't a tie
                a6 = attackUnit.attack(defendUnit, true, targetBoard, defBuildings, ignore);
                if(unitBerzerking){
                    a6+=berzerkRoll();
                }
                d6 = defendUnit.attack(attackUnit, false, targetBoard, defBuildings, ignore);
                String result = "It was a tie. Rerolling.";
                if(a6==d6){//only if there is a tie and there is an ability that influences the outcome
                    if(attackUnit.getAbility(Constants.winsDraws)){
                        a6++;
                        result = "It was a tie, but "+attacker.getCultureName()+"'s unit wins draws.";
                    }else if(attackUnit.getAbility(Constants.loosesDraws)||unitBerzerking){
                        a6--;
                        result = "It was a tie, but "+attacker.getCultureName()+"'s unit losses draws.";
                    }else if(defendUnit.getAbility(Constants.winsDraws)){
                        d6++;
                        result = "It was a tie, but "+target.getCultureName()+"'s unit wins draws.";
                    }else if(defendUnit.getAbility(Constants.loosesDraws)){
                        d6--;
                        result = "It was a tie, but "+attacker.getCultureName()+"'s unit losses draws.";
                    }
                }else{
                    result = attacker.getCultureName()+"'s unit rolled "+a6+" sixes.\n"
                            +target.getCultureName()+"'s unit rolled "+d6+" sixes.";
                }
                if(attacker.isHuman()||target.isHuman()){
                    JOptionPane.showMessageDialog(null, result, "Fight Results", JOptionPane.PLAIN_MESSAGE);
                }

            }
            postFight(a6, d6);
            attackLost = attackers.isEmpty();
            defenseLost = defenders.isEmpty();
        }//end while (one side lost or surrendered)
    }
    
    private void offerBerzerk(){
        int choice;
        if(attacker.isHuman()){
            choice = JOptionPane.showConfirmDialog(null, "Do you want to go berzerk?", "Berzerk", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        }else{
            Random rand = new Random();
            choice = rand.nextInt(2);
        }
        
        unitBerzerking = choice == 0;
    }
    
    private int berzerkRoll(){
        Random rand = new Random();
        int sixes = 0;
        for(int i = 0; i < 2; i++){
            if(rand.nextInt(6)+1==6)
                sixes++;
        }
        return sixes;
    }
}
