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
public class P_GatherCard extends Gather{
    public P_GatherCard(int c) {
        super(c, "Permanent-Gather", 1);        // 1 for indicating card type(perm)
    }
    @Override
    public boolean play(){
        placeVillagers();
        select();
        System.out.println("Gather choice: "+ choice);
        evaluateBoard();
        gather(true, false);        // gather for all = true, gather from all tiles = false;
        return false;
    }
}
