
package timeoflegends;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.ImageIcon;
/**
 *
 * @author JAY
 */
public abstract class Card {
    private final int type;     // 1 - perm, 2 - random, 3 - god
    public final ImageIcon img;
    private final String name;
    private final ImageIcon faceDown;
    
    public Card(int _type, String _name, int c){ // c -> used to pick 'c' culture image
        type = _type;
        if(type!=1){
            faceDown = new ImageIcon(Constants.randomFaceDown);
        }else{
            faceDown = null;
        }
        name = _name;
        img = new ImageIcon(Constants.IF + c + "/" + _name + ".png");
    }
    
    public String getName(){
       return name;
    }
    
    public int getType(){
        return type;
    }
    
    public ImageIcon getImage(){
        return img;
    }
    public ImageIcon getFaceDown() {
        return faceDown;
    }
    
    /**
     *
     * @param res resType
     * @param num 
     * @return
     */
    protected boolean charge(int res, int num) {
        int ret = PlayerHolder.getCurrentPlayer().getResource().getNumOfResource(res);
        if(ret>=num){
            Bank.getResource().add(res,PlayerHolder.getCurrentPlayer().getResource().draw(res, num));
        }else{
            return false;
        }
        return true;
    }
    
    /**
     *
     * @return returns weather current player is allowed to play extra action card in current turn
     */
    public abstract boolean play(); // if return is true -> remove the card from hand
    
    protected void gainUnit(int unitType,int max_unit){
        ArrayList<Unit> units = PlayerHolder.getCurrentPlayer().getDeck().getUnitDeck();
        if(PlayerHolder.getCurrentPlayer().isHuman()){
            boolean available = false;
            for (Unit unit : units) {
                if(unit.isType(unitType)){
                    available = true;
                    break;
                }
            }
            if(available){
                GainUnit gu = new GainUnit(unitType, max_unit);
                gu.dispose();
            }
        }else{
            while(max_unit>0){
                for (Unit unit : units) {
                    if(unit.isType(unitType)){
                        PlayerHolder.getCurrentPlayer().getDeck().getUnitDeck().remove(unit);
                        PlayerHolder.getCurrentPlayer().getArmy().addUnit(unit);
                        break;
                    }
                }
                max_unit--;
            }
        }
    }
    
    /**
     *
     * @param destPlayer perform destroy on destPlayer
     * @param tileType destroy a tile of tileType or any type if tileType = 0
     */
    protected void destroyProd(int destPlayer, int tileType){
        if(PlayerHolder.getCurrentPlayer().isHuman()){
    // destroy 1 food prod tile of player at 'playerpos'
            DestroyProd dp = new DestroyProd(1, tileType,destPlayer);
            dp.dispose();
        }else{
            Production_Tile[] tiles = PlayerHolder.getPlayer(destPlayer).
                                            getBoard().getProductionArea().getTiles();
            for (Production_Tile tile : tiles) {
                if (tile != null && (tile.getType() == tileType || tileType==0)) {
                    Production_Tile_Pool.putBackTiles(new ArrayList<>(Arrays.asList(tile)));
                    PlayerHolder.getPlayer(destPlayer).getBoard().getProductionArea().destroy(tile);
                    break;
                }
            }
        }
    }

    /**
     *
     * @param attacker player who is playing this card
     * @param unitType constrain on units of unitType, -1 for no constrain
     * @return
     */
    protected boolean eliminateUnit(int attacker, int unitType){
        ArrayList<Unit> army = PlayerHolder.getCurrentPlayer().getArmy().getUnits();
        int oldsize = army.size();
        if(oldsize>0){
            if(PlayerHolder.getPlayer(attacker).isHuman()){
                UnitSelector us = new UnitSelector(false, 1, unitType, -1, -1);
                us.dispose();
            }else{
                int r = new Random().nextInt(oldsize);
                if(unitType!=-1){
                    if(PlayerHolder.getCurrentPlayer().getArmy().getUnits().get(r).isType(unitType)){
                        PlayerHolder.getCurrentPlayer().getDeck().addUnit(
                            PlayerHolder.getCurrentPlayer().getArmy().removeUnit(r));
                    }
                }else{
                    PlayerHolder.getCurrentPlayer().getDeck().addUnit(
                        PlayerHolder.getCurrentPlayer().getArmy().removeUnit(r));
                }
            }
        }
        return PlayerHolder.getCurrentPlayer().getArmy().getSize()<oldsize;
    }
}
