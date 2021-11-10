import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RandomMonsterCreator implements MonsterCreator{

    private static List<String> dragonList;
    private static List<String> exoskeletonList;
    private static List<String> spiritList;

    public RandomMonsterCreator(){
        if (dragonList == null){
            dragonList = Parser.parse("Dragon");
        }
        if (exoskeletonList == null){
            exoskeletonList = Parser.parse("Exoskeleton");
        }
        if (spiritList == null) {
            spiritList = Parser.parse("Spirit");
        }
    }

    @Override
    public Monster createMonster(int levelLimit){
        Random random = new Random();
        int type = random.nextInt(3);
        if(type == 0){
            // create a dragon
            return createFromList(dragonList, levelLimit);
        } else if (type == 1){
            // create a exoskeleton
            return createFromList(exoskeletonList, levelLimit);
        } else {
            // create a spirit
            return createFromList(spiritList, levelLimit);
        }
    }

    private Monster createFromList(List<String> data, int levelLimit){
        ArrayList<Monster> monsters = new ArrayList<>();
        for(String line: data){
            if(!line.isEmpty()){
                String[] detail = line.split("\\s+");
                if (detail.length != 5){
                    System.out.println("Wrong config!");
                    continue;
                }
                String name = detail[0];
                int level = Integer.parseInt(detail[1]);
                int baseDamage = Integer.parseInt(detail[2]);
                int defence = Integer.parseInt(detail[3]);
                int dodgeChance = Integer.parseInt(detail[4]);
                monsters.add(new Monster(name , level , baseDamage , defence , dodgeChance));
            }
        }

        // random generate a dragon with level equals levelLimit
        Predicate<Monster> byLevel = monster -> monster.getLevel() == levelLimit;
        List<Monster> filterMonster = monsters.stream().filter(byLevel)
                .collect(Collectors.toList());

        return filterMonster.get(new Random().nextInt(filterMonster.size()));
    }
}
