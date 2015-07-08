/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

/**
 *
 * @author JAY
 */
public class Unit_Anubite extends Unit{

    public Unit_Anubite() {
        super(Constants.archaicAge, 5, new int[]{Constants.myth, Constants.cavalry},
                new int[]{0,1,0,3}, new boolean[]{false,false,false,false,false,false,false},
                Constants.egyptian, "Unit_Anubite");
    }
    
    @Override
    public int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.archer)
                bonusDice+=4;
        }
        return bonusDice;
    }
    
}
