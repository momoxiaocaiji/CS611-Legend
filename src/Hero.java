import java.util.*;


public class Hero extends Character implements Teleport {
    // mana
    protected int MP;
    protected int money;
    protected int exp;
    protected int expForLevelUp;
    protected Inventory inventory;
    // equipment
    protected Equipment equipment;

    protected int exploredTile;//the highest row hero has visited

    protected int damage;
    protected int weaponDamage;
    protected int def;
    protected int armorDef;

    protected double dodge;

    //record
    protected int recordHP;
    protected int recordMP;

    protected double extraDexterity;
    protected double extraAgility;
    protected double extraStrength;


    // skill
    protected Map<String, Integer> skills;

    //main skill
    protected List<String> mainSkills;

    protected Scanner scanner = MainScanner.getSingleInstance().getScanner();

    // learned spell
    protected List<Spell> learnedSpell;

    protected boolean chosen = false;

    public void setExtraDexterity(double extraDexterity) {
        this.extraDexterity = extraDexterity;
    }

    public void setExtraAgility(double extraAgility) {
        this.extraAgility = extraAgility;
    }

    public void setExtraStrength(double extraStrength) {
        this.extraStrength = extraStrength;
    }

    public double getExtraDexterity() {
        return extraDexterity;
    }

    public double getExtraAgility() {
        return extraAgility;
    }

