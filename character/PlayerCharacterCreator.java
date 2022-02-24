package character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

public class PlayerCharacterCreator {
    private Scanner kb;
    private Random r = new Random();
    public PlayerCharacterCreator(Scanner kb){
        this.kb = kb;
    }

    private ArrayList<Integer> rollDice(){
        ArrayList <Integer> result = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            result.add(r.nextInt(10)+20);
        }
        result.trimToSize();
        return result;
    }

    private void printAttributeScores(ArrayList <Integer> scores){
        String str = "" + scores.get(0);
        for(int i = 1; i < scores.size(); i++){
            str += "," + scores.get(i);
        }
        System.out.println(str);
    }

    private boolean isValidAttributeScore(ArrayList <Integer> scores, int chosenScore){
        return scores.indexOf(chosenScore) != -1;
    }

    public PlayerCharacter createCharacter(){
        String name;
        int att = 0;
        Hashtable <String,Integer> attributeHashtable = new Hashtable<String,Integer>();
        
        System.out.println("What is your name: ");
        name = kb.nextLine();
        
        ArrayList <Integer> scores = rollDice();
        
        System.out.println("");

        String Attributes [] = {"Strength", "Dexterity", "Constitution", "Spirit", "Intellect", "Luck"};
        for(String attName: Attributes) {
            do{
                printAttributeScores(scores);
                System.out.println("Enter your attribute score for " + attName + ": ");
                att = kb.nextInt(); kb.nextLine();
            }while(!isValidAttributeScore(scores,att));
        	
        	attributeHashtable.put(attName, att);
            scores.remove(scores.indexOf(att));
        }
        attributeHashtable.put("HP",attributeHashtable.get("Constitution") * attributeHashtable.get("Spirit") + 20);
        attributeHashtable.put("MP",(int)(attributeHashtable.get("Intellect")/5));

        char choice = ' ';
        PlayerCharacter pc = new PlayerCharacter(name,attributeHashtable);
        System.out.println(pc.toString());

        do{
            System.out.println("Do you wish to continue on your adventure?\n(y)es | (n)o\n");
            choice = kb.next().charAt(0); kb.nextLine();
        }while(choice != 'y' && choice != 'n');
        
        return (choice == 'y') ? pc : createCharacter(); 
    }
    
    public static void main(String [] args) {
    	PlayerCharacterCreator pcc = new PlayerCharacterCreator(new Scanner(System.in));
    	PlayerCharacter pc = pcc.createCharacter();
    	System.out.println(pc.toString());
    }
}
