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
public class P_ExploreCard extends Explore{

    public P_ExploreCard(int c) {
        super(c, "Permanent-Explore", 1);      // 1 for permenant card
    }

    @Override
    public boolean play() {
        getTiles(PlayerHolder.size()+1);
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
