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
public class P_BuildCard extends Build{

    public P_BuildCard(int c) {
        super(c, "Permanent-Build", 1);
    }
    @Override
    public boolean play() {
               // max 1 building for , true = build building, 0 = no cost reduced
        building(PlayerHolder.getCurrentPlayerPosition(),true, 1, 0);
        return false;
    }
}
