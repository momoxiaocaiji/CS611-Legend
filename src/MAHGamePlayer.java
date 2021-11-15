import java.util.*;

public class MAHGamePlayer {
    private Map<Hero, Position> team;
    private Scanner scanner = MainScanner.getSingleInstance().getScanner();

    public MAHGamePlayer() {
        team = new HashMap<>();
    }

    public void addHeroToTeam(Hero h, Position position) {
        System.out.println("Hero " + h.getName() + " is now your teammate!");
        team.put(h, position);
    }

    public int getTeamSize(){
        return team.size();
    }

    public Map<Hero, Position> getTeam() {
        return team;
    }

//    public void recordState() {
//        for(Hero h : team) {
//            h.recordState();
//        }
//    }

    public int getTheHighestLevel() {
        int highest = 0;
        for(Hero h : team.keySet()){
            if (h.getLevel() >= highest) {
                highest = h.getLevel();
            }
        }
        return highest;
    }

    // display the information of the team
    public void show() {
        for (Hero h: team.keySet()){
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
        for (Hero h: team.keySet()){
            h.checkEquipment();


            h.checkInventoryAndChangeEquipment();

            // recalculate the ability after change your equipment
            h.recalDamageAndDef();
        }
    }
}
