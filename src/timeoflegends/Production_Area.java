package timeoflegends;

/**
 *
 * @author JAY
 */
public class Production_Area {

    // state of Production_Area (pattern of production area)
    private final int[] state;

    /** all the tiles on the Production area
     * 
     * if value of a tile in tiles is nill -> that cell is empty
     * each tile contains utmost one villager
    */
    private final Production_Tile[] tiles;
    /**
     * villagers -- true indicates a villager is present in the respected index
     * initially no villager is present in any tile
     */
    private final boolean[] villagers;

    /**
     * Initializes the production area. Creates both state and tiles array
     * according to the parameter c (culture)
     *
     * @param c The culture of the player this area belongs to.
     */
    public Production_Area(int c) {
        state = new int[16];
        switch (c) {
            // egyptian
            case 1: 
                state[0] = state[1] = state[5] = state[8] = state[9]
                        = state[12] = Constants.desert;
                state[6] = state[7] = state[10] = state[11] = state[14]
                        = Constants.fertile;
                state[4] = Constants.forest;
                state[13] = state[15] = Constants.hills;
                state[2] = state[3] = Constants.swamp;
                break;
            // greek
            case 2: 
                state[0] = state[1] = state[6] = Constants.fertile;
                state[2] = state[7] = Constants.forest;
                state[4] = state[8] = state[9] = state[10] = state[11]
                        = state[13] = state[14] = state[15] = Constants.hills;
                state[5]=Constants.mountains;
                state[12]=Constants.desert;
                state[3]=Constants.swamp;
                break;
            // norse
            case 3: 
                state[0]=state[4]=state[15]=Constants.fertile;
                state[5]=state[10]=state[13]=state[14]=Constants.forest;
                state[6]=state[8]=state[11]=Constants.hills;
                state[1]=state[2]=state[3]=state[7]=Constants.mountains;
                state[12]=Constants.desert;
                state[9]=Constants.swamp;
                break;
            default:
                break;
        }
        tiles = new Production_Tile[16];
        villagers = new boolean[16];
        for (int i = 0; i < 16; i++){
            tiles[i] = null;
            villagers[i] = false;
        }
    }

    /**
     * attempt to add Production_Tile t to the tiles 
     * if tiles have empty cell for corresponding type of t
     * 
     * @param tile The Production_Tile to be added.
     * @return true if tile added else false
     */
    public boolean add(Production_Tile tile) {
        int tile_type = tile.getType();
        for(int i=0;i<tiles.length;i++){
            if(state[i]==tile_type && tiles[i]==null){
                tiles[i]=tile;
                return true;
            }
        }
        return false;
    }
    /**
     * attempt to destroy Production_Tile t to the tiles 
     * if tiles have empty cell for corresponding type of t
     * 
     * @param tile The Production_Tile to be added.
     * @return the removed tile or null if nothing is removed
     */
    public Production_Tile destroy(Production_Tile tile) {
        for(int i=0;i<tiles.length;i++){
            if(tiles[i]!=null && tiles[i]==tile){
                Production_Tile ret = tiles[i];
                tiles[i]=null;
                return ret;
            }
        }
        return null;
    }
    public int[] getState() {
        return state;
    }

    public Production_Tile[] getTiles() {
        return tiles;
    }
    
    public Production_Tile getTile(int i, int j){
        return tiles[(i*4)+j];
    }
    
    public int getNumberOfTiles(){
        int count=0;
        for (Production_Tile tile : tiles) {
            if (tile != null)
                count++;
        }
        return count;
    }
    public boolean isVillagerPresenetAt(int pos){
        return villagers[pos];
    }
    public boolean addVillager(int pos){       // tries to add villager to 'pos'
        return (!villagers[pos])?(villagers[pos]=true):false;
    }
}
