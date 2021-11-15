public interface TileCreator {
    default Tile createInaccessibleTile(){
        return new InaccessibleTile();
    };
    default Tile createNexusTile(){
        return new NexusTile();
    };
    Tile createTile();
}
