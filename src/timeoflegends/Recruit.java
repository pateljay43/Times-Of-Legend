/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author JAY
 */
public abstract class Recruit extends Card{
    public Recruit(int c, String name, int type){
        super(type, name, c);
    }
    /**
     * 
     * @param num max units allowed to be recruited
     * @param unitType type of unit to apply constrain on (-1 if no constrain)
     * @param resType type of res to cost for unitType
     * @param numCost number of resources to cost
     */
    protected void humanRecruit(int num, int unitType,int resType, int numCost){
        UnitSelector US = new UnitSelector(true,Math.min(num, 
                PlayerHolder.getCurrentPlayer().getDeck().getUnitDeck().size()), 
                unitType, resType, numCost);
        US.dispose();
    }
    protected void processAI(int num, int unitType,int resType, int numCost){
        while(num>0){
            if(!PlayerHolder.getCurrentPlayer().getDeck().getUnitDeck().isEmpty()){
                Unit unit = PlayerHolder.getCurrentPlayer().getDeck().drawUnitAt(
                    new Random().nextInt(PlayerHolder.getCurrentPlayer().getDeck().getUnitDeck().size()));
                final int[] cost;
                if((unitType!=-1)&&(unit.isType(unitType))){
                    cost = new int[4];
                    if(resType == Constants.food){
                        cost[0]=numCost;
                    }else if(resType == Constants.favor){
                        cost[1]=numCost;
                    }else if(resType == Constants.wood){
                        cost[2]=numCost;
                    }else if(resType == Constants.gold){
                        cost[3]=numCost;
                    }
                }else{
                    cost = unit.getCost();
                }
                if(PlayerHolder.getCurrentPlayer().getAge()>=unit.getAgeNeeded() &&
                            PlayerHolder.getCurrentPlayer().getResource().checkCost(cost)){
                    Bank.getResource().add(PlayerHolder.getCurrentPlayer().getResource().draw(cost));
                    PlayerHolder.getCurrentPlayer().getArmy().addUnit(unit);
                }else{      // return unit
                    PlayerHolder.getCurrentPlayer().getDeck().getUnitDeck().add(unit);
                }
            }
            num--;
        }
    }
    protected void trade(int srcUnitType, String name, int maxTrade){
        int maxAvailable=0;
        for(Unit unit: PlayerHolder.getCurrentPlayer().getDeck().getUnitDeck()){
            if(unit.getName().equalsIgnoreCase(name)){
                maxAvailable++;
            }
        }
        if(maxAvailable>0){
            if(PlayerHolder.getCurrentPlayer().isHuman()){
                TradeUnit tu = new TradeUnit(maxTrade, srcUnitType, name, maxAvailable);
                tu.dispose();
            }else{
                while(maxAvailable>0){
                    Unit temp = PlayerHolder.getCurrentPlayer().getArmy().getUnit(
                        new Random().nextInt(PlayerHolder.getCurrentPlayer().getArmy().getSize()));
                    if(temp.isType(srcUnitType)){
                        ArrayList<Unit> unitDeck = PlayerHolder.getCurrentPlayer().getDeck().getUnitDeck();
                        for(int i=0;i<unitDeck.size();i++){
                            if(unitDeck.get(i).getName().equalsIgnoreCase(name)){
                                PlayerHolder.getCurrentPlayer().getArmy().addUnit(
                                    PlayerHolder.getCurrentPlayer().getDeck().getUnitAt(i));
                            }
                        }
                    }else{
                        PlayerHolder.getCurrentPlayer().getArmy().addUnit(temp);
                    }
                    maxAvailable--;
                }
            }
        }
    }
    
    @Override
    public abstract boolean play();
}
