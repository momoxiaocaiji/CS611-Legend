import java.util.Random;

public class PlainTile extends ValorTile implements Tile{

    public PlainTile(){}

    @Override
    public boolean trigger(Hero hero){
        //init the extra attributes
        hero.setExtraDexterity(0);
        hero.setExtraStrength(0);
        hero.setExtraAgility(0);
       /* Random random = new Random();
        // means the hero will meet a fight with 70%
        if(random.nextInt(10) < 3) {
            System.out.println("Oh! Nothing happened!");
        } else {
            // start a fight
            hero.recordState();
            FightSystem fightSystem = new ParticipateBasedFightSystem(hero, new Monster(), true);
            System.out.println("start a fight");
            System.out.println("----------------------------------");
            fightSystem.startAFight();
        }*/
        return true;
    }

    public String toString() {
        return "P - P - P  ";
    }
}
