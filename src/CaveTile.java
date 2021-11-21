public class CaveTile extends ValorTile implements Tile{
    public CaveTile() {}

    public boolean trigger(Hero hero) {
        hero.setExtraDexterity(0);
        hero.setExtraStrength(0);
        hero.setExtraAgility(hero.skills.get("agility")*0.1);
        System.out.println("Your agility has increased:" + hero.getExtraAgility());
        return true;
    }

    public String toString() {
        return "C - C - C  ";
    }
}
