
package timeoflegends;

/**
 * singleton class
 * 
 * @author JAY
 */
public class Bank {
    private static Bank bank = null;
    private static Resource resource;

    protected static Resource getResource() {
        return bank.resource;
    }

    protected static void setResource(Resource resource) {
        bank.resource = resource;
    }
    public static Bank getInstance(){
        if(bank==null){
            bank = new Bank(PlayerHolder.size());
        }
        return bank;
    }
    protected Bank(int players) {
        int num;
        players = PlayerHolder.size();
        switch(players){
            case 3:
                num = 13;
                break;
            case 4:
                num = 14;
                break;
            case 5:
                num = 20;
                break;
            case 6:
                num = 26;
                break;
            case 7:
                num = 27;
                break;
            case 8:
                num = 28;
                break;
            default:    // 2 players
                num = 12;
                break;
        }
        resource = new Resource(num, num, num, num, 30);
    }
}
