/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeoflegends;

/**
 *
 * meaning of all the constants used in code
 * @author JAY
 */
public class Constants {
    // production tile
        // resource names
    protected final static int food = 1;   // green
    protected final static int favor = 2;  // blue
    protected final static int wood = 3;   // brown
    protected final static int gold = 4;   // yellow
    protected final static int vc = 5;   // red
        
        // cube images
    protected final static String foodimg = "cubes/food.PNG";   // green
    protected final static String favorimg = "cubes/favor.PNG";  // blue
    protected final static String woodimg = "cubes/wood.PNG";   // brown
    protected final static String goldimg = "cubes/gold.PNG";   // yellow
    protected final static String vcimg = "cubes/vc.PNG";   // yellow
    
        //  tile type name
    protected final static int fertile = 5;
    protected final static int forest = 6;
    protected final static int hills = 7;
    protected final static int mountains = 8;
    protected final static int desert = 9;
    protected final static int swamp = 10;
    
    // culture
        // 0 is for no culture selected by player
    protected final static int egyptian = 1;
    protected final static int greek = 2;
    protected final static int norse = 3;
    
    //borad areas
    protected final static int holding = 0;
    protected final static int city = 1;
    protected final static int production = 2;

    // player age
    protected final static int archaicAge = 4;
    protected final static int classicalAge = 5;
    protected final static int heroicAge = 6;
    protected final static int mythicAge = 7;
    
    // board images
    protected final static String greekBoard = "src/images/greek_board.png";
    protected final static String egyptBoard = "src/images/egypt_board.png";
    protected final static String norseBoard = "src/images/norse_board.png";
    
    //  image folder
    protected final static String IF = "src/images/";
    protected final static String circleImg = "src/images/circle.png";
    protected final static String buildingImg = "src/images/building-tiles/";
    
//    // card type
//    protected final static int victoryCard = 1;
//    protected final static int randomActionCard = 2;
//    protected final static int permanantActionCard = 3;
    
    // action of card
    protected final static int attack = 1;
    protected final static int build = 2;
    protected final static int explore = 3;
    protected final static int gather = 4;
    protected final static int nextAge = 5;
    protected final static int recruit = 6;
    protected final static int trade = 7;
    
    // victory card types
    protected final static int mostBuildings = 1;
    protected final static int largestArmy = 2;
    protected final static int wonLastBattle = 3;
//    protected final static int builtwonder = 4;
 
    // cost type
    protected final static int any = 1;
    
    // rewards of card
    protected final static int resourceOrTerrain = 1;
    protected final static int drawMoreTileThanPlayers = 2;
    protected final static int ageDependent = 3;
    
    // building types
    protected final static int woodWorkshop = 1;
    protected final static int market = 2;
    protected final static int house = 3;
    protected final static int wonder = 4;
    protected final static int granary = 5;
    protected final static int storehouse = 6;
    protected final static int siegeEngineWorkshop = 7;
    protected final static int greatTemple = 8;
    protected final static int armory = 9;
    protected final static int wall = 10;
    protected final static int monument = 11;
    protected final static int goldMint = 12;
    protected final static int quarry = 13;
    protected final static int tower = 14;
    
    // unit types
    protected final static int mortal = 0;
    protected final static int myth = 1;
    protected final static int hero = 2;
    protected final static int cavalry = 3;
    protected final static int giant = 4;
    protected final static int giantKiller = 5;
    protected final static int archer = 6;
    protected final static int warrior = 7;
    protected final static int flyer = 8;
    
    //unit battle abilities
    protected final static int winsDraws = 0;
    protected final static int loosesDraws = 1;
    protected final static int forcedRetreat = 2;
    protected final static int throwUnit = 3;
    protected final static int destroy2Buildings = 4;
    protected final static int ignoresWallsAndTowers = 5;
    protected static final int switchWithWadjet = 6;
    
    // random action face down image location
    protected static String randomFaceDown = IF+"randomfacedown.png";
    static String gameImage = IF+"gameImage.png";
}