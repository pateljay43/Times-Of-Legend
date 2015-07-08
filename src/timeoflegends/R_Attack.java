/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

/**
 *
 * @author Eric
 */
public class R_Attack extends Attack{
    
    public R_Attack(int c, int a){
        super(c, a, "Random-Attack"+a+"-Card", a, a);
    }
    
    public boolean play(){
        init();
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
}
