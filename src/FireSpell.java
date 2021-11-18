import java.util.ArrayList;
import java.util.List;

public class FireSpell extends Spell {
    public FireSpell(String name, int price, int minLevelLimit, int damage, int manaCost) {
        super(name, price, minLevelLimit, damage, manaCost);
    }

    public FireSpell() {
        this("Flame_Tornado", 700, 4, 850, 300);
    }

    public static List<Item> createSpell(List<String> data) {
        List<Item> fireSpell = new ArrayList<>();
        for (String line : data) {
            if (!line.isEmpty()) {
                String[] detail = line.split("\\s+");
                if (detail.length != 5) {
                    System.out.println("Wrong config!");
                    continue;
                }
                String name = detail[0];
                int price = Integer.parseInt(detail[1]);
                int minLevelLimit = Integer.parseInt(detail[2]);
                int damage = Integer.parseInt(detail[3]);
                int manaCost = Integer.parseInt(detail[4]);
                fireSpell.add(new FireSpell(name, price, minLevelLimit, damage, manaCost));
            }
        }
        return fireSpell;
    }

    @Override
    public boolean cast(Hero caster, Monster opposite) {
        boolean sup = super.cast(caster, opposite);
        if (sup) {
            int spellFinalDamage = (int) (damage + damage * (caster.getSkills().get("dexterity") + caster.getExtraDexterity()) / Constant.SPELL_DAMAGE_FACTOR);
            System.out.println(opposite.getName() + " took " + Constant.RED + spellFinalDamage + Constant.RESET + " damage");
            opposite.getHurt(spellFinalDamage);
            opposite.setBaseDamage((int) (opposite.getBaseDamage() * Constant.DETERIORATION_FACTOR));
        }
        return sup;
    }

    public Item copy() {
        return new FireSpell(name, price, minLevelLimit, damage, manaCost);
    }

}
