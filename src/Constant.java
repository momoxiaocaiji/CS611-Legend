import java.util.HashMap;
import java.util.Map;

public class Constant {
    public static final int HP_FACTOR = 100;
    public static final double MP_FACTOR = 1.1;
    public static final int LEVEL_UP_FACTOR = 10;
    public static final double MAIN_SKILL_FACTOR = 1.1;
    public static final double VERSE_SKILL_FACTOR = 1.05;
    public static final int RECOVER_FACTOR = 2;
    public static final int REGAIN_HP_FACTOR = 10;
    public static final int REGAIN_MP_FACTOR = 10;
    public static final int GAIN_MONEY_FACTOR = 100;
    public static final int GAIN_EXP = 2;
    public static final double DAMAGE_FACTOR = 0.05;
    public static final double DODGE_FACTOR = 0.002;
    public static final int SPELL_DAMAGE_FACTOR = 10000;
    public static final double DETERIORATION_FACTOR = 0.9;
    public static final double DEFENCE_RATE = 0.2;
    public static final int FIGHT_EXP = 2;




    // character status
    public static final int NORMAL = 1;
    public static final int FAINT = 2;
    public static final int DEAD = 3;

    //divide
    public static final String DIVIDE = "--------------------------------------";


    //color
    public static final String RESET = "\033[0m";
    public static final String BACKGROUND_RED = "\033[0;101m";
    public static final String BLACK_BACKGROUND ="\033[40m";
    public static final String PURPLE_BACKGROUND ="\033[45m";
    public static final String RED ="\033[0;31m";
    public static final String GREEN ="\033[0;32m";
    public static final String BLUE ="\033[0;34m";
    public static final String YELLOW ="\033[0;33m";
    public static final String PURPLE ="\033[0;35m";
    public static final String BLUE_BRIGHT ="\033[0;94m";
    public static final String YELLOW_BRIGHT ="\033[0;93m";
    public static final String YELLOW_BOLD_BRIGHT ="\033[1;93m";
    public static final String WHITE_BOLD_BRIGHT ="\033[1;97m";


    //file map
    public static final Map<String, String> FILE_MAP = new HashMap<String, String>(){
        {
            //Item
            put("Armor", "Armory");
            put("Weapon", "Weaponry");
            put("Potion", "Potions");
            //Spell
            put("FireSpell", "FireSpells");
            put("IceSpell", "IceSpells");
            put("LightningSpell", "LightningSpells");
            //Hero
            put("Warrior", "Warriors");
            put("Sorcerer", "Sorcerers");
            put("Paladin", "Paladins");
            //Monster
            put("Dragon", "Dragons");
            put("Exoskeleton", "Exoskeletons");
            put("Spirit", "Spirits");
        }
    };
}
