public abstract class ValorTile implements Tile{
    protected Hero hero;
    protected Monster monster;

    public ValorTile(){
        hero = null;
        monster = null;
    }

    @Override
    public boolean trigger(MAHGamePlayer player){return true;}

    // in valor, hero moves alone
    public abstract boolean trigger(Hero hero);

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Hero getHero() {
        return hero;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }
}
