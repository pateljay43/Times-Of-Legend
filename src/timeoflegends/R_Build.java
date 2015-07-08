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
public class R_Build extends Build{
    private final int builds;
    public R_Build(int c, int _builds) {
        super(c, (_builds==2?"Random-Build2-Card":
                    (_builds==3?"Random-Build3-Card":
                        "Random-Build4-Card")), 2);
        builds = _builds;
    }
    @Override
    public boolean play() {
               // max 1 building for , true = build building, 0 = no cost reduced
        building(PlayerHolder.getCurrentPlayerPosition(),true, builds, 0);
        return false;
    }
}
