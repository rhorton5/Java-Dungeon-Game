package dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

import character.Goblin;
import character.PlayerCharacter;
import character.TemplateCharacter;
import enums.DamageType;

public class Combat {
    private int level;
    private ArrayList <TemplateCharacter> characterList = new ArrayList<>();
    private PlayerCharacter pc;
    private Random r = new Random();
    public Combat(PlayerCharacter pc, int level, Scanner kb){
        this.level = level;
        this.pc = pc;
        combatMenu(kb,pc);
    }

    private void spawnEnemies(){
        for(int i = 0; i < 3; i++){
            characterList.add(
                new Goblin(String.format("Goblin #%d",i))
            );
        }
    }

    private void rollInitiative(){
        for(TemplateCharacter c: characterList){
            c.rollInitiative(0);
        }
    }

    private void displayTurnOrder(){
        for(TemplateCharacter c: characterList){
            System.out.printf("[%d] %s\n",c.getInitaitive(),c.getName());
        }
    }

    private void endTurn(){
        TemplateCharacter c = characterList.remove(0);
        characterList.add(c);
    }

    private void attackMenu(Scanner kb){
        System.out.println("Who would you like to attack: ");
        String targetName = kb.nextLine();
        int targetIndex = -1;
        for(int i = 0; i < characterList.size(); i++){
            if(targetName.equals(characterList.get(i).getName())){
                targetIndex = i;
            }
        }
        if(targetIndex == -1)
            return;
        
        meleeAttack(pc,characterList.get(targetIndex));
    }

    private void meleeAttack(TemplateCharacter attacker, TemplateCharacter target){
        int diceRoll = r.nextInt(19) + 1;
        int attackScore = diceRoll + pc.getMeleeAttackRollModifiers(0);
        if(attackScore >= target.getArmorClass(false) || diceRoll == 20){
            int dmg = pc.dealDamage(diceRoll == 20);
            DamageType damageType = pc.getWeaponDamageType();
            targetTakesDamage(target,dmg,damageType);
        }else
            System.out.println(target.getName() + " dodged your attack...");
    }

    private void targetTakesDamage(TemplateCharacter target,int dmg, DamageType damageType){
        dmg = target.takeDamage(dmg,damageType); 
        System.out.printf("%s takes %d damage!!\n",target.getName(),dmg);
         if(!target.isAlive()){
            characterList.remove(target);
            System.out.println(target.getName() + " has been slained!");
         }  
    }

    private int getEnemyEXP(){
        Stream<TemplateCharacter> enemyList = characterList.stream().filter(enemy -> enemy != this.pc);
        return enemyList.mapToInt(e -> e.getEXP()).sum();
    }

    private void combatMenu(Scanner kb,PlayerCharacter pc){
        char choice = 0;
        spawnEnemies();
        characterList.add(pc);
        rollInitiative();
        Collections.sort(characterList);
        int winningEXP = getEnemyEXP();
        boolean turnMade = false;
        do{
            System.out.println("Winning EXP: " + winningEXP);
            displayTurnOrder();
            System.out.println("[1] Attack [2] Magic [3] Items [4] Stats [q] Escape\nWhat would you like to do?");
            pc.printOverworldStats();
            choice = kb.next().charAt(0); kb.nextLine();
            switch(choice){
                case '1': turnMade = true; attackMenu(kb); break;
                case 2: turnMade = true; break;
                case 3: turnMade = true; break;
                case 4: break;
            }
            if(turnMade)
                endTurn();
        }while(choice != 'q' && pc.isAlive() && characterList.size() > 1);
        
        if(characterList.size() == 1){
            System.out.println("You were victorious!!");
            pc.addEXP(winningEXP);
        }
    }
}
