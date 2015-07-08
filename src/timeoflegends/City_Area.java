package timeoflegends;

/**
 * 
 *
 * @author JAY
 */
public class City_Area {
    private final Building_Tile[] tiles;
    private int numofhouses;
    private int numofbuildings;
    private final int MAX_BUILDINGS = 16;
    
    public City_Area(){
        tiles = new Building_Tile[MAX_BUILDINGS];       // all tiles are null initially
        numofhouses = 0;
        numofbuildings = 0;
    }
    public Building_Tile[] getTiles(){
        return tiles;
    }
    public int getNumberOfBuildings(){
        return numofbuildings;
    }
    public int numOfHouses(){
        return numofhouses;
    }
    public boolean buyBuilding(Building_Tile tile){     // tries to buy a building 'b'
        if((numofbuildings<16)?(tile.getType()==Constants.house)?numofhouses<10:
                find(tile.getType())==-1:
                    false){
            if(!checkCost(tile)){
                return false;
            }
            for(int i=0;i<16;i++){
                if(tiles[i]==null){
                    tiles[i] = tile;
                    if(tile.getType()==Constants.house){
                        numofhouses++;
                    }
                    numofbuildings++;
                    // pay cost
                    Bank.getResource().add(PlayerHolder.getCurrentPlayer().getResource().draw(tile.getCost()));
                    if(tile.getType()==Constants.wonder){
                        TimeOfLegends.setGameOver(true);
                    }
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    public boolean addBuilding(Building_Tile tile){
        if((numofbuildings<16)?(tile.getType()==Constants.house)?numofhouses<10:
                find(tile.getType())==-1:
                    false){
            for(int i=0;i<16;i++){
                if(tiles[i]==null){
                    tiles[i] = tile;
                    if(tile.getType()==Constants.house){
                        numofhouses++;
                    }
                    numofbuildings++;
                    if(tile.getType()==Constants.wonder){
                        TimeOfLegends.setGameOver(true);
                    }
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    /**
     * returns last occurring position of b building or -1 if not found
     * @param b
     * @return 
     */
    public int find(int b){
        for(int i=15;i>=0;i--){
            if(tiles[i]!=null && tiles[i].getType()==b){
                return i;
            }
        }
        return -1;
    }
    public Building_Tile removeBuilding(int b){       // tries to remove b
        int pos = find(b);
        if(pos!=-1){
            Building_Tile ret = tiles[pos];
            tiles[pos]=null;
            numofbuildings--;
            if(b==Constants.house){
                numofhouses--;
            }
            return ret;
        }
        return null;
    }

    private boolean checkCost(Building_Tile b) {
        int[] cost = b.getCost();
        Resource res = PlayerHolder.getCurrentPlayer().getResource();
        if(res.getNumOfFood()>=cost[0] &&
                res.getNumOfFavor()>=cost[1] &&
                res.getNumOfWood()>=cost[2] &&
                res.getNumOfGold()>=cost[3]){
            return true;
        }
        return false;
    }
}
