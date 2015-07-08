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
public class Army {
    private ArrayList<Unit> units;
    
    public Army(int culture){
        units = new ArrayList();
        switch(culture){
            case Constants.egyptian:{
                for(int i = 0; i < 2; i++)
                    units.add(new Unit_Spearman());
                for(int i = 0; i < 2; i++)
                    units.add(new Unit_Chariot_Archer());
                for(int i = 0; i < 2; i++)
                    units.add(new Unit_Elephant());
                break;
            }
            case Constants.greek:{
                for(int i = 0; i < 2; i++)
                    units.add(new Unit_Hoplite());
                for(int i = 0; i < 2; i++)
                    units.add(new Unit_Toxotes());
                for(int i = 0; i < 2; i++)
                    units.add(new Unit_Hippokon());
                break;
            }
            case Constants.norse:{
                for(int i = 0; i < 2; i++)
                    units.add(new Unit_Throwing_Axeman());
                for(int i = 0; i < 2; i++)
                    units.add(new Unit_Jarl());
                for(int i = 0; i < 2; i++)
                    units.add(new Unit_Huskarl());
                break;
            }
        }
    }
    
    public void addUnit(Unit u){
        units.add(u);
    }
    
    public Unit removeUnit(int i){
        Unit u = null;
        if(i < getSize()){
            u = units.remove(i);
        }
        return u;
    }
    
    public Unit getUnit(int i){
        Unit u = null;
        if(i < getSize()){
            u = units.get(i);
        }
        return u;
    }
    
    public int getPositionOf(Unit u){
        return units.lastIndexOf(u);
    }
    
    public int getSize(){
        return units.size();
    }
    
    public ArrayList<Unit> getUnits(){
        return units;
    }
}
