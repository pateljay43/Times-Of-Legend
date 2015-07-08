/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import javax.swing.ImageIcon;

/**
 *
 * @author JAY
 */
public class Building_Tile {
    private final int type;       // building type
    private final int[] cost;       // food, favor, wood, gold
    private final String name;
    private final ImageIcon img;

    public Building_Tile(String _name,int _type, int[] _cost) {
        type = _type;
        cost = _cost;
        name = _name;
        img = new ImageIcon(Constants.buildingImg+type+".PNG");
    }
    public int getType() {
        return type;
    }

    public int[] getCost() {
        return cost;
    }
    
    public String getName() {
        return name;
    }

    public ImageIcon getImg() {
        return img;
    }

}
