import java.util.Random;

public class ValorTileCreator implements TileCreator{
    private int numOfPTile; //number of Plain cell
    private int numOfCTile; //number of Cave cell
    private int numOfBTile; //number of Bush cell
    private int numOfKTile; //number of Koulou cell

    public ValorTileCreator(int total) {
        //TODO: change the embeded number
        numOfPTile = (int)((total - 28) * 0.4);
        numOfCTile = (int)((total - 28) * 0.2);
        numOfBTile = (int)((total - 28) * 0.2);
        numOfKTile = total - numOfPTile - numOfCTile - numOfBTile;
    }

    public Tile createInaccessibleTile() {
        return new InaccessibleTile();
    }

    public Tile createNexusTile() {
        return new NexusTile();
    }

    public Tile createTile() {
        Random random = new Random();
        int temp = random.nextInt(numOfPTile + numOfCTile + numOfBTile + numOfKTile);

        if (temp < numOfPTile) {
            numOfPTile--;
            return new PlainTile();
        } else if (temp < numOfPTile + numOfCTile) {
            numOfCTile--;
            return new CaveTile();
        } else if (temp < numOfPTile + numOfCTile + numOfBTile){
            numOfBTile--;
            return new BushTile();
        } else {
            numOfKTile--;
            return new KoulouTile();
        }
    }
}
