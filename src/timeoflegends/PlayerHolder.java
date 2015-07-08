/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

import java.util.ArrayList;

/**
 *
 * @author Eric
 */
public class PlayerHolder {
    private static final ArrayList<Player> players = new ArrayList();
    private static int currentPlayer = 0;
    private static int humanPlayer;

    public static int getHumanPlayerPosition() {
        return humanPlayer;
    }

    public static Player getHumanPlayer() {
        return players.get(humanPlayer);
    }
    
    public static void addPlayer(Player p){
        if(p.isHuman()) { humanPlayer = players.size(); }
        players.add(p);
    }
    
    public static Player getPlayer(int x){
        return players.get(x);
    }
    
    public static void cycle(){
        players.add(players.remove(0));
        humanPlayer = (humanPlayer + 2)%3;
        currentPlayer = 0;
    }
    
    public static int size(){
        return players.size();
    }
    
    public static void nextPlayer(){
        currentPlayer = (currentPlayer+1)%size();
    }
    
    public static Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }
    
    public static int getCurrentPlayerPosition(){
        return currentPlayer;
    }
}
