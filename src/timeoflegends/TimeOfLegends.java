package timeoflegends;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
/**
 *
 * @author JAY
 */
public class TimeOfLegends {
    private static Production_Tile_Pool pool;
    private static CultureChoice culture;
    private static ArrayList<Integer> cultures;
    private static final int PLAYERS = 3; //if more players are wanted, this just needs to change
    private static int player_id = 0;
    private static BoardContainer bc;
    private static boolean gameOver;
    public static int currentPlayer = 0;
    private static Player winner = null;
    private static ArrayList<Integer> extraTurnPlayerPos;
    
    public static void main(String[] args) {
        try{
        Bank.getInstance();     // initialize bank
        VictoryCards.getInstance();     // initialize Victory cards
        gameOver = false;
        pool = new Production_Tile_Pool();
        cultures = new ArrayList<>();
        for(int i=1;i<=PLAYERS;i++){
            cultures.add(i);
        }
        // choose culture and human player is on top of list (starting player)
        SplashScreen ss = new SplashScreen();
        ss.dispose();
        
        chooseRace();
        
        JOptionPane.showMessageDialog(null, "Now select your production tiles");

        // create Game Board container
        bc = new BoardContainer(pool);       // selection not done -> false
        pool = bc.getPool();     // update pool after tiles selection
        
        while(!gameOver){   // this loops evaluate one turn untill game is over
            turn();
            // change starting player
            PlayerHolder.cycle();
            bc.redraw(PlayerHolder.getHumanPlayer());
        }
        awardVC();
        findWinner();
        bc.redraw(PlayerHolder.getHumanPlayer());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private static void turn() {
        // placement of victory cubes
        Victory_Cube_Placement VCPlace = new Victory_Cube_Placement(true);
        VCPlace.dispose();
        for(int i=0;i<4;i++){
            System.out.println(i + ": " + VictoryCards.getCard(i+1).getCubes());
        }
        bc.redraw(PlayerHolder.getHumanPlayer());
        
// card selection        
        Permanent_Card_Selection pcs = new Permanent_Card_Selection();
        pcs.dispose();
        Random_Card_Selection rcs = new Random_Card_Selection();
        rcs.dispose();

// card play
        for(int i=0;i<PLAYERS;i++){
            for(int j=0;j<PlayerHolder.getPlayer(i).getCardsinhand().size();j++){
                System.out.println(PlayerHolder.getPlayer(i).getCultureName()+": "
                        +PlayerHolder.getPlayer(i).getCardsinhand().get(j).getName());
            }
        }
        extraTurnPlayerPos = new ArrayList<>();   // no one
        // three play out of hand_held cards by each player at a time = 9 cardplays
        for(int i=0;i<PLAYERS*3;i++){
            round();
            if(gameOver){
                return;
            }
        }
        if(!extraTurnPlayerPos.isEmpty()){
            for(int pos : extraTurnPlayerPos){
                int currpos = PlayerHolder.getCurrentPlayerPosition();
                while(PlayerHolder.getCurrentPlayerPosition()!=pos){
                    PlayerHolder.nextPlayer();
                }
                round();
                if(gameOver){
                    return;
                }
                while(PlayerHolder.getCurrentPlayerPosition()!=currpos){
                    PlayerHolder.nextPlayer();
                }
            }
            extraTurnPlayerPos.clear();
        }
        /** if bank has at least one victory cube left -> then conduct spoilage according to player's age
         */
        if(Bank.getResource().getNumOfVictoryCubes()>0){
            for(int i=0;i<PLAYERS;i++){
                PlayerHolder.getCurrentPlayer().setResource(
                        spoilage(PlayerHolder.getCurrentPlayer().getResource(),
                                (PlayerHolder.getCurrentPlayer().getBoard().getCityArea()
                                        .find(Constants.storehouse)!=-1)?8:5));
                PlayerHolder.nextPlayer();
            }
        }else{
            gameOver = true;
        }
        Return_Card_Selection ret = new Return_Card_Selection();
        ret.dispose();
    }
    private static void round(){
        Card card = null;
        if(PlayerHolder.getCurrentPlayer().isHuman()&&
                !PlayerHolder.getCurrentPlayer().getCardsinhand().isEmpty()){
            Card_Play cardPlay = new Card_Play();
            try{    while(!cardPlay.isfinish()){    Thread.sleep(1);    } } // wait to click done
            catch(Exception ex){   ex.printStackTrace();   }
            card = cardPlay.getCard();
            cardPlay.dispose();
            if(card!=null){
                System.out.println("Human played: "+card.getName());
                if(card.play()){
                    extraTurnPlayerPos.add(PlayerHolder.getCurrentPlayerPosition());
                }
            }
        }else{      // ai player plays random card from hand
            if(!PlayerHolder.getCurrentPlayer().getCardsinhand().isEmpty()){
                card = PlayerHolder.getCurrentPlayer().getCardsinhand().remove(
                new Random().nextInt(PlayerHolder.getCurrentPlayer().getCardsinhand().size()));
                System.out.println(PlayerHolder.getCurrentPlayer().getCultureName()+ " played: "+card.getName());
                if(card.play()){
                    extraTurnPlayerPos.add(PlayerHolder.getCurrentPlayerPosition());
                }
            }
        }
        if(card!=null){
            PlayerHolder.getCurrentPlayer().getDeck().returnCard(card);
            bc.redraw(PlayerHolder.getHumanPlayer());
        }
        PlayerHolder.nextPlayer();
    }
    private static Resource spoilage(Resource PlayerRes, int limit){
        if(PlayerRes.getNumOfFavor()>limit){
            Bank.getResource().addFavor(PlayerRes.removeFavor(PlayerRes.getNumOfFavor()-limit));
        }
        if(PlayerRes.getNumOfFood()>limit){
            Bank.getResource().addFood(PlayerRes.removeFood(PlayerRes.getNumOfFood()-limit));
        }
        if(PlayerRes.getNumOfGold()>limit){
            Bank.getResource().addGold(PlayerRes.removeGold(PlayerRes.getNumOfGold()-limit));
        }
        if(PlayerRes.getNumOfWood()>limit){
            Bank.getResource().addWood(PlayerRes.removeWood(PlayerRes.getNumOfWood()-limit));
        }
        return PlayerRes;
    }
    private static void chooseRace(){
        // create 1 human player and add to list of players
        culture = new CultureChoice();    // for human player
        while(culture.getChoice()==0){      // wait till a culture is selected
            try {Thread.sleep(1);} 
            catch (InterruptedException ex) {ex.printStackTrace();}
        }
            // player_id starts from 1
        PlayerHolder.addPlayer(new Player(true,
            cultures.remove(cultures.indexOf(culture.getChoice())),
            ++player_id));
        culture.dispose();  // dispose jframe of culturechoice
        
        // create two more AI players and add to list of players
        while(!cultures.isEmpty()){
            PlayerHolder.addPlayer(new Player(false, 
                cultures.remove(new Random().nextInt(cultures.size())),
                ++player_id));
        }
    }
    /**
     * set game over
     * @param gameOver
     */
    public static void setGameOver(boolean gameOver) {
        TimeOfLegends.gameOver = gameOver;
    }
    private static void awardVC() {
        for(int i=0;i<4;i++){
            VCard card = VictoryCards.getCard(i+1);
            switch(card.getType()){
                case Constants.wonder:
                    for(int j=0;j<PlayerHolder.size();j++){
                        if(PlayerHolder.getPlayer(j).getBoard().getCityArea().find(Constants.wonder)!=-1){
                            PlayerHolder.getPlayer(j).getResource().add(Constants.vc, card.awardCubes());
                            System.out.println("Player "+PlayerHolder.getPlayer(j).getCultureName()
                                +" gets wonder cubes");
                        }
                    }
                    break;
                case Constants.mostBuildings:
                    int max=0;
                    int pos=-1;
                    for(int j=0;j<PlayerHolder.size();j++){
                        int builds = PlayerHolder.getPlayer(j).getBoard().getCityArea().getNumberOfBuildings();
                        if(builds>max){
                            max = builds;
                            pos = j;
                        }else if(builds==max){
                            pos=-1;
                        }
                    }
                    if(pos!=-1){
                        PlayerHolder.getPlayer(pos).getResource().add(Constants.vc, card.awardCubes());
                        System.out.println("Player "+PlayerHolder.getPlayer(pos).getCultureName()
                                +" gets most building cubes");
                    }
                    break;
                case Constants.wonLastBattle:
                    // everything on this card is awarded right when a player wins -> in attack card
                    break;
                case Constants.largestArmy:
                    int max1=0;
                    int pos1=-1;
                    for(int j=0;j<PlayerHolder.size();j++){
                        int size = PlayerHolder.getPlayer(j).getArmy().getSize();
                        if(size>max1){
                            max1 = size;
                            pos1 = j;
                        }else if(size==max1){
                            pos1=-1;
                        }
                    }
                    if(pos1!=-1){
                        PlayerHolder.getPlayer(pos1).getResource().add(Constants.vc, card.awardCubes());
                        System.out.println("Player "+PlayerHolder.getPlayer(pos1).getCultureName()
                                +" gets largest army cubes");
                    }
                    break;
                default:
                    break;
            }
        }
        System.out.println("Victory Cubes on Victory-cards left:");
        for(int i=0;i<4;i++){
            System.out.println(i + ": " + VictoryCards.getCard(i+1).getCubes());
        }
    }
    private static void findWinner() {
        int maxvc = 0;
        int pos = -1;
        for(int j=0;j<PlayerHolder.size();j++){
            int vc = PlayerHolder.getPlayer(j).getResource().getNumOfVictoryCubes();
            if(vc>maxvc){
                maxvc = vc;
                pos = j;
            }else if(vc==maxvc){
                if(pos!=-1){
                    if(PlayerHolder.getPlayer(pos).getResource().getNumOfResource(0)<
                            PlayerHolder.getPlayer(j).getResource().getNumOfResource(0)){
                        pos=j;
                    }
                }
            }
        }
        if(pos!=-1){
            winner = PlayerHolder.getPlayer(pos);
        }
        if(winner!=null){
            JOptionPane.showMessageDialog(null, winner.getCultureName()+" Player wins the GAME !!");
        }
    }
}
