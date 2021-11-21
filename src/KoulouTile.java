public class KoulouTile extends ValorTile implements Tile{
    public KoulouTile() {}

    public boolean trigger(Hero hero) {
        hero.setExtraDexterity(0);
        hero.setExtraAgility(0);
        hero.setExtraStrength(hero.skills.get("strength")*0.1);
        System.out.println("Your strength has increased:" + hero.getExtraStrength());
        return true;
    }

    public String toString() {
        return "K - K - K  ";
    }
}
