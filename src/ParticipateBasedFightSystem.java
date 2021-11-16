import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ParticipateBasedFightSystem implements FightSystem{
    private Hero hero;
    private Monster monster;
    private Scanner scanner = MainScanner.getSingleInstance().getScanner();
    // indicates who can make the move
    private Boolean turnHero = true;

    public ParticipateBasedFightSystem(Hero hero, Monster monster, Boolean turnHero){
        this.hero = hero;
        this.monster = monster;
        this.turnHero = turnHero;
    }

    @Override
    // just for one turn fight
    public void startAFight(){
        displayFightInfo();

        // indicate which character to move
        System.out.println(Constant.DIVIDE);
        if (turnHero) {
            System.out.println("Hero " + hero.getName() + " !");
            chooseAMove(hero, monster);
        } else {
            // monster action
            Random random = new Random();
            System.out.println("Monster " + monster + " !");
            System.out.println(monster.getName() + " uses Formal Attack");
            if (random.nextDouble() * 100 <= hero.getDodge()) {
                System.out.println(hero.getName() + " dodged!");
            } else {
                int finalDamage = (int) Math.max(monster.getBaseDamage() - (hero.getDef() + hero.getArmorDef()) * Constant.DEFENCE_RATE, 0);
                System.out.println(hero.getName() +  " took " + Constant.WHITE_BOLD_BRIGHT +  finalDamage
                        + Constant.RESET + " damage");
                hero.getHurt(finalDamage);
            }
        }
        displayFightInfo();
        System.out.println(Constant.DIVIDE);
        System.out.println("Fight finish!!");
        System.out.println(Constant.DIVIDE);


    }

    private void displayFightInfo(){
        System.out.println(Constant.DIVIDE);
        System.out.println(hero);
        System.out.println(monster);
        System.out.println(Constant.DIVIDE);
    }

    /**
     * make the menu for hero to move during the fight
     * @param curHero
     * @param opposite
     */
    private void chooseAMove(Hero curHero, Monster opposite) {
        System.out.println(Constant.DIVIDE);
        while (true) {
            System.out.println("Please choose a move:");
            System.out.println("1. Formal Attack");
            System.out.println("2. Cast the spell");
            System.out.println("3. Use the potion");
            System.out.println("4. Change my equipment");
            int option = scanner.nextInt();
            if (option == 1) {
                Random random = new Random();
                System.out.println(curHero.getName() + " uses Formal Attack");
                if (random.nextInt(100) <= opposite.getDodgeChance()) {
                    // monster dodged
                    System.out.println(opposite.getName() + " dodged!");
                } else {
                    int realDamage = (int) Math.max(curHero.getDamage() + curHero.getWeaponDamage() - opposite.getDefence() * Constant.DEFENCE_RATE, 0);
                    System.out.println(opposite.getName() + " took "+ Constant.WHITE_BOLD_BRIGHT + realDamage + Constant.RESET +" damage");
                    opposite.getHurt(realDamage);
                }
                break;
            } else if (option == 2) {
                if (curHero.getLearnedSpell().size() == 0){
                    System.out.println(Constant.DIVIDE);
                    System.out.println("Hero " + curHero.getName() + " haven't learned any spell!");
                    System.out.println(Constant.DIVIDE);
                    continue;
                }
                curHero.displayLearnedSkill();
                System.out.println("Which spell you'd like to cast?");
                int spellIndex = scanner.nextInt();
                boolean suc = curHero.getLearnedSpell().get(spellIndex-1).cast(curHero, opposite);
                if (!suc) {
                } else {
                    break;
                }
            } else if (option == 3) {
                List<Item> potions = curHero.getPotions();
                if (potions.size() == 0){
                    System.out.println(Constant.DIVIDE);
                    System.out.println("Hero " + curHero.getName() + " doesn't have any potion!");
                    System.out.println(Constant.DIVIDE);
                    continue;
                }
                System.out.println("Which potion you'd like to use?");
                for(int i = 0; i < potions.size(); i++){
                    System.out.println((i+1) + ". " + potions);
                }
                int potionIndex = scanner.nextInt();
                potions.get(potionIndex-1).use(curHero);
                break;
            } else if (option == 4) {
                List<Item> weaponAndArmor = curHero.getWeaponAndArmor();
                if (weaponAndArmor.size() == 0){
                    System.out.println(Constant.DIVIDE);
                    System.out.println("Hero " + curHero.getName() + " doesn't have any alternative armor/weapon!");
                    System.out.println(Constant.DIVIDE);
                    continue;
                }
                System.out.println("Which armor/weapon you'd like to use?");
                for(int i = 0; i < weaponAndArmor.size(); i++){
                    System.out.println((i+1) + ". " + weaponAndArmor);
                }
                int weaponAndArmorIndex = scanner.nextInt();
                boolean suc = weaponAndArmor.get(weaponAndArmorIndex-1).use(curHero);
                if (!suc) {
                } else {
                    break;
                }
            } else {
                System.out.println("Invalid instruction");
            }

        }

    }

}
