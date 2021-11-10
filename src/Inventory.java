import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private Map<String, List<Item>> itemList;

    public Inventory() {
        itemList = new HashMap<>();

        // Weapon list
        itemList.put("Weapon" , new ArrayList<>());
        // Armor list
        itemList.put("Armor" , new ArrayList<>());
        // Potion list
        itemList.put("Potion" , new ArrayList<>());
    }

    public void add(String type, Item good){
        // put the good in right cat
        itemList.get(type).add(good);
    }

    public void show(){
        for(String type: itemList.keySet()){
            System.out.println(type + " :");
            for(int i = 0 ; i < itemList.get(type).size() ; i ++){
                System.out.println("    " + (i+1) + ". " + itemList.get(type).get(i));
            }
        }
    }

    //
    public Item removeItem(String type, int index){
        return itemList.get(type).remove(index);
    }

    public void removeItem(String type, Item i){
        itemList.get(type).remove(i);
    }

    public List<Item> getPotion() {
        return itemList.get("Potion");
    }

    public List<Item> getWeaponAndArmor() {
        List<Item> temp = new ArrayList<>();
        temp.addAll(itemList.get("Weapon"));
        temp.addAll(itemList.get("Armor"));
        return temp;
    }
}
