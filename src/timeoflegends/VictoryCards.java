/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;

/**
 *
 * @author Eric
 */
public class VictoryCards {
    private static ArrayList<VCard> cards;
    private static VictoryCards obj;

    public static VictoryCards getInstance(){
        if(obj==null){
            obj = new VictoryCards();
        }
        return obj;
    }
    
    protected VictoryCards() {
        cards = new ArrayList<>();
        cards.add(new VCard("Most-Buildings", Constants.mostBuildings));
        cards.add(new VCard("Largest-Army", Constants.largestArmy));
        cards.add(new VCard("Won-Last-Battle", Constants.wonLastBattle));
        cards.add(new VCard("The-Wonder", Constants.wonder));
    }
    
    public static VCard getCard(int type){
        for(VCard cd : cards){
            if(cd.getType()==type){
                return cd;
            }
        }
        return null;
    }
}
