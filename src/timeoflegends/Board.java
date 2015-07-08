package timeoflegends;

/**
 *
 * @author JAY
 */
public class Board {
    private final City_Area city;
    private final Production_Area production;
    private final boolean[] villagerOnProductionArea;       // 
    private int numofvillagers;     // total available villagers
    
    /**
     * Initializes the player board.
     * 
     * @param c The culture of the player.
     * 
     * needed for the production area and background pictures of board
     */
    public Board(int c){
        city = new City_Area();
        production = new Production_Area(c);
        villagerOnProductionArea = new boolean[16];
        numofvillagers = 0;
    }
    
    /**
     * Makes the information of the city avaliable.
     * 
     * @return The city area of the current player.
     */
    public City_Area getCityArea(){
        return city;
    }
    
    /**
     * Makes the information of the production avaliable.
     * 
     * @return The production area of the current player.
     */
    public Production_Area getProductionArea(){
        return production;
    }
    public boolean isVillagerOnProduction(int pos){
        return villagerOnProductionArea[pos];
    }
    public boolean[] getVillagerOnProductionArea() {
        return villagerOnProductionArea;
    }
    public boolean addVillagerOnProduction(int pos){     // tries to add villager
        if(numofvillagers<=city.numOfHouses()){
            numofvillagers++;
            villagerOnProductionArea[pos]=!villagerOnProductionArea[pos];
            return true;
        }else{
            return false;
        }
    }
    public void removeVillagerOnProduction(int pos){     // tries to remove villager
        villagerOnProductionArea[pos]=false;
        numofvillagers--;
    }
}
