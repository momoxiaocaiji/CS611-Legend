import java.util.ArrayList;
import java.util.List;

public class Weapon extends Item{
    protected int damage;
    protected int required_hands;

    public Weapon(String name, int price, int minLevelLimit, int damage, int required_hands){
        super(name, price, minLevelLimit);
        this.damage = damage;
        this.required_hands = required_hands;
    }

    public Weapon(){
        this("Sword", 500, 1 , 800 , 1);
    }

    public static List<Item> createWeapon(List<String> data) {
        List<Item> weapons = new ArrayList<>();
        for(String line: data){
            if(!line.isEmpty()){
                String[] detail = line.split("\\s+");
                if (detail.length != 5){
                    System.out.println("Wrong config!");
                    continue;
                }
                String name = detail[0];
                int price = Integer.parseInt(detail[1]);
                int minLevelLimit = Integer.parseInt(detail[2]);
                int damage = Integer.parseInt(detail[3]);
                int required_hands = Integer.parseInt(detail[4]);
                weapons.add(new Weapon(name , price , minLevelLimit , damage , required_hands));
            }
        }
        return weapons;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", minLevelLimit=" + minLevelLimit +
                ", damage=" + damage +
                ", required_hands=" + required_hands +
                '}';
    }

    public int getRequired_hands() {
        return required_hands;
    }

    public int getDamage(){
        return damage;
    }

    @Override
    public Item copy(){
        return new Weapon(name, price, minLevelLimit, damage, required_hands);
    }

    @Override
    public boolean use(Hero hero){
        Equipment equipment = hero.getEquipment();
        equipment.equipWeapon(this, hero.getInventory());
        return true;
    }
}
