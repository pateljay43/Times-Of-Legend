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
public class VCard {
    private final ImageIcon img;
    private final String name;
    private int cubes;
    private final int type;
    public VCard(String _name, int _type) {
        name = _name;
        img = new ImageIcon(Constants.IF + _name + ".png");
        cubes = 0;
        type = _type;
    }
    public ImageIcon getImage(){
        return img;
    }
    public void addCube(int card){
        if(card==0){
            return;
        }
        cubes = cubes + card;
    }
    public int getCubes(){
        return cubes;
    }
    public int awardCubes(){
        int ret = cubes;
        cubes = 0;
        return ret;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    
}
