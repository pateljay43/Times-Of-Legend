/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author Eric
 */
public abstract class Unit {
    private final int ageNeeded;
    private final int baseDice;
    private final int[] type;
    private final int[] cost;       // food,favor,wood,gold
    private final boolean[] abilities;
    private final ImageIcon img;
    private final String name;
    private final int culture;
    
    public Unit(int a, int d, int t[], int[] c, boolean[] b,int _culture, String _name){
        ageNeeded = a;
        baseDice = d;
        type = new int[t.length];
        System.arraycopy(t, 0, type, 0, t.length);
        cost = new int[c.length];
        System.arraycopy(c, 0, cost, 0, c.length);
        abilities = new boolean[b.length];
        System.arraycopy(b, 0, abilities, 0, b.length);
        culture = _culture;
        name = _name;
        img = new ImageIcon(Constants.IF + culture + "/" + name + ".PNG");
    }
    
    public int attack(Unit u, boolean attacking, int target, boolean[] defBuildings, boolean ignore){
        int totalDice = baseDice;
        totalDice+=getBonusDice(u);
        if(!attacking){
            totalDice+=getDefenseBonusDice(target, defBuildings, ignore);
        }
        return rollDice(totalDice);
    }
    
    protected abstract int getBonusDice(Unit u);
    
    private int getDefenseBonusDice(int target, boolean[] defBuildings, boolean ignore){
        if(ignore)
            return 0;
        switch(target){
            case Constants.production:{
                if(defBuildings[1])
                    return 2;
            }
            case Constants.city:{
                if(defBuildings[0])
                    return 2;
            }
            default:{
                return 0;
            }
        }
    }
    
    private int rollDice(int totalDice){
        Random r = new Random();
        int sixes = 0;
        for(int i = 0; i < totalDice; i++){
            if(r.nextInt(6)+1==6)
                sixes++;
        }
        return sixes;
    }
    
    protected void setAbility(int a, boolean b){
        abilities[a] = b;
    }
    
    protected boolean getAbility(int a){
        return abilities[a];
    }
    
    public boolean onDefeat(Player p, ArrayList<Unit> u){
        return true;
    }
    
    public boolean onVictory(Player p, ArrayList<Unit> squad){
        return true;
    }
    
    public int getAgeNeeded(){
        return ageNeeded;
    }
    
    public int getNumOfTypes(){
        return type.length;
    }
    
    public int getType(int i){
        return type[i];
    }
    
    public int getBaseDice(){
        return baseDice;
    }
    
    public void newBattle(){
        
    }
    
    protected void preFight(Player p, Unit u){
        
    }
    
    protected ImageIcon getImage(){
        return img;
    }
    
    protected String getName(){
        return name;
    }
    
    protected int getCulture(){
        return culture;
    }
    public int[] getCost() {
        return cost;
    }
    public boolean isType(int t){
        for(int i = 0; i < type.length; i++){
            if(type[i]==t)
                return true;
        }
        return false;
    }
}
