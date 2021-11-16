import java.util.*;

public class MAHGamePlayer {
    private List<Hero> team;
    private Scanner scanner = MainScanner.getSingleInstance().getScanner();

    public MAHGamePlayer() {
        team = new ArrayList<>();
    }

    public void addHeroToTeam(Hero h, Position position) {
        System.out.println("Hero " + h.getName() + " is now your teammate!");
        team.add(h);
        h.setPosition(position);
    }

    public int getTeamSize(){
        return team.size();
    }

    public List<Hero> getTeam() {
        return team;
    }

//    public void recordState() {
//        for(Hero h : team) {
//            h.recordState();
//        }
//    }

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
