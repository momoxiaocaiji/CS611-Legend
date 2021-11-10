import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MAHGamePlayer {
    private List<Hero> team;
    private Position position;
    private Scanner scanner = MainScanner.getSingleInstance().getScanner();

    public MAHGamePlayer() {
        team = new ArrayList<>();
        position = new Position();
    }

    public void addHeroToTeam(Hero h) {
        System.out.println("Hero " + h.getName() + " is now your teammate!");
        team.add(h);
    }

    public Position setPosition(Position position) {
        this.position = position;
        return position;
    }

    public int getTeamSize(){
        return team.size();
    }

    public List<Hero> getTeam() {
        return team;
    }

    public void afterBattle(int exp) {
        for(Hero h : team) {
            if (h.status == Constant.FAINT){
                // hero faint in the fight
                // the hero gets revived by the other hero(es) and gets back half
                // of his/her hp but doesn’t gain any exp or money
                h.recoverFromFaint();
            } else {
                h.getTheReward(getTheHighestLevel(), exp);
            }
        }
    }

    public void recordState() {
        for(Hero h : team) {
            h.recordState();
        }
    }

    public int getTheHighestLevel() {
        int highest = 0;
        for(Hero h : team){
            if (h.getLevel() >= highest) {
                highest = h.getLevel();
            }
        }
        return highest;
    }

    // display the information of the team
    public void show() {
        for (Hero h: team){
            h.show();
        }
        System.out.println("C/c: go back to the main menu");
        String out = scanner.next();
        while (!out.equalsIgnoreCase("c")){
            System.out.println("Invalid input");
            out = scanner.next();
        }
    }

    // check the inventory and change the equipment
    public void checkInventory(){
        for (Hero h: team){
            h.checkEquipment();


            h.checkInventoryAndChangeEquipment();

            // recalculate the ability after change your equipment
            h.recalDamageAndDef();
        }
    }
}
