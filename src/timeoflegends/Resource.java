
package timeoflegends;

/**
 *
 * @author JAY
 */
public class Resource {
    private int numOfFood;
    private int numOfFavor;
    private int numOfWood;
    private int numOfGold;
    private int numOfVictoryCubes;

    public Resource(int numOfFood, int numOfFavor, int numOfWood, int numOfGold, int numOfVictoryCubes) {
        this.numOfFood = numOfFood;
        this.numOfFavor = numOfFavor;
        this.numOfWood = numOfWood;
        this.numOfGold = numOfGold;
        this.numOfVictoryCubes = numOfVictoryCubes;
    }
    
    public int getNumOfFood() {
        return numOfFood;
    }
    
    public void addFood(int n){
        numOfFood+=n;
    }
    
    public int removeFood(int n){
        int ret = n;
        if(n>numOfFood){
            ret = numOfFood;
            numOfFood = 0;
        }else{
            numOfFood-=n;
        }
        return ret;
    }

    public int getNumOfFavor() {
        return numOfFavor;
    }
    
    public void addFavor(int n){
        numOfFavor+=n;
    }
    public void add(int restype, int num){
        switch(restype){
            case Constants.food:{
                addFood(num);
                break;
            }case Constants.favor:{
                addFavor(num);
                break;
            }case Constants.wood:{
                addWood(num);
                break;
            }case Constants.gold:{
                addGold(num);
                break;
            }default:{      // victory cubes
                addVictoryCubes(num);
                break;
            }
        }
    }
    public int draw(int restype, int num){
        switch(restype){
            case Constants.food:{
                return removeFood(num);
            }case Constants.favor:{
                return removeFavor(num);
            }case Constants.wood:{
                return removeWood(num);
            }case Constants.gold:{
                return removeGold(num);
            }default:{      // victory cubes
                return removeVictoryCube(num);        // atmost 1
            }
        }
    }

    /**
     *  returns number of resources of 'resType
     * @param resType 0 = returns total number of resources
     * @return
     */
    public int getNumOfResource(int resType){
        switch(resType){
            case Constants.food:{
                return numOfFood;
            }
            case Constants.favor:{
                return numOfFavor;
            }
            case Constants.wood:{
                return numOfWood;
            }
            case Constants.gold:{
                return numOfGold;
            }
            case 0:
                return (numOfFood+numOfFavor+numOfWood+numOfGold);
            default:{
                return numOfVictoryCubes;
            }
        }
    }
    public int removeFavor(int n){
        int ret = n;
        if(n>numOfFavor){
            ret = numOfFavor;
            numOfFavor = 0;
        }else{
            numOfFavor-=n;
        }
        return ret;
    }

    public int getNumOfWood() {
        return numOfWood;
    }
    
    public void addWood(int n){
        numOfWood+=n;
    }
    
    public int removeWood(int n){
        int ret = n;
        if(n>numOfWood){
            ret = numOfWood;
            numOfWood = 0;
        }else{
            numOfWood-=n;
        }
        return ret;
    }

    public int getNumOfGold() {
        return numOfGold;
    }
    
    public void addGold(int n){
        numOfGold+=n;
    }
    
    public int removeGold(int n){
        int ret = n;
        if(n>numOfGold){
            ret = numOfGold;
            numOfGold = 0;
        }else{
            numOfGold-=n;
        }
        return ret;
    }

    public int getNumOfVictoryCubes() {
        return numOfVictoryCubes;
    }
    
    public void addVictoryCubes(int n){
        numOfVictoryCubes+=n;
    }
    
    public int removeVictoryCube(int n){
        //return (numOfVictoryCubes>0)?numOfVictoryCubes--:0;
                int ret = n;
        if(n>numOfVictoryCubes){
            ret = numOfVictoryCubes;
            numOfVictoryCubes = 0;
        }else{
            numOfVictoryCubes-=n;
        }
        return ret;
    }

    public int[] draw(int[] cost) {
        int[] ret = new int[]{0,0,0,0};
        ret[0] = removeFood(cost[0]);
        ret[1] = removeFavor(cost[1]);
        ret[2] = removeWood(cost[2]);
        ret[3] = removeGold(cost[3]);
        return ret;
    }
    
    public boolean checkCost(int[] cost) {
        Resource res = PlayerHolder.getCurrentPlayer().getResource();
        return res.getNumOfFood()>=cost[0] &&
                res.getNumOfFavor()>=cost[1] &&
                res.getNumOfWood()>=cost[2] &&
                res.getNumOfGold()>=cost[3];
    }

    public void add(int[] cost) {
        addFood(cost[0]);
        addFavor(cost[1]);
        addWood(cost[2]);
        addGold(cost[3]);
    }
}
