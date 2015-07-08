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
public class Unit_Centaur extends Unit{

    public Unit_Centaur() {
        super(Constants.archaicAge, 5, new int[]{Constants.myth, Constants.archer, Constants.cavalry},
                new int[]{0,1,3,0}, new boolean[]{false,false,false,false,false,false,false},
                Constants.greek, "Unit_Centaur");
    }

    @Override
    protected int getBonusDice(Unit u) {
        int bonusDice = 0;
        for(int i = 0; i < u.getNumOfTypes(); i++){
            if(u.getType(i)==Constants.archer||u.getType(i)==Constants.flyer)
                bonusDice+=3;
        }
        return bonusDice;
    }
    
}
