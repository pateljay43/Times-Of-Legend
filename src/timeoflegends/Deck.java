/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Eric
 */
public class Deck {
    private final ArrayList<Card> permDeck;
    private final ArrayList<Card> randDeck;
    private final ArrayList<Card> discardDeck;
    private final ArrayList<Unit> unitDeck;

    public Deck(int c){
        permDeck = new ArrayList();
        randDeck = new ArrayList();
        discardDeck = new ArrayList();
        unitDeck = new ArrayList();
        populate(c);
        shuffle(randDeck);
    }
    
    private void populate(int c){
        permDeck.add(new P_TradeCard(c));
        permDeck.add(new P_AttackCard(c));
        permDeck.add(new P_ExploreCard(c));    // max # tiles = 1 for permanent
        permDeck.add(new P_RecruitCard(c));
        permDeck.add(new P_BuildCard(c));    // max # builds allowed is 1
        permDeck.add(new P_NextAgeCard(c));
        permDeck.add(new P_GatherCard(c));
        switch (c){
            case Constants.egyptian:{
                populateEgyptDeck(c);
                break;  
            }
            case Constants.greek:{
                populateGreekDeck(c);
                break;
            }
            case Constants.norse:{
                populateNorseDeck(c);
                break;
            }
        }
    }
    
    public Card randDraw(){
        if(randDeck.isEmpty()){
            while(!discardDeck.isEmpty()){
                randDeck.add(discardDeck.remove(0));
            }
            shuffle(randDeck);
        }
        return randDeck.remove(0);
    }
    
    public ArrayList<Card> randDraw (ArrayList<Integer> positions){
        ArrayList<Card> cards = new ArrayList<>();
        int count = 0;
        for(int pos : positions){
            cards.add(randDraw());
            count++;
        }
        return cards;
    }
    
    public Card permDraw(int i){
        return permDeck.remove(i);
    }
    
    public ArrayList<Card> permDraw (ArrayList<Integer> positions){
        ArrayList<Card> cards = new ArrayList<>();
        int count = 0;
        for(int pos : positions){
            cards.add(permDraw(pos-count));
            count++;
        }
        return cards;
    }
    
    public void returnCard(Card c){
        if(c.getType()==1){
            permDeck.add(c);
        }else{
            discardDeck.add(c);
        }
    }
    
    private void shuffle(ArrayList<Card> d){
        Random r = new Random();
        for(int i = 0; i < 1000; i++){
            d.add(d.remove(r.nextInt(d.size())));
        }
    }
    
    public int randSize(){
        return randDeck.size();
    }
    
    public int permSize(){
        return permDeck.size();
    }
    
    public Card getPermCard(int i){
        return permDeck.get(i);
    }
    public int getUnitPosition(Unit u){
        return unitDeck.lastIndexOf(u);
    }
    public ArrayList<Unit> getUnitDeck() {
        return unitDeck;
    }
    public Unit getUnitAt(int pos){
        return unitDeck.get(pos);
    }
    public Unit drawUnit(int type){
        for (Unit unit : unitDeck) {
            if(unit.isType(type)){
                return unit;
            }
        }
        return null;
    }
    public Unit drawUnitAt(int pos){
        return unitDeck.remove(pos);
    }
    public void addUnit(Unit u){
        unitDeck.add(u);
    }

    public Card getRandCard(int i) {
        return randDeck.get(i);
    }

