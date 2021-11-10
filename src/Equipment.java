import java.util.Scanner;

public class Equipment {
    private Weapon leftHand;
    private Weapon rightHand;
    private Armor body;
    private Scanner scanner = MainScanner.getSingleInstance().getScanner();

    public Equipment(){
        // init equipment
        leftHand = new Weapon();
        body = new Armor();
    }

    /**
     * equip the weapon
     * @param weapon
     * @param inventory
     */
    public void equipWeapon(Weapon weapon, Inventory inventory){
        if (weapon.getRequired_hands() == 1){
            // check an empty hand
            if (leftHand == null){
                //left-hand empty
                leftHand = weapon;
            } else if (leftHand.getRequired_hands() == 1 && rightHand == null){
                //right-hand empty
                rightHand = weapon;
            } else {
                System.out.println("no empty hands");
                System.out.println("please take off your weapon first");
                this.show();
                takeoff(inventory);
                equipWeapon(weapon, inventory);
            }
        } else {
            // weapon.getRequired_hands() == 2
            if(leftHand == null && rightHand == null){
                leftHand = weapon;
            } else {
                System.out.println("no enough empty hands");
                System.out.println("please take off your weapon first");
                this.show();
                takeoff(inventory);
                equipWeapon(weapon, inventory);
            }
        }
    }

    /**
     * equip the armor
     * @param armor
     * @param inventory
     */
    public void equipArmor(Armor armor, Inventory inventory){
        if (body == null){
            body = armor;
        } else {
            System.out.println("please take off your armor first");
            this.show();
            takeoff(inventory);
            equipArmor(armor, inventory);
        }
    }

    public void show() {
        System.out.println("1.    Left Hand:" + (leftHand == null? "" : leftHand));
        System.out.println("2.    Right Hand:" + (rightHand == null? "" : rightHand));
        System.out.println("3.    Body:" + (body == null? "" : body));
    }


    /**
     * take off the equipment and put back to the inventory
     * @param inventory
     */
    public void takeoff(Inventory inventory) {
        System.out.println("Please enter the index before the item you want to take off!");
        int index = scanner.nextInt();
        Item re = null;
        if(index == 1){
            re = leftHand;
            leftHand = null;
        } else if(index == 2){
            re = rightHand;
            rightHand = null;
        } else if(index == 3){
            re = body;
            body = null;
        }
        // give back to the inventory
        if(re != null){
            System.out.println("OK! The item is now in your inventory");
            inventory.add(index == 3 ? "Armor" : "Weapon" , re);
        } else {
            System.out.println("Fine! You took off nothing! ");
        }
    }

    /**
     * calculate the damage of equipped weapon
     * @return
     */
    public int sumDamage() {
        int sum = 0;
        if(leftHand != null) {
            sum += leftHand.getDamage();
        }
        if(rightHand != null) {
            sum += rightHand.getDamage();
        }
        return sum;
    }

    /**
     * calculate the defence of equipped armor
     * @return
     */
    public int sumDef(){
        int sum = 0;
        if (body != null) {
            sum += body.getDamageReduction();
        }
        return sum;
    }
}
