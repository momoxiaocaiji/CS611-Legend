public class BushTile extends ValorTile implements Tile{
    public BushTile() {}
    public boolean trigger(Hero hero) {
        hero.setExtraStrength(0);
        hero.setExtraAgility(0);
        hero.setExtraDexterity(hero.skills.get("dexterity")*0.1);
        System.out.println("Your dexterity has increased: "+hero.getExtraDexterity());
        return true;
    }

    public String toString() {
        return "B - B - B  ";
    }
}