    private void populateEgyptDeck(int c) {
        unitDeck.add(new Unit_Wadjet());
        unitDeck.add(new Unit_Phoenix());
        unitDeck.add(new Unit_Spearman());
        unitDeck.add(new Unit_Mummy());
        unitDeck.add(new Unit_Elephant());
        unitDeck.add(new Unit_Chariot_Archer());
        unitDeck.add(new Unit_Anubite());
        unitDeck.add(new Unit_Sphinx());
        unitDeck.add(new Unit_Scorpion_Man());
        randDeck.add(new G_Nephthys());
        randDeck.add(new G_Horus());
        randDeck.add(new G_Ptah());
        randDeck.add(new G_Ra());
        randDeck.add(new G_Tg());
        randDeck.add(new G_Set());
        randDeck.add(new G_Hathor());
        randDeck.add(new G_Bast());
        randDeck.add(new G_Anubis());
        randDeck.add(new G_Osiris());
        randDeck.add(new G_Isis());
        randDeck.add(new G_Sekhmet());
        randDeck.add(new G_Thoth());
        randDeck.add(new R_Attack(c, 5));
        randDeck.add(new R_Attack(c, 5));
        randDeck.add(new R_Attack(c, 6));
        randDeck.add(new R_Attack(c, 6));
        randDeck.add(new R_Attack(c, 7));
        randDeck.add(new R_Build(c,4));
        randDeck.add(new R_Build(c,3));
        randDeck.add(new R_Build(c,2));
        randDeck.add(new R_Build(c,3));
        randDeck.add(new R_Build(c,2));
        randDeck.add(new R_Explore(c,0));
        randDeck.add(new R_Explore(c,2));
        randDeck.add(new R_Explore(c,0));
        randDeck.add(new R_Explore(c,2));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_NextAge(c));
        randDeck.add(new R_NextAge(c));
        randDeck.add(new R_Recruit(c,3));
        randDeck.add(new R_Recruit(c,3));
        randDeck.add(new R_Recruit(c,4));
        randDeck.add(new R_Recruit(c,5));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
    }

    private void populateGreekDeck(int c) {
        unitDeck.add(new Unit_Centaur());
        unitDeck.add(new Unit_Cyclops());
        unitDeck.add(new Unit_Manticore());
        unitDeck.add(new Unit_Toxotes());
        unitDeck.add(new Unit_Hydra());
        unitDeck.add(new Unit_Minotaur());
        unitDeck.add(new Unit_Hippokon());
        unitDeck.add(new Unit_Hoplite());
        unitDeck.add(new Unit_Medusa());
        randDeck.add(new G_Hermes());
        randDeck.add(new G_Hera());
        randDeck.add(new G_Artemis());
        randDeck.add(new G_Poseidon());
        randDeck.add(new G_Hades());
        randDeck.add(new G_Dionysus());
        randDeck.add(new G_Hephaestos());
        randDeck.add(new G_Zeus());
        randDeck.add(new G_Apollo());
        randDeck.add(new G_Aphrodite());
        randDeck.add(new G_Ares());
        randDeck.add(new R_Attack(c, 5));
        randDeck.add(new R_Attack(c, 5));
        randDeck.add(new R_Attack(c, 6));
        randDeck.add(new R_Attack(c, 6));
        randDeck.add(new R_Attack(c, 7));
        randDeck.add(new R_Attack(c, 7));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Build(c,2));
        randDeck.add(new R_Build(c,2));
        randDeck.add(new R_Build(c,3));
        randDeck.add(new R_Build(c,3));
        randDeck.add(new R_Build(c,3));
        randDeck.add(new R_Build(c,4));
        randDeck.add(new R_Explore(c,0));
        randDeck.add(new R_Explore(c,0));
        randDeck.add(new R_Explore(c,2));
        randDeck.add(new R_Explore(c,2));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_NextAge(c));
        randDeck.add(new R_NextAge(c));
        randDeck.add(new R_Recruit(c,3));
        randDeck.add(new R_Recruit(c,3));
        randDeck.add(new R_Recruit(c,4));
        randDeck.add(new R_Recruit(c,4));
        randDeck.add(new R_Recruit(c,5));
        randDeck.add(new R_Recruit(c,5));
        
    }

    private void populateNorseDeck(int c) {
        unitDeck.add(new Unit_Throwing_Axeman());
        unitDeck.add(new Unit_Jarl());
        unitDeck.add(new Unit_Valkyrie());
        unitDeck.add(new Unit_Troll());
        unitDeck.add(new Unit_Nidhogg());
        unitDeck.add(new Unit_Dwarf());
        unitDeck.add(new Unit_Huskarl());
        unitDeck.add(new Unit_Frost_Giant());
        randDeck.add(new G_Forseti());
        randDeck.add(new G_Loki());
        randDeck.add(new G_Odin());
        randDeck.add(new G_NJord());
        randDeck.add(new G_Baldr());
        randDeck.add(new G_Thor());
        randDeck.add(new G_Freyia());
        randDeck.add(new G_Skadi());
        randDeck.add(new G_Hel());
        randDeck.add(new G_Bragi());
        randDeck.add(new G_Heimdall());
        randDeck.add(new G_Tyr());
        randDeck.add(new R_Attack(c, 5));
        randDeck.add(new R_Attack(c, 5));
        randDeck.add(new R_Attack(c, 6));
        randDeck.add(new R_Attack(c, 7));
        randDeck.add(new R_Attack(c, 7));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_Trade(c));
        randDeck.add(new R_NextAge(c));
        randDeck.add(new R_NextAge(c));
        randDeck.add(new R_NextAge(c));
        randDeck.add(new R_Build(c,2));
        randDeck.add(new R_Build(c,2));
        randDeck.add(new R_Build(c,3));
        randDeck.add(new R_Build(c,3));
        randDeck.add(new R_Build(c,3));
        randDeck.add(new R_Build(c,4));
        randDeck.add(new R_Explore(c,0));
        randDeck.add(new R_Explore(c,0));
        randDeck.add(new R_Explore(c,1));
        randDeck.add(new R_Explore(c,2));
        randDeck.add(new R_Explore(c,2));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Gather(c));
        randDeck.add(new R_Recruit(c,3));
        randDeck.add(new R_Recruit(c,3));
        randDeck.add(new R_Recruit(c,4));
        randDeck.add(new R_Recruit(c,4));
        randDeck.add(new R_Recruit(c,5));
        randDeck.add(new R_Recruit(c,5));
    }
}
