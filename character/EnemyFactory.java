package character;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EnemyFactory {
    private Enemy createEnemy(String enemyName){
        switch(enemyName.toLowerCase()){
            case "cultist": return new Cultist("Cultist");
            case "goblin": return new Goblin("Goblin");
        }
        return null;
    } 

    private String [] getEnemyList(int level){
        switch(level){
            case 1: return new String[]{"Cultist","Goblin"};
        }
        return null;
    }

    public ArrayList<Enemy> createEnemyParty(int level){
        ArrayList<Enemy> enemyList = new ArrayList<>();
        String [] lvlEnemyList = getEnemyList(level);
        for(int i = 0; i < 3; i++){
            Collections.shuffle(Arrays.asList(lvlEnemyList));
            enemyList.add(createEnemy(lvlEnemyList[0]));
        }
        enemyList.trimToSize();
        return enemyList;
    }
}
