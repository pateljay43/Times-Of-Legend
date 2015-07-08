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
public class R_Explore extends Explore{
    private final int extra;
    public R_Explore(int c, int _extra) {
        super(c, (_extra==0?"Random-Explore-Card":
                    (_extra==1?"Random-Explore1-Card":
                        "Random-Explore2-Card")), 2);      // 1 for permenant card
        extra = _extra;
    }

    @Override
    public boolean play() {
        getTiles(PlayerHolder.size()+extra);
        for(int i=0;i<PlayerHolder.size();i++){
            if(PlayerHolder.getCurrentPlayer().isHuman()){
                selectTile();
            }else{
                processAI();
            }
            PlayerHolder.nextPlayer();
        }
        Production_Tile_Pool.putBackTiles(pool);
        return false;
    }
}
