package timeoflegends;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author JAY
 */
public class Production_Tile_Pool {
    private static ArrayList<Production_Tile> pool;
    /**
     * Initializes all tiles, adds them to the ArrayList and shuffles the whole
     * thing.
     */
    public Production_Tile_Pool(){
        pool = new ArrayList<>();
        populatePool();
        randomize();
    }
    
    private void populatePool(){
        addTiles(Constants.fertile, Constants.food, 2, 12);
        addTiles(Constants.fertile, Constants.wood, 1, 3);
        addTiles(Constants.fertile, Constants.favor, 1, 3);
        addTiles(Constants.fertile, Constants.gold, 1, 3);
        addTiles(Constants.forest, Constants.food, 1, 2);
        addTiles(Constants.forest, Constants.gold, 1, 2);
        addTiles(Constants.forest, Constants.favor, 1, 2);
        addTiles(Constants.forest, Constants.wood, 2, 9);
        addTiles(Constants.hills, Constants.gold, 2, 4);
        addTiles(Constants.hills, Constants.food, 1, 4);
        addTiles(Constants.hills, Constants.favor, 1, 4);
        addTiles(Constants.hills, Constants.wood, 1, 4);
        addTiles(Constants.mountains, Constants.wood, 1, 3);
        addTiles(Constants.mountains, Constants.favor, 1, 3);
        addTiles(Constants.mountains, Constants.gold, 2, 6);
        addTiles(Constants.desert, Constants.favor, 2, 7);
        addTiles(Constants.desert, Constants.gold, 1, 7);
        addTiles(Constants.swamp, Constants.food, 1, 4);
        addTiles(Constants.swamp, Constants.favor, 1, 4);
        addTiles(Constants.swamp, Constants.wood, 1, 4);
    }
    
    private void addTiles(int type, int res, int num, int x){
        Production_Tile t = new Production_Tile(type, res, num,
                ""+type+"-"+res);
        for(int i = 0; i < x; i++){
            pool.add(t);
        }
    }
    
    /**
     * Gives how many tiles are left in the pool.
     * 
     * @return The size of the ArrayList.
     */
    public static int getSize(){
        return pool.size();
    }
    
    // check for empty pool
    public static boolean empty(){
        return pool.isEmpty();
    }
    
    /**
     * Removes and returns the top n tiles from the pool.
     * 
     * @param n How many tiles to be removed.
     * @return An arrayList of the tiles removed from the pool.
     */
    public static ArrayList<Production_Tile> getTop_N_Tiles(int n){
        ArrayList<Production_Tile> ret = new ArrayList<>();
        for(int i = 0; i < n; i++)
            ret.add(pool.remove(0));
        return ret;
    }
    
    /**
     * Puts Back unselected tiles to the pool.
     * 
     * @param list tiles to be put back to pool.
     * randomize again after all tiles added to pool
     */
    public static void putBackTiles(ArrayList<Production_Tile> list){
        while(!list.isEmpty())
            pool.add(list.remove(0));
        randomize();
    }
    
    /**
     * just test function -> may not need it
     * Retrieves the tile at index n without displacing it from the list.
     * 
     * @param n The index of the tile to be looked at.
     * @return The tile at index n in the ArrayList.
     */
    public static Production_Tile getTile(int n){
        return pool.get(n);
    }
    
    /**
     * Picks a random tile and places it in the back of the ArrayList. This
     * happens 1,000 times.
     */
    private static void randomize(){
        Random r = new Random();
        for(int i = 0; i < 1000; i++){
            pool.add(pool.remove(r.nextInt(pool.size())));
        }
    }
    
}
