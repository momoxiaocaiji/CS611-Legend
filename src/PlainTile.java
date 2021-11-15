import java.util.Random;

public class PlainTile implements Tile{

    public PlainTile(){}

    @Override
    public boolean trigger(MAHGamePlayer player){
        Random random = new Random();
        // means the hero will meet a fight with 70%
        if(random.nextInt(10) < 3) {
            System.out.println("Oh! Nothing happened!");
        } else {
            // start a fight
            player.recordState();
            FightSystem fightSystem = new TurnBasedFightSystem(player);
            System.out.println("start a fight");
            System.out.println("----------------------------------");
            fightSystem.startAFight();
        }
        return true;
    }

    public String toString() {
        return "P - P - P  ";
    }
}
