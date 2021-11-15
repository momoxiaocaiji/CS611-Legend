import java.util.*;

public class PlayMap {
    private ValorTile[][] playGround;
    private int length;
    private int width;
    private MainMarket mainMarket;
    private Map<MAHGamePlayer, Position> playersPosition;


    // for non-square map
    public PlayMap(int length, int width) {
        this.length = length;
        this.width = width;
        playGround = new ValorTile[length][width];
        mainMarket = MainMarket.getSingleInstance();
        playersPosition = new HashMap<>();
    }

    public PlayMap(int size){
        this(size,size);
    }

    public PlayMap(){
        this(8);
    }

    public void add(ValorTile tile, int x, int y) {
        playGround[x][y] = tile;
    }

    public void show(){
        for(int l = 0; l< length; l++) {
            for (int w = 0; w < width; w++) {
                System.out.print(playGround[l][w]);
            }
            System.out.print("\n");
            //TODO:blank cell
            for (int w = 0; w < width; w++) {
                System.out.print("| ");
                if(playGround[l][w] instanceof InaccessibleTile){
                    System.out.print("X X X |  ");
                    continue;
                }
                if (playGround[l][w].getHero() != null){
                    System.out.print(Constant.BLUE + "H" + Constant.RESET);
                } else {
                    System.out.print(" ");
                }
                if (playGround[l][w].getMonster() != null){
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
    public Position giveValorInitPosition(Hero h, int index){
        playGround[length - 1][(index - 1) * 3].setHero(h);
        return new Position(length - 1 , (index - 1) * 3);
    }

    public void initMonsterPosition(Monster monster, int laneIndex){
        // random create the monster in the lane
        playGround[0][(laneIndex - 1) * 3 + new Random().nextInt(2)].setMonster(monster);
    }

    public boolean validMove(Position heroPosition, String direction) {
        switch (direction){
            case "w":
            case "W":
                 if (heroPosition.getxPos() - 1 < 0
                         || playGround[heroPosition.getxPos() - 1][heroPosition.getyPos()] instanceof InaccessibleTile)
                 {
                     return false;
                 }
                 else {
                     // delete hero in current tile
                     playGround[heroPosition.getxPos()-1][heroPosition.getyPos()].setHero(
                             playGround[heroPosition.getxPos()][heroPosition.getyPos()].getHero());
                     playGround[heroPosition.getxPos()][heroPosition.getyPos()].setHero(null);
                     heroPosition.setxPos(heroPosition.getxPos()-1);
                     //encounterEvent(player);
                     return true;
                 }
            case "a":
            case "A":
                if (heroPosition.getyPos() - 1 < 0
                        || playGround[heroPosition.getxPos()][heroPosition.getyPos() - 1] instanceof InaccessibleTile)
                {
                    return false;
                }
                else {
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
                        || playGround[heroPosition.getxPos()][heroPosition.getyPos() + 1] instanceof InaccessibleTile)
                {
                    return false;
                }
                else {
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
                        || playGround[heroPosition.getxPos() + 1][heroPosition.getyPos()] instanceof InaccessibleTile)
                {
                    return false;
                }
                else {
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

    private void encounterEvent(MAHGamePlayer player) {
        playGround[playersPosition.get(player).getxPos()][playersPosition.get(player).getyPos()].trigger(player);
    }
}