    public double getExtraStrength() {
        return extraStrength;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public Hero (){
        skills = new HashMap<>();
        mainSkills = new ArrayList<>();
        inventory = new Inventory();
        equipment = new Equipment();
        learnedSpell = new ArrayList<>();
        // add three main skill
        //influence weapon damage
        skills.put("strength", 0);

        //influence spell damage
        skills.put("dexterity", 0);

        //influence dodge rate
        skills.put("agility", 0);

        expForLevelUp = this.level * Constant.LEVEL_UP_FACTOR;
    }

    protected void setMainSkills(String mainSkill) {
        this.mainSkills.add(mainSkill);
    }

    protected void setMainSkills(List<String> mainSkills) {
        this.mainSkills.addAll(mainSkills);
    }

    // for extension
    public void addNewSkill(String skill){
        this.skills.put(skill, 0);
    }

    /**
     * things happen when the hero levels up
     */
    private void levelUp() {
        while (this.exp >= this.expForLevelUp) {
            this.level++;
            System.out.println("~~~~~~~~~~~~~~~~~~~~");
            System.out.println(this.name + " levels up! (LV. "+ this.level + ")");
            this.expForLevelUp += this.level * Constant.LEVEL_UP_FACTOR;

            // if higher than reset HP recovery then keep the origin HP
            this.HP = Math.max(this.level * Constant.HP_FACTOR, this.HP);

            // MP recovery
            this.MP = (int) (this.MP * Constant.MP_FACTOR);

            // skill improve
            for (String skill : skills.keySet()){
                if (mainSkills.contains(skill)) {
                    skills.replace(skill, (int) (skills.get(skill) * Constant.MAIN_SKILL_FACTOR));
                } else {
                    skills.replace(skill, (int) (skills.get(skill) * Constant.VERSE_SKILL_FACTOR));
                }
            }
        }
        recalDamageAndDef();
    }

    /**
     * show the info of the Hero in the main menu
     */
    public void show() {
        System.out.println("-------------------------------------------");
        System.out.println("Hero " + this.name + "      LV." + this.level);
        System.out.println("EXP |  " + this.exp +"  /  "+ this.expForLevelUp + "  |");
        System.out.println(Constant.RED + "HP:" + Constant.RESET + this.HP+
                Constant.BLUE_BRIGHT + "       MP:" + Constant.RESET+ this.MP);
        System.out.println(Constant.YELLOW_BOLD_BRIGHT + "Money:" + Constant.RESET + this.money);
        System.out.println("Damage: " + this.damage + Constant.YELLOW_BRIGHT + "(+" + weaponDamage +")" + Constant.RESET +
                "     Defence: " + this.def + Constant.YELLOW_BRIGHT + "(+" + armorDef +")" + Constant.RESET);
        System.out.println("Dodge: " + this.dodge + "%");
        for(String skill : skills.keySet()){
            if (mainSkills.contains(skill)){
                System.out.println(Constant.BLUE_BRIGHT + skill + "(*): " + skills.get(skill) + Constant.RESET);
            } else {
                System.out.println(skill + " :" + skills.get(skill));
            }
        }
        System.out.println("-------------------------------------------");
        System.out.println("C/c: go back to the main menu");
        String out = scanner.next();
        while (!out.equalsIgnoreCase("c")){
            System.out.println("Invalid input");
            out = scanner.next();
        }
    }

    public void acquireExp(int exp) {
        this.exp += exp;
        levelUp();
    }

    @Override
    public String toString() {
        return "Hero{" +
                (status == Constant.NORMAL ? "" : "(faint)") +
                "name='" + name + '\'' +
                ", level=" + level +
                ", HP=" + HP +
                ", MP=" + MP +
                ", money=" + money +
                ", exp=" + exp +
                ", \n   skills=" + skills +
                ", damage=" + damage + Constant.YELLOW_BRIGHT + "(+" + weaponDamage +")" + Constant.RESET +
                ", def=" + def + Constant.YELLOW_BRIGHT + "(+" + armorDef +")" + Constant.RESET +
                ", dodge=" + dodge + "%" +
                '}'+ "\n";
    }

    @Override
    public boolean canBuy(Item item) {
        if(item instanceof Spell && !((Spell) item).canLearn(this)){
            // hero may have learned this spell
            return false;
        }
        if (money < item.getPrice()){
            System.out.println("You don't have enough money(Req: " + item.getPrice()+ " Your money: " + money + ")");
            return false;
        } else {
            return super.canBuy(item);
        }
    }

    @Override
    public void getHurt(int damage){
        super.getHurt(damage);
        if(HP == 0) {
            status = Constant.FAINT;
        }
    }

    public void recordState(){
        recordHP = HP;
        recordMP = MP;
    }

    /**
     * hero faint during the fight
     */
    public void recoverFromFaint(){
        System.out.println("Hero " + name + " recover from faint");
        HP = recordHP / Constant.RECOVER_FACTOR;
        status = Constant.NORMAL;
    }

    public void respawnFromFaint(){
        System.out.println("Hero " + name + "  respawns in the Nexus of their lane");
        HP = this.level * Constant.HP_FACTOR;;
        status = Constant.NORMAL;
    }

    public void recoverForTurn(){
        HP = recordHP / Constant.REGAIN_HP_FACTOR + HP;
        MP = recordMP / Constant.REGAIN_MP_FACTOR + MP;
    }

    /**
     * hero not faint util fight finished
     * @param level
     * @param exp
     */
    public void getTheReward(int level, int exp){
        System.out.println("Hero " + name + " get " + Constant.GAIN_MONEY_FACTOR * level + " money");
        money = money + Constant.GAIN_MONEY_FACTOR * level;
        System.out.println("Hero " + name + " get 2 exp");
        acquireExp(exp);
    }

    public void addToInventory(String type, Item good){
        money -= good.getPrice();
        inventory.add(type, good);
    }

    /**
     * show the equipment of the hero
     */
    public void checkEquipment(){
        System.out.println(Constant.DIVIDE);
        System.out.println("Hero " + name + "'s equipment:");
        equipment.show();
    }

    /**
     * show the inventory of the hero and can use things in the inventory
     */
    public void checkInventoryAndChangeEquipment(){
        inventory.show();
        printForChangeEquipment();

        String decision = scanner.next();
        while (!decision.equalsIgnoreCase("C")){
            if (decision.equalsIgnoreCase("T")){
                // take off the equipment
                System.out.println(Constant.DIVIDE);
                checkEquipment();
                equipment.takeoff(inventory);
            } else {
                int index = scanner.nextInt();
                Item item = inventory.removeItem(decision , index-1);
                item.use(this);
            }
            System.out.println(Constant.DIVIDE);
            checkEquipment();
            inventory.show();
            printForChangeEquipment();
            decision = scanner.next();
        }
    }

    public void setExploredTile(int exploredTile) {
        this.exploredTile = exploredTile;
    }

    public int getExploredTile() {
        return exploredTile;
    }

    public Equipment getEquipment(){
        return equipment;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public int getDamage() {
        return damage;
    }

    public int getWeaponDamage() {
        return weaponDamage;
    }

    public int getDef() {
        return def;
    }

    public int getArmorDef() {
        return armorDef;
    }

    public double getDodge() {
        return dodge;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public List<Spell> getLearnedSpell() {
        return learnedSpell;
    }

    public List<Item> getPotions() {
        return inventory.getPotion();
    }

    public List<Item> getWeaponAndArmor(){
        return inventory.getWeaponAndArmor();
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public void increaseSkill(String skill, int num) {
        skills.replace(skill, skills.get(skill) + num);
    }

    /**
     * recalculate damage, defence, dodge when equipment or skill changes
     */
    public void recalDamageAndDef(){
        damage = (int)Math.ceil((skills.get("strength")+this.extraStrength) * Constant.DAMAGE_FACTOR);
        weaponDamage = (int) Math.ceil(equipment.sumDamage() * Constant.DAMAGE_FACTOR);
        armorDef = equipment.sumDef();
        dodge = (skills.get("agility")+this.extraAgility) * Constant.DODGE_FACTOR;
    }

    /**
     * show the detail of the inventory and choose which item to sale
     */
    public void checkInventoryAndSale(){
        inventory.show();
        printForSale();

        String decision = scanner.next();
        while (!decision.equalsIgnoreCase("C")){
            int index = scanner.nextInt();
            Item i = inventory.removeItem(decision , index-1);
            System.out.println("You sale the " + i.getName());
            i.sale(this);
            System.out.println(Constant.DIVIDE);
            inventory.show();
            printForSale();
            decision = scanner.next();
        }
    }

    private void printForChangeEquipment(){
        System.out.println(Constant.DIVIDE);
        System.out.println("If you want to use the item, please enter the type of item and the index to use it:");
        System.out.println(Constant.RED + "eg: Potion 1" + Constant.RESET);
        System.out.println(Constant.DIVIDE);
        System.out.println("T/t : take off the equipment");
        System.out.println("C/c: back to the main menu");
    }

    private void printForSale(){
        System.out.println("If you want to sale the item, please enter the type of item and the index to sale it:");
        System.out.println("eg: Potion 1");
        System.out.println("C/c: back to the main menu");
    }

    public void learnSpell(Spell spell){
        learnedSpell.add(spell);
    }

    public void displayLearnedSkill(){
        for(int i = 0; i < learnedSpell.size(); i++){
            System.out.println((i+1) + ". " + learnedSpell.get(i));
        }
    }

    /**
     * display the info of hero during the fight
     * @return
     */
    public String toStringInBattle(){
        return "Hero{" +
                (status == Constant.NORMAL ? "" : "(faint)") +
                "name='" + name + '\'' +
                ", level=" + level +
                ", HP=" + HP +
                ", MP=" + MP +
                ", damage=" + damage + Constant.YELLOW_BRIGHT + "(+" + weaponDamage +")" + Constant.RESET +
                ", def=" + def + Constant.YELLOW_BRIGHT + "(+" + armorDef +")" + Constant.RESET +
                ", dodge=" + dodge + "%" +
                '}';
    }

    @Override
    public boolean tp(Position heroPosition,int row, int col) {
        if(this.exploredTile>row){
            return false;
        }else{
            heroPosition.setxPos(row);
            heroPosition.setyPos(col);
            return true;
        }
    }
}
