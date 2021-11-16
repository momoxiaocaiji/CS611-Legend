import java.util.List;

public abstract class Character implements Hurtable, Buyable{
    protected String name;
    protected int level;
    protected int HP;
    protected int status;
    // Position
    protected Position position = new Position();

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public Character() {
        this.level = 1;
        this.HP = this.level * Constant.HP_FACTOR;
        this.status = Constant.NORMAL;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getLevel() {
        return level;
    }

    public boolean canBuy(Item item){
        if (this.level >= item.getMinLevelLimit()){
            return true;
        }
        System.out.println("You don't get the level(Req: " + item.getMinLevelLimit()+ " Your level: " + level + ")");
        return false;
    }

    @Override
    public void getHurt(int damage){
        this.HP -= damage;
        if(this.HP < 0) {
            this.HP = 0;
        }
    }

    public int getStatus() {
        return status;
    }
}
