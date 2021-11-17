import java.util.*;

public class PlayMap {
    private ValorTile[][] playGround;
    private int length;
    private int width;
    private MainMarket mainMarket;
    private Map<MAHGamePlayer, Position> playersPosition;
    private Scanner scanner = MainScanner.getSingleInstance().getScanner();


    public int getWidth() {
        return width;
    }

    // for non-square map
    public PlayMap(int length, int width) {
        this.length = length;
        this.width = width;
        playGround = new ValorTile[length][width];
        mainMarket = MainMarket.getSingleInstance();
        playersPosition = new HashMap<>();
    }

    public PlayMap(int size) {
        this(size, size);
    }

    public PlayMap() {
        this(8);
    }

    public void add(ValorTile tile, int x, int y) {
        playGround[x][y] = tile;
    }

    public void show(Hero currentHero) {
        for (int l = 0; l < length; l++) {
            for (int w = 0; w < width; w++) {
                System.out.print(playGround[l][w]);
            }
            System.out.print("\n");
            //TODO:blank cell
            for (int w = 0; w < width; w++) {
                System.out.print("| ");
                if (playGround[l][w] instanceof InaccessibleTile) {
                    System.out.print("X X X |  ");
                    continue;
                }
                if (playGround[l][w].getHero() != null) {
                    if (playGround[l][w].getHero().equals(currentHero)) {
                        System.out.print(Constant.YELLOW_BRIGHT + "H" + Constant.RESET);
                    } else {
                        System.out.print(Constant.BLUE + "H" + Constant.RESET);
                    }
                } else {
                    System.out.print(" ");
                }
                if (playGround[l][w].getMonster() != null) {
                    System.out.print(Constant.RED + "   M" + Constant.RESET);
                } else {
                    System.out.print("    ");
                }
                System.out.print(" |  ");
            }
            System.out.print("\n");
            for (int w = 0; w < width; w++) {
                System.out.print(playGround[l][w]);
            }
            System.out.print("\n\n");
        }
    }

    //
    public Position giveValorInitPosition(Hero h, int index) {
        h.setExploredTile(length - 1);
        playGround[length - 1][(index - 1) * 3].setHero(h);
        return new Position(length - 1, (index - 1) * 3);
    }

    public Position initMonsterPosition(Monster monster, int laneIndex) {
        // random create the monster in the lane
        int random = new Random().nextInt(2);
        playGround[0][(laneIndex - 1) * 3 + random].setMonster(monster);
        return new Position(0, (laneIndex - 1) * 3 + random);
    }

