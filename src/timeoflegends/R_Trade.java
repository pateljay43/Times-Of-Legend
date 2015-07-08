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
public class R_Trade extends Trade{
    
    public R_Trade(int c) {
        super(c, "Random-Trade-Card", 2);
    }

    @Override
    public boolean play() {
        payCost(1);        // any one cubes charge to play permenant trade
        if(PlayerHolder.getCurrentPlayer().isHuman()){
            humanTrade();
        }else{
            processAI();
        }
        trade(transaction);
        checkTemple();
        return false;
    }
}
