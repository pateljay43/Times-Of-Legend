package timeoflegends;

import java.util.ArrayList;

/**
 *
 * @author JAY
 */
public class Player {
    private final Deck deck;
    private final Board board;
    private final boolean human;
    private final int culture;
    private int age;        // default - archaic age
    private final int id;
    private Resource resource;
    private final Army army;
    private final ArrayList<Card> cardsinhand;
    private boolean changeVillager;
    private ArrayList<Building_Tile> buildingsPool;     // unselected buildings
    private int houseinpool;    // 10
    
    public Player(boolean _human, int _culture, int _id){
        houseinpool = 10;
        human = _human;
        culture = _culture;
        id = _id;
        board = new Board(_culture);
        resource = new Resource(4, 4, 4, 4, 0);
        age = Constants.archaicAge;
        cardsinhand = new ArrayList<>();
        deck = new Deck(culture);
        army = new Army(culture);
        changeVillager = false;
        buildingsPool = new ArrayList<>();
        populate();
    }
    private void populate(){
        buildingsPool.add(new Building_Tile("woodWorkshop", Constants.woodWorkshop, new int[]{2,0,0,3}));
        buildingsPool.add(new Building_Tile("market", Constants.market, new int[]{0,2,0,3}));
        buildingsPool.add(new Building_Tile("house", Constants.house, new int[]{2,0,2,0}));
        buildingsPool.add(new Building_Tile("monument", Constants.monument, new int[]{4,0,0,2}));
        buildingsPool.add(new Building_Tile("granary", Constants.granary, new int[]{0,0,2,3}));
        buildingsPool.add(new Building_Tile("storehouse", Constants.storehouse, new int[]{2,2,2,2}));
        buildingsPool.add(new Building_Tile("siegeEngineWorkshop", Constants.siegeEngineWorkshop, new int[]{0,0,3,2}));
        buildingsPool.add(new Building_Tile("greatTemple", Constants.greatTemple, new int[]{4,4,4,4}));
        buildingsPool.add(new Building_Tile("armory", Constants.armory, new int[]{0,0,3,2}));
        buildingsPool.add(new Building_Tile("wall", Constants.wall, new int[]{0,0,3,3}));
//        buildingsPool.add(new Building_Tile("wonder", Constants.wonder, new int[]{10,10,10,10}));
        buildingsPool.add(new Building_Tile("goldMint", Constants.goldMint, new int[]{3,0,2,0}));
        buildingsPool.add(new Building_Tile("quarry", Constants.quarry, new int[]{4,0,0,1}));
        buildingsPool.add(new Building_Tile("tower", Constants.tower, new int[]{0,0,3,3}));     // img not available
    }
    
    public Deck getDeck(){
        return deck;
    }
    
    public Resource getResource() {
        return resource;
    }
    
    public void setResource(Resource resource) {
        this.resource = resource;
    }
    
    public ArrayList<Card> getCardsinhand() {
        return cardsinhand;
    }

    public Card removeCardInHand(int i){
        return cardsinhand.remove(i);
    }
    
    public void addCardInHand(Card card){
        cardsinhand.add(card);
    }
    public void addCardInHand(ArrayList<Card> cards){
        for(Card card : cards) {
            cardsinhand.add(card);
        }
    }
    
    public void advanceAge(){
        age++;
        switch(culture){
            case Constants.egyptian:
                switch(age){
                    case Constants.classicalAge:
                        deck.addUnit(new Unit_Priest());
                        break;
                    case Constants.heroicAge:
                        deck.addUnit(new Unit_Pharaoh());
                        break;
                    case Constants.mythicAge:
                        deck.addUnit(new Unit_SonOfOsiris());
                        buildingsPool.add(new Building_Tile("wonder", Constants.wonder, new int[]{10,10,10,10}));
                        break;
                }
                break;
            case Constants.greek:
                switch(age){
                    case Constants.classicalAge:
                        deck.addUnit(new Unit_ClassicalGreekHero());
                        break;
                    case Constants.heroicAge:
                        deck.addUnit(new Unit_HeroicGreekHero());
                        break;
                    case Constants.mythicAge:
                        deck.addUnit(new Unit_MythicalGreekHero());
                        buildingsPool.add(new Building_Tile("wonder", Constants.wonder, new int[]{10,10,10,10}));
                        break;
                }
                break;
            case Constants.norse:
                switch(age){
                    case Constants.classicalAge:
                        deck.addUnit(new Unit_ClassicalNorseHero());
                        break;
                    case Constants.heroicAge:
                        deck.addUnit(new Unit_HeroicNorseHero());
                        break;
                    case Constants.mythicAge:
                        deck.addUnit(new Unit_MythicNorseHero());
                        buildingsPool.add(new Building_Tile("wonder", Constants.wonder, new int[]{10,10,10,10}));
                        break;
                }
                break;
        }
    }
    public int getAge() {
        return age;
    }
    public String getAgeName(){
        switch(age){
            case Constants.archaicAge:
                return "Archaic";
            case Constants.classicalAge:
                return "Classical";
            case Constants.heroicAge:
                return "Heroic";
            default:
                return "Mythic";
        }
    }
    public Board getBoard() {
        return board;
    }
    public boolean isHuman() {
        return human;
    }
    public int getCulture() {
        return culture;
    }
    public String getCultureName(){
        switch (culture){
            case Constants.egyptian:{
                return "Egypt";
            }
            case Constants.greek:{
                return "Greece";
            }
            case Constants.norse:{
                return "Norse";
            }
            default:{
                return "";
            }
        }
    }
    public int getId() {
        return id;
    }
    public Army getArmy(){
        return army;
    }
    public boolean canChangeVillager() {
        return changeVillager;
    }

    public void setChangeVillager(boolean _changeVillager) {
        changeVillager = _changeVillager;
    }
    public ArrayList<Building_Tile> getBuildingsPool() {
        return buildingsPool;
    }
    public Building_Tile drawBuildingfromPool(int pos){
        if(buildingsPool.get(pos).getType()==Constants.house){
            houseinpool--;
            if(houseinpool==1){     // last house
                houseinpool--;
                return buildingsPool.remove(pos);
            }else{      // return copy of house
                return buildingsPool.get(pos);
            }
        }else{
            return (buildingsPool.get(pos)!=null)?buildingsPool.remove(pos):null;    
        }
    }

    public int getHouseinpool() {
        return houseinpool;
    }
    public void addBuildingToPool(Building_Tile b){
        if(b.getType()==Constants.house){
            if(!buildingsPool.contains(b)){
                buildingsPool.add(b);
            }
            houseinpool++;
        }else{
            buildingsPool.add(b);
        }
    }
}
