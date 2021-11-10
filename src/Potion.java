import java.util.*;

public class Potion extends Item{
    protected int attributeIncrease;

    protected List<String> attributeAffected;

    protected Scanner scanner = MainScanner.getSingleInstance().getScanner();

    public Potion(String name, int price, int minLevelLimit, int attributeIncrease, List<String> attributeAffected){
        super(name, price, minLevelLimit);

        this.attributeAffected = attributeAffected;
        this.attributeIncrease = attributeIncrease;
    }

    public Potion(){
        this("Healing_Potion", 250, 1 , 100, new ArrayList<>());
        attributeAffected.add("Health");
    }

    public static List<Item> createPotion(List<String> data) {
        List<Item> potion = new ArrayList<>();
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
                int attributeIncrease = Integer.parseInt(detail[3]);
                List<String> attributeAffected = Arrays.asList(detail[4].split("/"));
                potion.add(new Potion(name , price , minLevelLimit , attributeIncrease, attributeAffected));
            }
        }
        return potion;
    }

    @Override
    public String toString() {
        return "Potion{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", minLevelLimit=" + minLevelLimit +
                ", attributeIncrease=" + attributeIncrease +
                ", attributeAffected=" + attributeAffected +
                '}';
    }

    @Override
    public Item copy(){
        return new Potion(name, price, minLevelLimit, attributeIncrease, attributeAffected);
    }

    @Override
    public boolean use(Hero hero) {
        System.out.println(Constant.DIVIDE);
        System.out.println("Hero "+hero.getName()+ " uses " + this.name);
        for(String attribute: attributeAffected){
            if(attribute.contains("Health")){
                hero.setHP(hero.getHP() + attributeIncrease);
            } else if (attribute.contains("Mana")){
                hero.setMP(hero.getMP() + attributeIncrease);
            } else if (attribute.contains("Strength")){
                hero.increaseSkill("strength" , attributeIncrease);
            } else if (attribute.contains("Agility")){
                hero.increaseSkill("agility", attributeIncrease);
            } else if (attribute.contains("Dexterity")){
                hero.increaseSkill("dexterity", attributeIncrease);
            } else if (attribute.contains("Defense")){
                hero.setDef(hero.getDef() + attributeIncrease);
            }
        }
        hero.getInventory().removeItem("Potion", this);
        hero.recalDamageAndDef();
        System.out.println(attributeAffected + " increase " + attributeIncrease);
        System.out.println("Press C/c to leave");
        System.out.println(Constant.DIVIDE);
        String ins = scanner.next();
        while (!ins.equalsIgnoreCase("C")){
            System.out.println("Invalid instruction");
            ins = scanner.next();
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this != o) return false;
        return true;
    }
}
