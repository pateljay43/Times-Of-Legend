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
public class P_NextAgeCard extends NextAge{

    public P_NextAgeCard(int c) {
        super(c, "Permanent-NextAge", 1);
    }

    @Override
    public boolean play() {
        boolean advance = setCost(4);       // classic age cost = 4
        advance(advance);
        return false;
    }
}
