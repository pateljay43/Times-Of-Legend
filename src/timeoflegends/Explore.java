/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;

/**
 *
 * @author JAY
 */
public abstract class Explore extends Card{
    protected ArrayList<Production_Tile> pool;
    public Explore(int c, String name, int type) {
        super(type, name, c);
    }
    @Override
    public abstract boolean play();
    
    protected void getTiles(int num){
        pool = Production_Tile_Pool.getTop_N_Tiles(num);
    }
    protected void selectTile(){
        TileSelector t = new TileSelector(pool);     // current player selects 1 tile
        try{    while(t.isfinish()){    Thread.sleep(1);    }   }
        catch(Exception ex){ex.printStackTrace();}
        pool = t.getPool();
        t.dispose();
    }
    protected void processAI(){
        for(int j=0;j<pool.size();j++){
            Production_Tile t = pool.get(j);
            if(check(t)){
                pool.remove(t);
                break;
            }
        }
    }
    protected boolean check(Production_Tile tile){        // tries to add tile to current player
        System.out.println(tile.getTypeName()+" clicked by AI");
        for(int i=0;i<16;i++){
            if(PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().add(tile)){
                return true;
            }
        }
        return false;
    }
}
