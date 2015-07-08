package timeoflegends;

import javax.swing.ImageIcon;

/**
 *
 * @author JAY
 */
public class Production_Tile {
    private final int type;
    private final int resource;
    private final int number;
    private final ImageIcon image;

    public ImageIcon getImage() {
        return image;
    }
    
    public Production_Tile(int t, int r, int n,String path){
        type = t;
        resource = r;
        number = n;
        image = new ImageIcon(Constants.IF + path + ".png");
    }
    
    /**
     * Gives the environment of the tile.
     * 
     * @return The integer corresponding to the environment.
     */
    public int getType(){
        return type;
    }
    
    /**
     * Might be removed later, but a good reference for what int means what.
     * 
     * leave 0 int for something else later in coding like 'not defined'
     * @return The name of the environment corresponding to the integer given.
     */
    public String getTypeName(){
        switch(type){
            case Constants.fertile:{
                return "Fertile";
            }
            case Constants.forest:{
                return "Forest";
            }
            case Constants.hills:{
                return "Hills";
            }
            case Constants.mountains:{
                return "Mountains";
            }
            case Constants.desert:{
                return "Desert";
            }
            case Constants.swamp:{
                return "Swamp";
            }
            default:{
                return "";
            }
        }
    }
    
    /**
     * Gives the resource type of the tile.
     * 
     * leave 0 int for something else later in coding like 'not defined'
     * @return The integer corresponding to the resource type.
     */
    public int getResource(){
        return resource;
    }
    
    /**
     * Might be removed later, but a good reference for what int means what.
     * 
     * @return The name of the resource corresponding to the integer given.
     */
    public String getResourceName(){
        switch(resource){
            case 1:{
                return "Food";
            }
            case 2:{
                return "Favor";
            }
            case 3:{
                return "Wood";
            }
            case 4:{
                return "Gold";
            }
            default:{
                return "";
            }
        }
    }
    
    /**
     * Gives the number of resources on the tile.
     * 
     * @return The number of resources.
     */
    public int getNumber(){
        return number;
    }
}
