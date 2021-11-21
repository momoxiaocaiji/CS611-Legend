import java.util.Scanner;

public class NexusTile extends ValorTile implements Tile{

    protected MainMarket market;
    protected Scanner scanner = MainScanner.getSingleInstance().getScanner();

    public NexusTile() {
        market = MainMarket.getSingleInstance();
    }

    @Override
    public boolean trigger(Hero hero) {
        //init the extra attributes
        hero.setExtraDexterity(0);
        hero.setExtraStrength(0);
        hero.setExtraAgility(0);

        // enter the Market
        System.out.println("Welcome! Hero " + hero.getName());
        market.setGuest(hero);

        while (true){
            System.out.println("Do you want to buy something or sale something?");
            System.out.println("1. Buy");
            System.out.println("2. Sale");
            System.out.println("3. Exit");
            int index = scanner.nextInt();
            if (index == 1){
                market.showTheList();
            } else if(index == 2){
                hero.checkInventoryAndSale();
            } else if(index == 3){
                System.out.println("Hero " + hero.getName() + " exits the market!");
                market.setGuest(null);
                break;
            }
        }
        return true;
    }

    public String toString() {
        return "N - N - N  ";
    }
}
