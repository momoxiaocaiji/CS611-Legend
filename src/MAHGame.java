import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MAHGame extends RPGame{
    private List<MAHGamePlayer> players;
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
        playMap = createMap(new BalancedTileCreator(size*size));
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
        //TODO
        PlayMap playMap = new PlayMap(size);

        for (int i = 0; i< size * size; i++){
            playMap.add(tileCreator.createTile(), i / size , i % size);
        }

        return playMap;
    }

    public void startGame() {
        // welcome
        System.out.println("Welcome to the world of MAH!");

        // init your team
        intiTeam();

        isNotEnd = true;
        // start game
        while(isNotEnd) {
            for (MAHGamePlayer p : players){
                move(p);
            }
        }
    }

    private void intiTeam() {
        for (MAHGamePlayer p : players) {
            System.out.println("Hi! Jz!");
            System.out.println("Please choose the size of your team(1~3):");
            int teamSize = scanner.nextInt();
            while (teamSize > 0) {
                Hero h = chooseTheHero();
                p.addHeroToTeam(h);
                teamSize--;
            }

            // init team position
            playMap.setPlayersPosition(p , p.setPosition(playMap.giveValidPosition()));

            playMap.show();

            //reset hero list
            warriors = null;
            sorcerers = null;
            paladins = null;
        }
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

    private void move(MAHGamePlayer p) {
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
            p.show();
        } else if ("Q".equalsIgnoreCase(moveIndex)){
            // quit the game
            System.out.println("JZ quits the game! See you next time!");
            isNotEnd = false;
            return;
        } else if ("E".equalsIgnoreCase(moveIndex)){
            p.checkInventory();
        }else if ("W".equalsIgnoreCase(moveIndex)
                ||"A".equalsIgnoreCase(moveIndex)
                ||"S".equalsIgnoreCase(moveIndex)
                ||"D".equalsIgnoreCase(moveIndex)){
            // make a valid move
            while (!playMap.validMove(p , moveIndex)){
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
