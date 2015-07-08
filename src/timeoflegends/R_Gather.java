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
public class R_Gather extends Gather{
    
    public R_Gather(int c) {
        super(c, "Random-Gather-Card", 2);        // 1 for indicating card type(perm)
    }
    @Override
    public boolean play(){
        placeVillagers();
        evaluateBoard();
        gather(true, true);        // gather for all = true, gather from all tiles = false;
        return false;
    }
}
