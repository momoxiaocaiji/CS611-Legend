import java.util.Objects;

public abstract class Item implements Usable, Saleable{
    // s a name,
    //a price and a minimum hero level
    protected String name;
    protected int price;
    protected int minLevelLimit;

    public Item(){}

    public Item(String name, int price, int minLevelLimit){
        this.name = name;
        this.price = price;
        this.minLevelLimit = minLevelLimit;
    }

    public int getMinLevelLimit() {
        return minLevelLimit;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public abstract Item copy();

    @Override
    public boolean use(Hero hero) {
        return false;
    }

    @Override
    public void sale(Hero hero){
        // sale by half price
        hero.setMoney(hero.getMoney() + this.price / 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

}
