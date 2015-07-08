/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Eric
 */
public abstract class Attack extends Card{
    Random r = new Random();
    protected Player attacker;
    protected Player target;
    protected int targetBoard;
    protected int aAllowedUnits;
    protected ArrayList<Unit> attackers;
    protected int dAllowedUnits;
    protected ArrayList<Unit> defenders;
    protected boolean attackLost;
    protected boolean defenseLost;
    protected Unit attackUnit;
    protected Unit defendUnit;
    
    public Attack(int c, int type, String _name, int a, int d){
        super(type, _name, c);
        aAllowedUnits = a;
        dAllowedUnits = d;
    }

    @Override
    public abstract boolean play();
//    {
//        getTargetPlayer();
//        getTargetBoard();
//        attackerChoosesSquad();
//        defenderChoosesSquad();
//        alertPlayer();
//        newBattle();
//        battle();
//        afterBattle();
//        returnUnits();
//        return true;
//    }
    
    protected void init(){
        attacker = PlayerHolder.getCurrentPlayer();
    }
    
    protected void getTargetPlayer(){
        final int currentPos = PlayerHolder.getCurrentPlayerPosition();
        final int nextPos = (currentPos+1)%PlayerHolder.size();
        final int prevPos = (currentPos+PlayerHolder.size()-1)%PlayerHolder.size();
        int response;
        if(attacker.isHuman()){
            String[] options = new String[]{PlayerHolder.getPlayer(nextPos).getCultureName(),PlayerHolder.getPlayer(prevPos).getCultureName()};
            response = JOptionPane.showOptionDialog(null, "Who do you want to attack?",
                    "Choose Target", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
        }else{
            response = r.nextInt(2);
        }
        final int targetPos = (currentPos+(response==0?1:PlayerHolder.size()-1))%PlayerHolder.size();
        target = PlayerHolder.getPlayer(targetPos);
    }
    
    protected void getTargetBoard(){
        int response;
        if(attacker.isHuman()){
            String[] options = new String[]{"Holding","Cty","Production"};
            response = JOptionPane.showOptionDialog(null, "Where do you want to attack?",
                    "Choose Target", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
        }else{
            response = r.nextInt(3);
        }
        targetBoard = response;
        System.out.println(response);

        if(target.isHuman()){
            JOptionPane.showMessageDialog(null, "You're being attacked by " + attacker.getCultureName()
                    + "! They're attacking your " + (response==0?"Holding Area":response==1?"City Area":"Production Area"));
        }else{
            System.out.println(""+target.getCultureName()+" is attacked by "+ attacker.getCultureName());
        }
    }
    
    protected void attackerChoosesSquad(){
        if(attacker.getArmy().getSize()>0){
            SelectUnit su = new SelectUnit(attacker.getArmy().getUnits(), aAllowedUnits+(attacker.getBoard().getCityArea().find(Constants.armory)==-1?0:1), attacker.isHuman(), true);
            while(!su.isSelected()){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            attackers = su.getChoice();
            su.dispose();
        }else{
            attackers = new ArrayList();
        }
    }
    
    protected void defenderChoosesSquad(){
        if(target.getArmy().getSize()>0){
            SelectUnit su = new SelectUnit(target.getArmy().getUnits(), dAllowedUnits+(target.getBoard().getCityArea().find(Constants.armory)==-1?0:1), target.isHuman(), true);
            while(!su.isSelected()){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            defenders = su.getChoice();
            su.dispose();
        }else{
            defenders = new ArrayList();
        }
    }
    
    protected void alertPlayer(){
        if(attacker.isHuman()){
            SelectUnit su = new SelectUnit(defenders, 0, true, false);
            while(!su.isSelected()){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            su.dispose();
        }else if(target.isHuman()){
            SelectUnit su = new SelectUnit(attackers, 0, true, false);
            while(!su.isSelected()){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            su.dispose();
        }
    }
    
    protected void newBattle(){
        for(Unit u: attackers){
            u.newBattle();
        }
        for(Unit u: defenders){
            u.newBattle();
        }
        attackLost = attackers.isEmpty();
        defenseLost = defenders.isEmpty();
    }
    
    protected void battle(){
        while(!attackLost && !defenseLost){
            if(offerSurrender())
                break;
            pickUnitsForBattle();
            if(attacker.isHuman()){
                JOptionPane.showMessageDialog(null, defendUnit.getImage(), "Defending Unit", JOptionPane.PLAIN_MESSAGE);
            }else if(target.isHuman()){
                JOptionPane.showMessageDialog(null, attackUnit.getImage(), "Attacking Unit", JOptionPane.PLAIN_MESSAGE);
            }
            offerWadjetSwitch();
            
            attackUnit.preFight(attacker, defendUnit);//berzerk and throw
            defendUnit.preFight(target, attackUnit);
            boolean[] defBuildings = new boolean[]{target.getBoard().getCityArea().find(Constants.wall)!=-1, target.getBoard().getCityArea().find(Constants.tower)!=-1};
            boolean ignore = (attacker.getBoard().getCityArea().find(Constants.siegeEngineWorkshop)!=-1||attackUnit.getAbility(Constants.ignoresWallsAndTowers));
            int a6 = 0;
            int d6 = 0;
            while(a6==d6){//keeps rolling until there isn't a tie
                a6 = attackUnit.attack(defendUnit, true, targetBoard, defBuildings, ignore);
                d6 = defendUnit.attack(attackUnit, false, targetBoard, defBuildings, ignore);
                String result = "It was a tie. Rerolling.";
                if(a6==d6){//only if there is a tie and there is an ability that influences the outcome
                    if(attackUnit.getAbility(Constants.winsDraws)){
                        a6++;
                        result = "It was a tie, but "+attacker.getCultureName()+"'s unit wins draws.";
                    }else if(attackUnit.getAbility(Constants.loosesDraws)){
                        a6--;
                        result = "It was a tie, but "+attacker.getCultureName()+"'s unit losses draws.";
                    }else if(defendUnit.getAbility(Constants.winsDraws)){
                        d6++;
                        result = "It was a tie, but "+target.getCultureName()+"'s unit wins draws.";
                    }else if(defendUnit.getAbility(Constants.loosesDraws)){
                        d6--;
                        result = "It was a tie, but "+target.getCultureName()+"'s unit losses draws.";
                    }
                }else{
                    result = attacker.getCultureName()+"'s unit rolled "+a6+" sixes.\n"
                            +target.getCultureName()+"'s unit rolled "+d6+" sixes.";
                }
                if(attacker.isHuman()||target.isHuman()){
                    JOptionPane.showMessageDialog(null, result, "Fight Results", JOptionPane.PLAIN_MESSAGE);
                }

            }
            postFight(a6, d6);
            attackLost = attackers.isEmpty();
            defenseLost = defenders.isEmpty();
        }//end while (one side lost or surrendered)
    }
    
    protected boolean offerSurrender(){
        if(offerAttackSurrender()){
            return true;
        }
        return offerDefenseSurrender();
    }
    
    private boolean offerAttackSurrender(){
        if(attacker.isHuman()){
            int response = JOptionPane.showConfirmDialog(null, "Do you want to surrender?", "Surrender", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response == JOptionPane.YES_OPTION){
                attackLost = true;
                return true;
            }
        }
        return false;
    }
    
    private boolean offerDefenseSurrender(){
        if(target.isHuman()){
            int response = JOptionPane.showConfirmDialog(null, "Do you want to surrender?", "Surrender", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(response == JOptionPane.YES_OPTION){
                defenseLost = true;
                return true;
            }
        }
        return false;
    }
    
    protected void pickUnitsForBattle(){
        pickAttackUnitForBattle();
        pickDefenseUnitForBattle();
    }
    
    private void pickAttackUnitForBattle(){
        SelectUnit su = new SelectUnit(attackers, 1, attacker.isHuman(), true);
        while(!su.isSelected()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(su.getChoice().get(0)!=null){
            attackUnit = su.getChoice().remove(0);
        }
        su.dispose();
    }
    
    private void pickDefenseUnitForBattle(){
        SelectUnit su = new SelectUnit(defenders, 1, target.isHuman(), true);
        while(!su.isSelected()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(su.getChoice().get(0)!=null){
            defendUnit = su.getChoice().remove(0);
        }
        su.dispose();
    }
    
    protected void offerWadjetSwitch(){
        offerAttackWagjetSwitch();
        offerDefenseWagjetSwitch();
    }
    
    private void offerAttackWagjetSwitch(){
        if(attackUnit.getAbility(Constants.switchWithWadjet)){
            int wadjetPos = -1;
            for(int i = 0; i < attackers.size(); i++){
                if(attackers.get(i).getName().equals("Unit_Wadjet")){
                    wadjetPos = i;
                }
            }
            if(wadjetPos!=-1){
                if(attacker.isHuman()){
                    int response = JOptionPane.showConfirmDialog(null, "Do you switch with the wadget?", "Switch", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(response == JOptionPane.YES_OPTION){
                        attackers.add(attackUnit);
                        attackUnit = attackers.remove(wadjetPos);
                    }
                }else{
                    if(r.nextBoolean()){
                        attackers.add(attackUnit);
                        attackUnit = attackers.remove(wadjetPos);
                        if(target.isHuman()){
                            JOptionPane.showMessageDialog(null, attackUnit.getImage(), "New Attacking Unit", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
        }
    }
    private void offerDefenseWagjetSwitch(){
        if(defendUnit.getAbility(Constants.switchWithWadjet)){
            int wadjetPos = -1;
            for(int i = 0; i < defenders.size(); i++){
                if(defenders.get(i).getName().equals("Unit_Wadjet")){
                    wadjetPos = i;
                }
            }
            if(wadjetPos!=-1){
                if(target.isHuman()){
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to switch with the wadget?", "Switch", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(response == JOptionPane.YES_OPTION){
                        defenders.add(defendUnit);
                        defendUnit = defenders.remove(wadjetPos);
                    }
                }else{
                    if(r.nextBoolean()){
                        defenders.add(defendUnit);
                        defendUnit = defenders.remove(wadjetPos);
                        if(attacker.isHuman()){
                            JOptionPane.showMessageDialog(null, defendUnit.getImage(), "New Attacking Unit", JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
        }
    }
    
    protected void postFight(int a6, int d6){
        if(a6>d6){
            if(attackUnit.getAbility(Constants.throwUnit)){//attacker won and throws unit
                attackUnit.onVictory(attacker, attackers);
                attackers.add(attackUnit);
                target.getArmy().addUnit(defendUnit);
            }else if(attackUnit.getAbility(Constants.forcedRetreat)&&defendUnit.getType(0)==Constants.hero){//attacker won, forces a retreat, and fought a hero
                attackUnit.onVictory(attacker, attackers);
                attackers.add(attackUnit);
                if(defendUnit.onDefeat(target, defenders))
                    target.getDeck().addUnit(defendUnit);
                while(!defenders.isEmpty()){
                    target.getArmy().addUnit(defenders.remove(0));
                }
            }else{//attacker won no special ability
                attackUnit.onVictory(attacker, attackers);
                attackers.add(attackUnit);
                if(defendUnit.onDefeat(target, defenders))
                    target.getDeck().addUnit(defendUnit);
            }
        }else if(d6>a6){//same as above but defender won
            if(defendUnit.getAbility(Constants.throwUnit)){
                defendUnit.onVictory(target, defenders);
                defenders.add(defendUnit);
                attacker.getArmy().addUnit(attackUnit);
            }else if(defendUnit.getAbility(Constants.forcedRetreat)&&attackUnit.getType(0)==Constants.hero){
                defendUnit.onVictory(target, defenders);
                defenders.add(defendUnit);
                if(attackUnit.onDefeat(target, attackers))
                    attacker.getDeck().addUnit(attackUnit);
                while(!attackers.isEmpty()){
                    attacker.getArmy().addUnit(attackers.remove(0));
                }
            }else{
                defendUnit.onVictory(target, defenders);
                defenders.add(defendUnit);
                if(attackUnit.onDefeat(attacker, attackers))
                    attacker.getDeck().addUnit(attackUnit);
            }
        }
    }
    
    protected void getAtackUnit(){
        SelectUnit su = new SelectUnit(attackers, 1, attacker.isHuman(), true);
        while(!su.isSelected()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        attackUnit = su.getChoice().remove(0);
        su.dispose();
    }
    
    protected void getDefenseUnit(){
        SelectUnit su = new SelectUnit(defenders, 1, target.isHuman(), true);
        while(!su.isSelected()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Attack.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        defendUnit = su.getChoice().remove(0);
        su.dispose();
    }
    
    protected void afterBattle(){
        if(defenseLost && !attackLost){
            switch (targetBoard){
                case Constants.production:{
                    StealProduction sp = new StealProduction(attacker, target);
                    Production_Tile t = sp.getTile();
                    System.out.println("Selected: "+t.getTypeName());
                    sp.dispose();
                    if(!attacker.getBoard().getProductionArea().add(t)){
                        ArrayList<Production_Tile> tile = new ArrayList();
                        tile.add(t);
                        Production_Tile_Pool.putBackTiles(tile);
                    }
                    break;
                }
                case Constants.holding:{
                    int[] results;
                    if(attacker.isHuman()){
                        StealResources sr = new StealResources(attacker, target, 5);
                        results = sr.getResult();
                        sr.dispose();
                    }else{
                        results = new int[]{0,0,0,0};
                        for(int i = 0; i < 5; i++){
                            results[r.nextInt(4)]++;
                        }
                    }
                    attacker.getResource().add(results);
                    break;
                }
                case Constants.city:{
                    int destroy = 1;
                    if(attacker.getBoard().getCityArea().find(Constants.siegeEngineWorkshop)!=-1)
                        destroy = 2;
                    for (Unit attacker1 : attackers) {
                        if(attacker1.getAbility(Constants.destroy2Buildings))
                            destroy = 2;
                    }
                    DestroyBuildings db = new DestroyBuildings(attacker, target, destroy);
                    db.dispose();
                    break;
                }
            }
        }
    }
    
    protected void returnUnits(){
        while(!attackers.isEmpty()){
            attacker.getArmy().addUnit(attackers.remove(0));
        }
        while(!defenders.isEmpty()){
            target.getArmy().addUnit(defenders.remove(0));
        }
    }
    
    protected void awardVictoryCubes(){
        if(attackLost){
            awardCubesToPlayer(target);
        }else{
            awardCubesToPlayer(attacker);
        }
    }
    
    private void awardCubesToPlayer(Player p){
        int reward = VictoryCards.getCard(Constants.wonLastBattle).awardCubes();
        System.out.println(reward);
        p.getResource().addVictoryCubes(reward);
    }
}
