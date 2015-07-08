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
public class R_NextAge extends NextAge{
    
    public R_NextAge(int c) {
        super(c, "Random-NextAge-Card", 2);
    }

    @Override
    public boolean play() {
        boolean advance = setCost(3);       // classic age cost = 3
        advance(advance);
        return false;
    }
}
