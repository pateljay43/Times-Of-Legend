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
public abstract class Build extends Card{
//    private final int num;       // # of buildings allowed to building
    
    public Build(int c, String name, int type){
        super(type, name, c);
    }

    @Override
    public abstract boolean play();

    /**
     *
     * @param attacker player who is playing this card
     * @param build false = destroy, true = build
     * @param num max number of buildings to build or destroy
     * @param reduce cost of any building build is reduced by 'reduce'
     */
    protected void building(int attacker, boolean build, int num, int reduce){
        if(PlayerHolder.getPlayer(attacker).isHuman()){
            if(!build){
                if(PlayerHolder.getPlayer(attacker).getBoard().getCityArea().getNumberOfBuildings()>0){
                    BuildSelector bs;
                    bs = new BuildSelector(build, num, reduce);
                    bs.dispose();
                }
            }else{     
                BuildSelector bs;
                bs = new BuildSelector(build, num, reduce);
                bs.dispose();
            }
        }else{
            processAI(build, num, reduce); 
        }
    }
    
    protected void processAI(boolean build, int num, int reduce) {
        while(num>0){
            if(build){
                if(!PlayerHolder.getCurrentPlayer().getBuildingsPool().isEmpty()){
                    int r = new Random().nextInt(PlayerHolder.getCurrentPlayer().getBuildingsPool().size());
                    Building_Tile b = PlayerHolder.getCurrentPlayer().getBuildingsPool().get(r);
                    if(reduce>0){
                        int[] cost = b.getCost();
                        int red = reduce;
                        for(int j=0;j<cost.length;j++){
                            if(cost[j]>=red){
                                PlayerHolder.getCurrentPlayer().getResource().add(j+1, red);
                                red=0;
                            }else if(cost[j]>0){
                                PlayerHolder.getCurrentPlayer().getResource().add(j+1, cost[j]);
                                red = red-cost[j];
                            }
                            if(red==0){
                                break;
                            }
                        }
                    }
                    if(PlayerHolder.getCurrentPlayer().getBoard().getCityArea().buyBuilding(b)){
                        PlayerHolder.getCurrentPlayer().drawBuildingfromPool(r);
                        if(reduce>0){
                            int red = reduce;
                            int[] cost = b.getCost();
                            for(int j=0;j<cost.length;j++){
                                if(cost[j]>=red){
                                    Bank.getResource().draw(j+1, red);
                                    red=0;
                                }else if(cost[j]>0){
                                    Bank.getResource().draw(j+1, cost[j]);
                                    red = red-cost[j];
                                }
                                if(red==0){
                                    break;
                                }
                            }
                        }
                    }else{
                        if(reduce>0){
                            int[] cost = b.getCost();
                            int red = reduce;
                            for(int j=0;j<cost.length;j++){
                                if(cost[j]>=red){
                                    PlayerHolder.getCurrentPlayer().getResource().draw(j+1, red);
                                    red=0;
                                }else if(cost[j]>0){
                                    PlayerHolder.getCurrentPlayer().getResource().draw(j+1, cost[j]);
                                    red = red-cost[j];
                                }
                                if(red==0){
                                    break;
                                }
                            }
                        }
                    }
                }    
            }else{      // destroy
                Building_Tile[] tiles = PlayerHolder.getCurrentPlayer().getBoard().getCityArea().getTiles();
                for(Building_Tile tile : tiles){
                    if(tile!=null){
                        PlayerHolder.getCurrentPlayer().addBuildingToPool(
                            PlayerHolder.getCurrentPlayer().getBoard().getCityArea().removeBuilding(tile.getType()));
                        break;
                    }
                }
            }
            num--;
        }
    }

}
