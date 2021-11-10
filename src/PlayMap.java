import java.util.*;

public class PlayMap {
    private Tile[][] playGround;
    private int length;
    private int width;
    private MainMarket mainMarket;
    private Map<MAHGamePlayer, Position> playersPosition;


    // for non-square map
    public PlayMap(int length, int width) {
        this.length = length;
        this.width = width;
        playGround = new Tile[length][width];
        mainMarket = MainMarket.getSingleInstance();
        playersPosition = new HashMap<>();
    }

    public PlayMap(int size){
        this(size,size);
    }

    public PlayMap(){
        this(8);
    }

    public void add(Tile tile, int x, int y) {
        playGround[x][y] = tile;
    }

    public void show(){
        for(int l = 0; l< length; l++) {
            for (int w = 0; w < width; w++) {
                if(playersPosition.containsValue(new Position(l,w))) {
                    System.out.print(Constant.BACKGROUND_RED + playGround[l][w] + Constant.RESET);
                } else {
                    System.out.print(playGround[l][w]);
                }
            }
            System.out.print("\n");
        }
    }

    public Position giveValidPosition(){
        boolean valid = false;
        Random random = new Random();
        int xPos = 0;
        int yPos = 0;

        while (!valid){
            xPos = random.nextInt(length - 2) + 1;
            yPos = random.nextInt(width - 2) + 1;
            if (playGround[xPos][yPos - 1] instanceof InaccessibleTile
                    && playGround[xPos][yPos + 1] instanceof  InaccessibleTile
                    && playGround[xPos+1][yPos] instanceof InaccessibleTile
                    && playGround[xPos - 1][yPos] instanceof InaccessibleTile) {
            } else {
                if (playGround[xPos][yPos] instanceof InaccessibleTile) {
                } else  {
                    valid = true;
                }
            }
        }

        return new Position(xPos, yPos);
    }

    public void setPlayersPosition(MAHGamePlayer player , Position playersPosition) {
        this.playersPosition.put(player , playersPosition);
    }

    public boolean validMove(MAHGamePlayer player , String direction) {
        switch (direction){
            case "w":
            case "W":
                 if (playersPosition.get(player).getxPos() - 1 < 0
                         || playGround[playersPosition.get(player).getxPos() - 1][playersPosition.get(player).getyPos()] instanceof InaccessibleTile)
                 {
                     return false;
                 }
                 else {
                     playersPosition.get(player).setxPos(playersPosition.get(player).getxPos()-1);
                     encounterEvent(player);
                     return true;
                 }
            case "a":
            case "A":
                if (playersPosition.get(player).getyPos() - 1 < 0
                        || playGround[playersPosition.get(player).getxPos()][playersPosition.get(player).getyPos() - 1] instanceof InaccessibleTile)
                {
                    return false;
                }
                else {
                    playersPosition.get(player).setyPos(playersPosition.get(player).getyPos() - 1);
                    encounterEvent(player);
                    return true;
                }
            case "d":
            case "D":
                if (playersPosition.get(player).getyPos() + 1 == length
                        || playGround[playersPosition.get(player).getxPos()][playersPosition.get(player).getyPos() + 1] instanceof InaccessibleTile)
                {
                    return false;
                }
                else {
                    playersPosition.get(player).setyPos(playersPosition.get(player).getyPos() + 1);
                    encounterEvent(player);
                    return true;
                }
            case "s":
            case "S":
                if (playersPosition.get(player).getxPos() + 1 == width
                        || playGround[playersPosition.get(player).getxPos() + 1][playersPosition.get(player).getyPos()] instanceof InaccessibleTile)
                {
                    return false;
                }
                else {
                    playersPosition.get(player).setxPos(playersPosition.get(player).getxPos() + 1);
                    encounterEvent(player);
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
