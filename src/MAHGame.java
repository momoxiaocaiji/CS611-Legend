import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MAHGame extends RPGame{
    private List<MAHGamePlayer> players;
    private List<Monster> monsters;
    private final int size;
    private Scanner scanner;
    private boolean isNotEnd;

    private static List<Warrior> warriors;
    private static List<Sorcerer> sorcerers;
    private static List<Paladin> paladins;

    public MAHGame() {
        size = 8;
        scanner = MainScanner.getSingleInstance().getScanner();
        players = new ArrayList<>();
        players.add(new MAHGamePlayer());
//        playMap = createMap(new BalancedTileCreator(size*size));
        monsters = new ArrayList<>();
        playMap = createMap(new ValorTileCreator(size*size));
    }

    @Override
    protected List<MAHGamePlayer> getPlayers() {
        return players;
    }

    @Override
    protected void removePlayer(MAHGamePlayer p) {
        players.remove(p);
    }

    private PlayMap createMap(TileCreator tileCreator) {
        PlayMap playMap = new PlayMap(size);

//        for (int i = 0; i< size * size; i++){
//            playMap.add(tileCreator.createTile(), i / size , i % size);
//        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i == 0 || i == (size -1)) && (j != 2 && j != 5)) {
                    playMap.add((ValorTile) tileCreator.createNexusTile(), i , j);
                }else if (j == 2 || j == 5) {
                    playMap.add((ValorTile) tileCreator.createInaccessibleTile(), i , j);
                }else {
                    playMap.add((ValorTile) tileCreator.createTile(), i , j);
                }
            }
        }

        return playMap;
    }

    public void startGame() {
        // welcome
        System.out.println("Welcome to the world of Valor!");

        // init your team
        intiTeam();

        //init the monster
        initMonster(players.get(0).getTheHighestLevel());

        playMap.show();

        isNotEnd = true;
        // start game
        while(isNotEnd) {
            for (MAHGamePlayer p : players){
                for (Hero hero : p.getTeam().keySet()){
                    System.out.println(Constant.DIVIDE);
                    System.out.println("Hero " + hero.getName());
                    move(hero, p.getTeam().get(hero));
                }
            }
        }
    }

    private void intiTeam() {
        for (MAHGamePlayer p : players) {
            System.out.println("Hi! Jz!");
            System.out.println("Please choose your team(3):");
            int teamSize = Constant.INIT_HERO_NUM;
            while (teamSize > 0) {
                Hero h = chooseTheHero();
                // init hero position
                p.addHeroToTeam(h, playMap.giveValorInitPosition(h, teamSize));

                teamSize--;
            }

            //reset hero list
            warriors = null;
            sorcerers = null;
            paladins = null;
        }
    }

    private void initMonster(int levelLimit) {
        // init add 3 monsters
        Monster m1 = new RandomMonsterCreator().createMonster(levelLimit);
        monsters.add(m1);
        // for line one
        playMap.initMonsterPosition(m1 , 1);

        Monster m2 = new RandomMonsterCreator().createMonster(levelLimit);
        monsters.add(m2);
        // for line two
        playMap.initMonsterPosition(m2, 2);

        Monster m3 = new RandomMonsterCreator().createMonster(levelLimit);
        monsters.add(m3);
        // for line three
        playMap.initMonsterPosition(m3,3);
    }

    private Hero chooseTheHero() {
        Hero re = null;
        while (true){
            System.out.println("Which type of hero you'd like to choose:");
            System.out.println("1. Warrior");
            System.out.println("2. Sorcerer");
            System.out.println("3. Paladin");
            int type = MainScanner.getSingleInstance().validInput(3);
            System.out.println("Which one you want to choose:");
            switch (type){
                case 1:
                    if (warriors == null){
                        // need to read the config
                        List<String> rawData = Parser.parse("Warrior");
                        warriors = Warrior.createWarriors(rawData);
                    }
                    re = displayAndChooseTheHero(warriors);
                    if (re == null){
                        continue;
                    }
                    return re;
                case 2:
                    if (sorcerers == null){
                        // need to read the config
                        List<String> rawData = Parser.parse("Sorcerer");
                        sorcerers = Sorcerer.createSorcerer(rawData);
                    }
                    re = displayAndChooseTheHero(sorcerers);
                    if (re == null){
                        continue;
                    }
                    return re;
                case 3:
                    if (paladins == null){
                        // need to read the config
                        List<String> rawData = Parser.parse("Paladin");
                        paladins = Paladin.createPaladin(rawData);
                    }
                    re = displayAndChooseTheHero(paladins);
                    if (re == null){
                        continue;
                    }
                    return re;
                default:
                    // maybe you'd like a warrior
                    return new Warrior();
            }
        }

    }

    private void move(Hero hero, Position heroPosition) {
        System.out.println("Select the move:");
        System.out.print("W/w: move up\n" +
                "A/a: move left\n" +
                "S/s: move down\n" +
                "D/d: move right\n" +
                "Q/q: quit game\n" +
                "E/e: show inventory and change the equipment\n" +
                "I/i: show information.\n");
        String moveIndex = scanner.next();

        if("I".equalsIgnoreCase(moveIndex)) {
            // show the team info
            hero.show();
        } else if ("Q".equalsIgnoreCase(moveIndex)){
            // quit the game
            System.out.println("JZ quits the game! See you next time!");
            isNotEnd = false;
            return;
        } else if ("E".equalsIgnoreCase(moveIndex)){
            hero.checkEquipment();

            hero.checkInventoryAndChangeEquipment();

            // recalculate the ability after change your equipment
            hero.recalDamageAndDef();
        }else if ("W".equalsIgnoreCase(moveIndex)
                ||"A".equalsIgnoreCase(moveIndex)
                ||"S".equalsIgnoreCase(moveIndex)
                ||"D".equalsIgnoreCase(moveIndex)){
            // make a valid move
            while (!playMap.validMove(heroPosition, moveIndex)){
                System.out.println("Can't move to this tile");
                playMap.show();
                System.out.println("Select the move:");
                System.out.print("W/w: move up\n" +
                        "A/a: move left\n" +
                        "S/s: move down\n" +
                        "D/d: move right\n" +
                        "Q/q: quit game\n" +
                        "I/i: show information.\n");
                moveIndex = scanner.next();
            }
        } else {
            System.out.println("Not a valid move instruction!!");
        }
        System.out.println(Constant.DIVIDE);
        playMap.show();

    }

    private Hero displayAndChooseTheHero(List<? extends Hero> list){
        for(int i = 0; i < list.size(); i++){
            if (list.get(i).isChosen()){
                System.out.println(Constant.GREEN + (i+1) + ". " + list.get(i) + Constant.RESET);
            } else {
                System.out.println((i+1) + ". " + list.get(i));
            }
        }
        System.out.println((list.size()+1) + ". Go back to the previous menu" );
        int index = MainScanner.getSingleInstance().validInput(list.size()+1);
        if (index == list.size()+1){
            // go back
            return null;
        }
        while (list.get(index - 1).isChosen()){
            System.out.println("Hero " + list.get(index - 1).getName() + "is already in your team!");
            index = MainScanner.getSingleInstance().validInput(list.size());
        }
        list.get(index - 1).setChosen(true);

        return list.get(index - 1);
    }
}
