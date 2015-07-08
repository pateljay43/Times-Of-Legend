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
 * @author Eric
 */
public abstract class Trade extends Card{
    ArrayList<Integer> transaction;

    public Trade(int c, String name, int type){
        super(type, name, c);
    }

    @Override
    public abstract boolean play();
    
    protected void humanTrade(){
        TradeSelector ts = new TradeSelector();
        while(!ts.isFinished()){
            try {    Thread.sleep(10);   }
            catch (InterruptedException ex) {     ex.printStackTrace();   }
        }
        transaction = ts.getResults();
        ts.dispose();
    }
    
    protected void processAI(){
        transaction = new ArrayList();
        Random r = new Random();
        int playerTrade = (r.nextInt(4));
        int bankTrade = (r.nextInt(4));
        int tradeAmount = Math.min(PlayerHolder.getCurrentPlayer().getResource().
                getNumOfResource(playerTrade), Bank.getResource().getNumOfResource(bankTrade));
        for(int i = 0; i < 8; i++){
            transaction.add(0);
        }
        transaction.set(playerTrade, tradeAmount);
        transaction.set(bankTrade+4, tradeAmount);
    }
    
    protected void payCost(int cost){
        if(PlayerHolder.getCurrentPlayer().getBoard().getCityArea().find(Constants.market)!=-1){
            cost = 0;
        }
        if(cost > 0){
            if(PlayerHolder.getCurrentPlayer().isHuman()){
                PayTradeFine pft = new PayTradeFine(cost);
                while(!pft.paidOff()){
                    try {    Thread.sleep(1);   }
                    catch (InterruptedException ex) {     ex.printStackTrace();   }
                }
                transaction = pft.getResults();     // food, favor, wood, gold
                pft.dispose();
            }else{
                Random r = new Random();
                transaction = new ArrayList();
                for(int i = 0; i < 4; i++){
                    transaction.add(0);
                }
                for(int i = 0; i < cost; i++){
                    int choice = r.nextInt(4);
                    transaction.set(choice, transaction.get(choice)+1);
                }
            }
            payFine(transaction);
        }
    }
    protected void checkTemple(){
        if(PlayerHolder.getCurrentPlayer().getBoard().getCityArea().find(Constants.greatTemple)!=-1
                && PlayerHolder.getCurrentPlayer().getResource().getNumOfFavor() >= 8 
                && Bank.getResource().getNumOfVictoryCubes()>=1){
            int available = Math.min(PlayerHolder.getCurrentPlayer().getResource().getNumOfFavor()/8, 
                    Bank.getResource().getNumOfVictoryCubes());
            if(PlayerHolder.getCurrentPlayer().isHuman()){
                BuyVictoryCube bvc = new BuyVictoryCube(available);
                while(!bvc.isFinished()){
                    try {   Thread.sleep(1);    }
                    catch (InterruptedException ex) {   ex.printStackTrace();   }
                }
                buyVictoryCubes(bvc.getResult());
                bvc.dispose();
            }else{
                buyVictoryCubes((available==0)?0:new Random().nextInt(available+1));
            }
        }
    }
    private void payFine(ArrayList<Integer> t){
        System.out.println("Fine Paid:");
        int resource = PlayerHolder.getCurrentPlayer().getResource().removeFood(t.remove(0));
        System.out.println("Food: "+resource);
        Bank.getResource().addFood(resource);
        resource = PlayerHolder.getCurrentPlayer().getResource().removeFavor(t.remove(0));
        System.out.println("Favor: "+resource);
        Bank.getResource().addFavor(resource);
        resource = PlayerHolder.getCurrentPlayer().getResource().removeWood(t.remove(0));
        System.out.println("Wood: "+resource);
        Bank.getResource().addWood(resource);
        resource = PlayerHolder.getCurrentPlayer().getResource().removeGold(t.remove(0));
        System.out.println("Gold: "+resource);
        Bank.getResource().addGold(resource);
    }
    
    protected void trade(ArrayList<Integer> t){
        int resource = PlayerHolder.getCurrentPlayer().getResource().removeFood(t.remove(0));
        Bank.getResource().addFood(resource);
        resource = PlayerHolder.getCurrentPlayer().getResource().removeFavor(t.remove(0));
        Bank.getResource().addFavor(resource);
        resource = PlayerHolder.getCurrentPlayer().getResource().removeWood(t.remove(0));
        Bank.getResource().addWood(resource);
        resource = PlayerHolder.getCurrentPlayer().getResource().removeGold(t.remove(0));
        Bank.getResource().addGold(resource);
        
        resource = Bank.getResource().removeFood(t.remove(0));
        PlayerHolder.getCurrentPlayer().getResource().addFood(resource);
        resource = Bank.getResource().removeFavor(t.remove(0));
        PlayerHolder.getCurrentPlayer().getResource().addFavor(resource);
        resource = Bank.getResource().removeWood(t.remove(0));
        PlayerHolder.getCurrentPlayer().getResource().addWood(resource);
        resource = Bank.getResource().removeGold(t.remove(0));
        PlayerHolder.getCurrentPlayer().getResource().addGold(resource);
    }
    
    private void buyVictoryCubes(int v){
        if(v!=0){
            int ret = Bank.getResource().removeVictoryCube(v);
            PlayerHolder.getCurrentPlayer().getResource().addVictoryCubes(ret);
            PlayerHolder.getCurrentPlayer().getResource().removeFavor(ret*8);
            Bank.getResource().addFavor(ret*8);
        }
    }
    
}
