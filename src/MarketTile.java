import java.util.Scanner;

public class MarketTile implements Tile{

    protected MainMarket market;
    protected Scanner scanner = MainScanner.getSingleInstance().getScanner();

    public MarketTile() {
        market = MainMarket.getSingleInstance();
    }

    @Override
    public boolean trigger(MAHGamePlayer player) {
        // enter the Market
        for (Hero h: player.getTeam()){
            System.out.println("Welcome! Hero " + h.getName());
            market.setGuest(h);

            while (true){
                System.out.println("Do you want to buy something or sale something?");
                System.out.println("1. Buy");
                System.out.println("2. Sale");
                System.out.println("3. Exit");
                int index = scanner.nextInt();
                if (index == 1){
                    market.showTheList();
                } else if(index == 2){
                    h.checkInventoryAndSale();
                } else if(index == 3){
                    System.out.println("Hero " + h.getName() + " exits the market!");
                    market.setGuest(null);
                    break;
                }
            }

        }
        return true;
    }

    public String toString() {
        return "[M]";
    }
}
