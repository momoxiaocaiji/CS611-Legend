import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FightSystem {
    private MAHGamePlayer player;
    private List<Monster> teamMonster;
    private Scanner scanner = MainScanner.getSingleInstance().getScanner();

    public FightSystem(MAHGamePlayer curPlayer) {
        player = curPlayer;
        teamMonster = createMonsterTeam(new RandomMonsterCreator());
    }

    /**
     * create the team of monster in the fight
     * @param monsterCreator
     * @return
     */
    private List<Monster> createMonsterTeam(MonsterCreator monsterCreator) {
        List<Monster> team = new ArrayList<>();

        for (int i = 0; i < player.getTeamSize(); i++) {
            team.add(monsterCreator.createMonster(player.getTheHighestLevel()));
        }

        return team;
    }

    /**
     * Start the fight in the common tile
     */
    public void startAFight() {
        displayTeamInfo();

        // indicate which character to move
        boolean turnHero = true;
        CirculateList cHero = new CirculateList(player.getTeam());
        CirculateList cMonster = new CirculateList(teamMonster);
        while (true) {
            Hero h = (Hero) cHero.getHead();
            System.out.println(Constant.DIVIDE);
            if (turnHero) {
                System.out.println("Hero " + h.getName() + " !");
                System.out.println("Choose monster's index that you want to fight:");
                int indexMonster = scanner.nextInt() - 1;
                chooseAMove(h, teamMonster.get(indexMonster));
                // justify whether finish
                if (countHPSum(teamMonster) == 0) {
                    System.out.println("Heroes win!");
                    break;
                }
                cMonster.goNext();
                turnHero = false;
            } else {
                Monster m = (Monster) cMonster.getHead();
                // monster action
                Random random = new Random();
                System.out.println("Monster " + m.getName() + " !");
                System.out.println(m.getName() + " uses Formal Attack");
                if (random.nextDouble() * 100 <= h.getDodge()) {
                    System.out.println(h.getName() + " dodged!");
                } else {
                    int finalDamage = (int) Math.max(m.getBaseDamage() - (h.getDef() + h.getArmorDef()) * Constant.DEFENCE_RATE, 0);
                    System.out.println(h.getName() +  " took " + Constant.WHITE_BOLD_BRIGHT +  finalDamage
                            + Constant.RESET + " damage");
                    h.getHurt(finalDamage);
                }
                // justify whether finish
                if (countHPSum(player.getTeam()) == 0) {
                    System.out.println("Monsters win");
                    break;
                }
                cHero.goNext();
                turnHero = true;
            }
            displayTeamInfo();
            System.out.println(Constant.DIVIDE);
        }

        System.out.println("Fight finish!!");
        System.out.println(Constant.DIVIDE);

        player.afterBattle(Constant.GAIN_EXP);

        System.out.println(Constant.DIVIDE);

        System.out.println("Press c to exit this fight");
        String ins = scanner.next();
        while (!ins.equalsIgnoreCase("C")){
            System.out.println("Invalid instruction");
            scanner.next();
        }

    }

    /**
     * display the info of both team
     */
    private void displayTeamInfo() {
        for (int i = 0; i < player.getTeamSize(); i++) {
            System.out.print(player.getTeam().get(i).toStringInBattle());
            System.out.print("        ");
            System.out.print((i + 1) + ". " + teamMonster.get(i));
            System.out.println("\n");
        }
    }

    /**
     * count the sum of HP of one team
     * @param team
     * @return
     */
    private int countHPSum(List<? extends Character> team) {
        int sum = 0;
        for (int i = 0; i < team.size(); i++) {
            sum += team.get(i).getHP();
        }
        return sum;
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