    public boolean validMove(String direction, Hero hero) {
        Position heroPosition = hero.getPosition();
        switch (direction) {
            case "b":
            case "B":
                if (playGround[length - 1][heroPosition.getyPos()].getHero() != null) {
                    System.out.println("There already has a hero");
                    return false;
                }
                playGround[length - 1][heroPosition.getyPos()].setHero(
                        playGround[heroPosition.getxPos()][heroPosition.getyPos()].getHero());
                playGround[heroPosition.getxPos()][heroPosition.getyPos()].setHero(null);
                hero.tp(heroPosition, length - 1, heroPosition.getyPos());
                return true;
            case "t":
            case "T":
                int input;
                int row;
                int column;
                System.out.println("Where would you like to tp?");

                System.out.println("input row:");
                input = scanner.nextInt();
                while (true) {
                    if (input >= 1 && input <= this.length && input <= width - hero.getExploredTile()) {
                        // player will see the inverse index of row
                        row = width - input;
                        break;
                    } else {
                        System.out.println("input row, the area is not able to arrive:");
                        input = scanner.nextInt();
                    }
                }

                System.out.println("input column:");
                input = scanner.nextInt();
                while (true) {
                    if (input >= 1 && input <= this.width && Math.abs(input - 1 - heroPosition.getyPos()) > 1) {
                        column = input - 1;
                        break;
                    } else {
                        System.out.println("input column, the area is not able to arrive:");
                        input = scanner.nextInt();
                    }
                }

                if (playGround[row][column] instanceof InaccessibleTile) {
                    return false;
                } else if (playGround[row][column].getHero() != null) {
                    System.out.println("There already has a hero");
                    return false;
                } else {
                    playGround[row][column].setHero(
                            playGround[heroPosition.getxPos()][heroPosition.getyPos()].getHero());
                    playGround[heroPosition.getxPos()][heroPosition.getyPos()].setHero(null);
                    hero.tp(heroPosition, row, column);
                    return true;
                }
            case "w":
            case "W":
                int next = heroPosition.getyPos() % 3 == 1 ? heroPosition.getyPos() - 1 : heroPosition.getyPos() + 1;
                if (heroPosition.getxPos() - 1 < 0
                        || playGround[heroPosition.getxPos() - 1][heroPosition.getyPos()] instanceof InaccessibleTile
                        || playGround[heroPosition.getxPos() - 1][heroPosition.getyPos()].getHero() != null) {
                    return false;
                } else if (playGround[heroPosition.getxPos()][heroPosition.getyPos()].getMonster() != null
                        || playGround[heroPosition.getxPos()][next].getMonster() != null) {
                    // cannot move behind the monster
                    System.out.println("Cannot move behind the monster!!");
                    return false;
                } else {
                    // delete hero in current tile
                    playGround[heroPosition.getxPos() - 1][heroPosition.getyPos()].setHero(
                            playGround[heroPosition.getxPos()][heroPosition.getyPos()].getHero());
                    playGround[heroPosition.getxPos()][heroPosition.getyPos()].setHero(null);
                    heroPosition.setxPos(heroPosition.getxPos() - 1);
                    //encounterEvent(player);


                    return true;
                }
            case "a":
            case "A":
                if (heroPosition.getyPos() - 1 < 0
                        || playGround[heroPosition.getxPos()][heroPosition.getyPos() - 1] instanceof InaccessibleTile
                        || playGround[heroPosition.getxPos()][heroPosition.getyPos() - 1].getHero() != null) {
                    return false;
                } else {
                    // delete hero in current tile
                    playGround[heroPosition.getxPos()][heroPosition.getyPos() - 1].setHero(
                            playGround[heroPosition.getxPos()][heroPosition.getyPos()].getHero());
                    playGround[heroPosition.getxPos()][heroPosition.getyPos()].setHero(null);
                    heroPosition.setyPos(heroPosition.getyPos() - 1);
                    //encounterEvent(player);
                    return true;
                }
            case "d":
            case "D":
                if (heroPosition.getyPos() + 1 == length
                        || playGround[heroPosition.getxPos()][heroPosition.getyPos() + 1] instanceof InaccessibleTile
                        || playGround[heroPosition.getxPos()][heroPosition.getyPos() + 1].getHero() != null) {
                    return false;
                } else {
                    // delete hero in current tile
                    playGround[heroPosition.getxPos()][heroPosition.getyPos() + 1].setHero(
                            playGround[heroPosition.getxPos()][heroPosition.getyPos()].getHero());
                    playGround[heroPosition.getxPos()][heroPosition.getyPos()].setHero(null);
                    heroPosition.setyPos(heroPosition.getyPos() + 1);
                    //encounterEvent(player);
                    return true;
                }
            case "s":
            case "S":
                if (heroPosition.getxPos() + 1 == width
                        || playGround[heroPosition.getxPos() + 1][heroPosition.getyPos()] instanceof InaccessibleTile
                        || playGround[heroPosition.getxPos() + 1][heroPosition.getyPos()].getHero() != null) {
                    return false;
                } else {
                    // delete hero in current tile
                    playGround[heroPosition.getxPos() + 1][heroPosition.getyPos()].setHero(
                            playGround[heroPosition.getxPos()][heroPosition.getyPos()].getHero());
                    playGround[heroPosition.getxPos()][heroPosition.getyPos()].setHero(null);
                    heroPosition.setxPos(heroPosition.getxPos() + 1);
                    //encounterEvent(player);
                    return true;
                }
            default:
                return true;
        }
    }

