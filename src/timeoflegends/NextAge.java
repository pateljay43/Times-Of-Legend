/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

/**
 *
 * @author Eric
 */
public abstract class NextAge extends Card{
    private int cost;
    public NextAge(int c, String name, int type){
        super(type, name, c);
    }

    @Override
    public abstract boolean play();
    protected boolean setCost(int start){
        boolean advance = true;
        switch(PlayerHolder.getCurrentPlayer().getAge()){
            case Constants.archaicAge:{
                cost = start;
                break;
            }
            case Constants.classicalAge:{
                cost = start + 1;
                break;
            }
            case Constants.heroicAge:{
                cost = start + 2;
                break;
            }
            default:{
                advance = false;
            }
        }
        return advance;
    }
    protected void advance(boolean advance){
        Player p = PlayerHolder.getCurrentPlayer();
        if(advance&&
                p.getResource().getNumOfFood()>=cost&&
                p.getResource().getNumOfFavor()>=cost&&
                p.getResource().getNumOfWood()>=cost&&
                p.getResource().getNumOfGold()>=cost){
            PlayerHolder.getCurrentPlayer().advanceAge();
            Bank.getResource().add(
                PlayerHolder.getCurrentPlayer().getResource().draw(new int[]{cost,cost,cost,cost}));
//            Bank.getResource().addFood(PlayerHolder.getCurrentPlayer().getResource().removeFood(cost));
//            Bank.getResource().addFavor(PlayerHolder.getCurrentPlayer().getResource().removeFavor(cost));
//            Bank.getResource().addWood(PlayerHolder.getCurrentPlayer().getResource().removeWood(cost));
//            Bank.getResource().addGold(PlayerHolder.getCurrentPlayer().getResource().removeGold(cost));
        }
    }
}
