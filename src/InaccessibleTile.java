public class InaccessibleTile implements Tile{

    public InaccessibleTile(){}

    @Override
    public boolean trigger(MAHGamePlayer player) {
        System.out.println("You cannot access here!!");

        return false;
    }

    public String toString() {
//        return Constant.BLACK_BACKGROUND + "I - I - I " + Constant.RESET;
        return "I - I - I  ";
    }
}