    public boolean monsterMove(Monster monster) {
        Position monsterPosition = monster.getPosition();
        List<Hero> heroList = (List<Hero>) haveOpposite(monster);
        Hero h = null;
        if (heroList.size() != 0) {
            h = heroList.get(0);
        }
        if (h == null) {
            // move forward
            if (playGround[monsterPosition.getxPos() + 1][monsterPosition.getyPos()].getMonster() != null) {
                System.out.println(Constant.DIVIDE);
                System.out.println("Already has a monster in that tile. Just wait!");
                System.out.println(Constant.DIVIDE);
            }
            playGround[monsterPosition.getxPos() + 1][monsterPosition.getyPos()].setMonster(
                    playGround[monsterPosition.getxPos()][monsterPosition.getyPos()].getMonster());
            playGround[monsterPosition.getxPos()][monsterPosition.getyPos()].setMonster(null);
            monsterPosition.setxPos(monsterPosition.getxPos() + 1);
        } else {
            // fight with hero
            FightSystem ft = new ParticipateBasedFightSystem(h, monster, false);
            ft.startAFight();

            // after battle
            if (h.getStatus() == Constant.FAINT) {
                h.respawnFromFaint();
                playGround[length - 1][h.getPosition().getyPos()].setHero(
                        playGround[h.getPosition().getxPos()][h.getPosition().getyPos()].getHero());
                playGround[h.getPosition().getxPos()][h.getPosition().getyPos()].setHero(null);
                h.getPosition().setxPos(length - 1);
            }

            System.out.println(Constant.DIVIDE);

            System.out.println("Press c to exit this fight");
            String ins = scanner.next();
            while (!ins.equalsIgnoreCase("C")) {
                System.out.println("Invalid instruction");
                scanner.next();
            }
        }
        return true;
    }

    public boolean triggerFight(Hero hero) {
        Monster monster = null;
        List<Monster> monsterList = (List<Monster>) haveOpposite(hero);
        if (monsterList.size() != 0) {
            monster =  chooseMonster(monsterList);
        }
        if (monster == null) {
            System.out.println(Constant.DIVIDE);
            System.out.println("you cannot fight with any monster!");
            System.out.println(Constant.DIVIDE);
            return false;
        } else {
            // fight with monster
            FightSystem ft = new ParticipateBasedFightSystem(hero, monster, true);
            ft.startAFight();

            // after battle
            if (monster.getStatus() == Constant.DEAD) {
                hero.getTheReward(monster.getLevel(), Constant.FIGHT_EXP);
                playGround[monster.getPosition().getxPos()][monster.getPosition().getyPos()].setMonster(null);
            }

            System.out.println(Constant.DIVIDE);

            System.out.println("Press c to exit this fight");
            String ins = scanner.next();
            while (!ins.equalsIgnoreCase("C")) {
                System.out.println("Invalid instruction");
                scanner.next();
            }

            return true;
        }
    }

    private void encounterEvent(MAHGamePlayer player) {
        playGround[playersPosition.get(player).getxPos()][playersPosition.get(player).getyPos()].trigger(player);
    }

    // trigger the fight
    private List<? extends Character> haveOpposite(Character c) {
        if (c instanceof Hero) {
            List<Monster> monsterList = new ArrayList<>();
            for (int i = c.getPosition().getxPos(); i >= c.getPosition().getxPos() - 1; i--) {
                if (playGround[i][c.getPosition().getyPos()].getMonster() != null) {
                    monsterList.add(playGround[i][c.getPosition().getyPos()].getMonster());
                }
                int next = c.getPosition().getyPos() % 3 == 0 ? c.getPosition().getyPos() + 1 : c.getPosition().getyPos() - 1;
                if (playGround[i][next].getMonster() != null) {
                    monsterList.add(playGround[i][next].getMonster());
                }
            }
            return monsterList;
        } else {
            List<Hero> heroList = new ArrayList<>();
            for (int i = c.getPosition().getxPos(); i <= c.getPosition().getxPos() + 1; i++) {
                if (playGround[i][c.getPosition().getyPos()].getHero() != null) {
                    heroList.add(playGround[i][c.getPosition().getyPos()].getHero());
                }
                int next = c.getPosition().getyPos() % 3 == 0 ? c.getPosition().getyPos() + 1 : c.position.getyPos() - 1;
                if (playGround[i][next].getHero() != null) {
                    heroList.add(playGround[i][next].getHero());
                }
            }
            return heroList;
        }
    }

    private Monster chooseMonster(List<Monster> monsterList) {
        for (int i = 0; i < monsterList.size(); i++) {
            System.out.println((i + 1) + ". " + monsterList.get(i));
        }
        System.out.println("Choose monster's index that you want to fight:");
        int indexMonster = scanner.nextInt() - 1;
        return monsterList.get(indexMonster);
    }
}
