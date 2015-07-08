/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.Random;

/**
 *
 * @author JAY
 */
public abstract class Gather extends Card{
    protected int choice;
    public Gather(int c, String _name, int type){
        super(type, _name, c);
    }

    @Override
    public abstract boolean play();
    
    protected void placeVillagers() {
        if(PlayerHolder.getCurrentPlayer().isHuman()){
            PlayerHolder.getCurrentPlayer().setChangeVillager(true);
            PlaceVillagers pv = new PlaceVillagers();
            pv.dispose();
            PlayerHolder.getCurrentPlayer().setChangeVillager(false);
        }
    }
    protected void evaluateBoard() {
        if(PlayerHolder.getCurrentPlayer().getBoard().getCityArea().find(Constants.granary)!=-1){
            PlayerHolder.getCurrentPlayer().getResource().
                    add(Constants.food, Bank.getResource().removeFood(2));
        }
        if(PlayerHolder.getCurrentPlayer().getBoard().getCityArea().find(Constants.monument)!=-1){
            PlayerHolder.getCurrentPlayer().getResource().
                    add(Constants.favor, Bank.getResource().removeFavor(2));
        }
        if(PlayerHolder.getCurrentPlayer().getBoard().getCityArea().find(Constants.woodWorkshop)!=-1){
            PlayerHolder.getCurrentPlayer().getResource().
                    add(Constants.wood, Bank.getResource().removeWood(2));
        }
        if(PlayerHolder.getCurrentPlayer().getBoard().getCityArea().find(Constants.goldMint)!=-1){
            PlayerHolder.getCurrentPlayer().getResource().
                    add(Constants.gold, Bank.getResource().removeGold(2));
        }
    }

    /**
     *
     * @param gatherAll gather for all players?
     * @param allTiles gather from all tiles?
     */
    protected void gather(boolean gatherAll, boolean allTiles) {
        for(int i = 0; i < PlayerHolder.size(); i++){
            Production_Tile[] tiles = PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().getTiles();
            boolean[] villager = PlayerHolder.getCurrentPlayer().getBoard().getVillagerOnProductionArea();
            for(int j=0;j<tiles.length;j++){
                if(tiles[j]!=null && (choice==tiles[j].getResource()||choice==tiles[j].getType()||allTiles)){
                    PlayerHolder.getCurrentPlayer().getResource().add(tiles[j].getResource(), 
                        Bank.getResource().draw(tiles[j].getResource(), 
                                (villager[j]?1:0)+tiles[j].getNumber()));   
                            // add atmost res available from bank
                }
            }
            if(!gatherAll){
                return;
            }
            PlayerHolder.nextPlayer();
        }
    }
    protected void select() {
        if(PlayerHolder.getCurrentPlayer().isHuman()){
            GatherSelector gs = new GatherSelector();
            choice = gs.getChoice();
            gs.dispose();
        }else{
            Random r = new Random();
            choice = r.nextInt(10)+1;
        }
        if(choice==-1){
            select();       // if gather frame is closed without selecting any choice -> reopen dialog
        }
    }
    /**
     * @param b only current player gathers?
     * @param res res to gather (food, favor, wood, gold)
     * @param num # of res to gather per res-tile 
     */
    void gatherExtra(boolean b, int res, int num) {
        choice = res;
        for(int i = 0; i < PlayerHolder.size(); i++){
            Production_Tile[] tiles = PlayerHolder.getCurrentPlayer().getBoard().getProductionArea().getTiles();
            for (Production_Tile tile : tiles) {
                if (tile != null && choice == tile.getResource()) {
                    PlayerHolder.getCurrentPlayer().getResource().add(choice,
                            Bank.getResource().draw(choice,2));
                    // add atmost num of res available from bank
                }
            }
            if(b){
                return;
            }
            PlayerHolder.nextPlayer();
        }
    }
}

