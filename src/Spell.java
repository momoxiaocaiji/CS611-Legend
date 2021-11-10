import java.util.Objects;

public class Spell extends Item implements Castable, Learnable{
    protected int damage;
    protected int manaCost;

    public Spell(String name, int price, int minLevelLimit, int damage, int manaCost){
        super(name, price, minLevelLimit);
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public Spell() {
        this("Flame_Tornado" , 700, 4 , 850 , 300);
    }

    @Override
    public String toString() {
        return "Spell{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", minLevelLimit=" + minLevelLimit +
                ", damage=" + damage +
                ", manaCost=" + manaCost +
                '}';
    }

    @Override
    public Item copy() {
        return new Spell(name, price, minLevelLimit, damage, manaCost);
    }

    @Override
    public boolean cast(Hero caster, Monster opposite){
        if(caster.getMP() < manaCost){
            System.out.println("You can't use this spell. (Req mana:"+ manaCost +" , You have:"+ caster.getMP() +" )");
            return false;
        }
        System.out.println(caster.getName() + " uses " + this.name);
        caster.setMP(caster.getMP() - manaCost);
        return true;
    }

    @Override
    public boolean canLearn(Hero hero){
        boolean re = hero.getLearnedSpell().contains(this);
        if(re){
            System.out.println(Constant.DIVIDE);
            System.out.println("Hero " + hero.getName() + " has learned this spell");
            System.out.println(Constant.DIVIDE);
        }
        return !re;
    }
}
