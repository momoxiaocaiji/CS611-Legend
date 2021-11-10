public class Monster extends Character{
    protected int baseDamage;
    protected int defence;
    protected int dodgeChance;

    public Monster () {
        this("Natsunomeryu" , 1 , 100 , 200 , 10);
    }

    public Monster(String name, int level, int baseDamage, int defence, int dodgeChance){
        this.name = name;
        this.level = level;
        this.baseDamage = baseDamage;
        this.defence = defence;
        this.dodgeChance = dodgeChance;
        this.HP = this.level * Constant.HP_FACTOR;
    }

    @Override
    public String toString() {
        return "Monster{" +
                (status == Constant.NORMAL ? "" : "(dead)") +
                "name='" + name + '\'' +
                ", level=" + level +
                ", HP=" + HP +
                ", baseDamage=" + baseDamage +
                ", defence=" + defence +
                ", dodgeChance=" + dodgeChance + "%" +
                '}';
    }

    @Override
    public void getHurt(int damage){
        super.getHurt(damage);
        if(HP == 0) {
            status = Constant.DEAD;
        }
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(int dodgeChance) {
        this.dodgeChance = dodgeChance;
    }
}
