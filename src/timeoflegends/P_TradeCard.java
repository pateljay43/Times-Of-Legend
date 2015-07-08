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
public class P_TradeCard extends Trade{

    public P_TradeCard(int c) {
        super(c, "Permanent-Trade", 1);
    }

    @Override
    public boolean play() {
        payCost(2);        // any two cubes charge to play permenant trade
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
