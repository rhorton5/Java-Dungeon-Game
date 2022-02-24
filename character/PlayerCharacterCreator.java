package character;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;
import character.PlayerCharacter;
public class PlayerCharacterCreator {
    private Scanner kb;
    private Random r = new Random();
    public PlayerCharacterCreator(Scanner kb){
        this.kb = kb;
    }

    private ArrayList<Integer> rollDice(){
        ArrayList <Integer> result = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            result.add(r.nextInt(10)+8);
        }
        result.trimToSize();
        return result;
    }

    public PlayerCharacter createCharacter(){
        String name;
        int att = 0;
        Hashtable <String,Integer> attributeHashtable = new Hashtable<String,Integer>();
        
        System.out.println("What is your name: ");
        name = kb.nextLine();
        
        //Do stuff
        ArrayList <Integer> scores = rollDice();
        for(int i = 0; i < 6; i++){
            System.out.println(scores.get(i));
        }
        String Attributes [] = {"Strength", "Dexterity", "Constitution", "Spirit", "Intellect", "Luck"};
        for(String attName: Attributes) {
        	System.out.println("Enter your attribute score for " + attName + ": ");
        	att = kb.nextInt(); kb.nextLine();
        	attributeHashtable.put(attName, att);
        }
        attributeHashtable.put("HP",attributeHashtable.get("Constitution") * attributeHashtable.get("Spirit") + 20);
        attributeHashtable.put("MP",(int)(attributeHashtable.get("Intellect")/5));
        
        return new PlayerCharacter(name,attributeHashtable);
    }
    
    public static void main(String [] args) {
    	PlayerCharacterCreator pcc = new PlayerCharacterCreator(new Scanner(System.in));
    	PlayerCharacter pc = pcc.createCharacter();
    	System.out.println(pc.toString());
    }
}
