import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Warrior extends Hero{

    public Warrior(String name, int MP, int strength, int agility, int dexterity, int money, int exp){
        super();
        this.name = name;
        this.MP = MP;
        this.skills.replace("strength", strength);
        this.skills.replace("agility", agility);
        this.skills.replace("dexterity", dexterity);
        this.money = money;
        this.exp = exp;
        setMainSkills(Arrays.asList("strength", "agility"));
        recalDamageAndDef();
    }

    public Warrior(){
        this("Gaerdal_Ironhand", 100, 700, 500, 600, 1354, 7);
    }

    public static List<Warrior> createWarriors(List<String> data) {
        List<Warrior> warriors = new ArrayList<>();
        for(String line: data){
            if(!line.isEmpty()){
                String[] detail = line.split("\\s+");
                if (detail.length != 7){
                    System.out.println("Wrong config!");
                    continue;
                }
                String name = detail[0];
                int MP = Integer.parseInt(detail[1]);
                int strength = Integer.parseInt(detail[2]);
                int agility = Integer.parseInt(detail[3]);
                int dexterity = Integer.parseInt(detail[4]);
                int money = Integer.parseInt(detail[5]);
                int exp = Integer.parseInt(detail[6]);
                warriors.add(new Warrior(name , MP , strength , agility , dexterity , money , exp));
            }
        }
        return warriors;
    }
}
