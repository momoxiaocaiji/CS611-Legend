import java.util.Random;

//public class BalancedTileCreator implements TileCreator{
public class BalancedTileCreator{
    private int numOfITile;
    private int numOfMTile;
    private int numOfCTile;

    // total tile need to be created
    public BalancedTileCreator(int total) {
        numOfCTile = (int) (0.5 * total);
        numOfMTile = (int) (0.2 * total);
        numOfITile = total - numOfCTile - numOfMTile;
    }

    public Tile createTile() {
        Random random = new Random();
        int temp = random.nextInt(numOfITile + numOfMTile + numOfCTile);

        if (temp < numOfCTile) {
            numOfCTile--;
            return new PlainTile();
        } else if (temp < numOfCTile + numOfMTile) {
            numOfMTile--;
            return new NexusTile();
        } else {
            numOfITile--;
            return new InaccessibleTile();
        }
    }
}
