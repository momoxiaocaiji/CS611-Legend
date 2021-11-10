import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sorcerer extends Hero{

    public Sorcerer(String name, int MP, int strength, int agility, int dexterity, int money, int exp){
        super();
        this.name = name;
        this.MP = MP;
        this.skills.replace("strength", strength);
        this.skills.replace("agility", agility);
        this.skills.replace("dexterity", dexterity);
        this.money = money;
        this.exp = exp;
        setMainSkills(Arrays.asList("dexterity", "agility"));
        recalDamageAndDef();
    }

    public Sorcerer(){
        this("Rillifane_Rallathil", 1300, 750, 450, 500, 2500, 9);
    }

    public static List<Sorcerer> createSorcerer(List<String> data) {
        List<Sorcerer> sorcerers = new ArrayList<>();
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
                sorcerers.add(new Sorcerer(name , MP , strength , agility , dexterity , money , exp));
            }
        }
        return sorcerers;
    }
}
