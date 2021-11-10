import java.util.*;

public class MainMarket implements Market{

    // Singleton Pattern
    private static MainMarket mainMarketInstance;
    private Scanner scanner;
    private Hero guest;

    private Map<String, List<Item>> itemList;

    private MainMarket(){
        scanner = MainScanner.getSingleInstance().getScanner();
        initItemList();
    }

    public static MainMarket getSingleInstance() {
        if (mainMarketInstance == null)
            mainMarketInstance = new MainMarket();
        return mainMarketInstance;
    }


    @Override
    public boolean makeADeal(Item good) {
        if(guest.canBuy(good)){
            // generator a new same good

            if (good instanceof Weapon) {
                guest.addToInventory("Weapon", good.copy());
            } else if (good instanceof Armor){
                guest.addToInventory("Armor", good.copy());
            } else if (good instanceof Potion){
                guest.addToInventory("Potion", good.copy());
            } else if (good instanceof Spell){
                guest.learnSpell((Spell) good.copy());
            }
            return true;
        }
        return false;
    }

    @Override
    public void showTheList() {
        System.out.println("Welcome to the market");
        System.out.println("Choose the catalog:");

        while (true) {
            System.out.println("1. Weapon");
            System.out.println("2. Armor");
            System.out.println("3. Potion");
            System.out.println("4. Spell");
            System.out.println("5. Return to the previous menu");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    showCatDetail(itemList.get("Weapon"));
                    break;
                case 2:
                    showCatDetail(itemList.get("Armor"));
                    break;
                case 3:
                    showCatDetail(itemList.get("Potion"));
                    break;
                case 4:
                    showCatDetail(itemList.get("Spell"));
                    break;
                default:
                    // reset the guest
                    return;
            }
        }

    }

    @Override
    public void setGuest(Character guest){
        this.guest = (Hero) guest;
    }

    private void initItemList(){
        itemList = new HashMap<>();

        // Weapon
        itemList.put("Weapon" , Weapon.createWeapon(Parser.parse("Weapon")));

        //Armor
        itemList.put("Armor" , Armor.createArmor(Parser.parse("Armor")));

        //Spell
        itemList.put("Spell" , FireSpell.createSpell(Parser.parse("FireSpell")));
        itemList.get("Spell").addAll(IceSpell.createSpell(Parser.parse("IceSpell")));
        itemList.get("Spell").addAll(LightningSpell.createSpell(Parser.parse("LightningSpell")));

        //Potion
        itemList.put("Potion" , Potion.createPotion(Parser.parse("Potion")));

    }

    private void showCatDetail(List<Item> subMenu){
        for(int i = 0; i < subMenu.size(); i++){
            System.out.println( (i+1) + ". " + subMenu.get(i));
        }
        System.out.println( (subMenu.size()+1) + ". Return to the previous menu");
        System.out.println("Choose the good:");
        int orderNum = scanner.nextInt();
        while (orderNum < subMenu.size()+1){
            boolean purchaseResult = makeADeal(subMenu.get(orderNum-1));
            if (purchaseResult) {
                // success
                System.out.println("Item will be put in your hero's inventory.");
            } else {
                // false
                System.out.println("You can't buy this good.");
            }
            System.out.println("You can do another purchase or return to the previous menu");
            for(int i = 0; i < subMenu.size(); i++){
                System.out.println( (i+1) + ". " + subMenu.get(i));
            }
            System.out.println( (subMenu.size()+1) + ". Return to the previous menu");
            System.out.println("Choose the good:");
            orderNum = scanner.nextInt();
        }
    }
}
