import java.util.ArrayList;
import java.util.List;

public class Armor extends Item{
    protected int damageReduction;

    public Armor(){
        this("Platinum_Shield", 150 , 1 , 200);
    }

    public Armor(String name, int price, int minLevelLimit, int damageReduction){
        this.name = name;
        this.price = price;
        this.minLevelLimit = minLevelLimit;
        this.damageReduction = damageReduction;
    }

    public int getDamageReduction() {
        return damageReduction;
    }

    public static List<Item> createArmor(List<String> data) {
        List<Item> armors = new ArrayList<>();
        for(String line: data){
            if(!line.isEmpty()){
                String[] detail = line.split("\\s+");
                if (detail.length != 4){
                    System.out.println("Wrong config!");
                    continue;
                }
                String name = detail[0];
                int price = Integer.parseInt(detail[1]);
                int minLevelLimit = Integer.parseInt(detail[2]);
                int damageReduction = Integer.parseInt(detail[3]);
                armors.add(new Armor(name , price , minLevelLimit , damageReduction));
            }
        }
        return armors;
    }

    @Override
    public String toString() {
        return "Armor{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", minLevelLimit=" + minLevelLimit +
                ", damageReduction=" + damageReduction +
                '}';
    }

    @Override
    public Item copy(){
        return new Armor(name, price, minLevelLimit, damageReduction);
    }

    @Override
    public boolean use(Hero hero){
        Equipment equipment = hero.getEquipment();
        equipment.equipArmor(this, hero.getInventory());
        return true;
    }
}
