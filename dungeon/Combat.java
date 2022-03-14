package dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Stream;

import character.Cultist;
import character.Enemy;
import character.Goblin;
import character.PlayerCharacter;
import character.TemplateCharacter;
import enums.DamageType;
import magic.AttackSpell;
import magic.HealingSpell;
import magic.Spells;

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
        for(int i = 0; i < 2; i++){
            characterList.add(
                new Cultist(String.format("Cultist #%d",i))
            );
        }
    }

    private void rollInitiative(){
        characterList.stream().forEach(c -> c.rollInitiative(0));
    }

    private void displayTurnOrder(){
        characterList.stream().forEach(
            c -> System.out.printf("[%d] %s\n",c.getInitaitive(),c.getName())
        );
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

    private void magicMenu(Scanner kb){
        pc.printSpells();
        
        Spells chosenSpell = null;
        do{
            System.out.println("Select your spell: ");
            String spellName = kb.nextLine();
            chosenSpell = pc.getSpell(spellName);
        }while(chosenSpell == null);
        if(pc.castSpell(chosenSpell)){
            if(chosenSpell.getSpellType().equals("Attack")){
                //Refactor later.
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
                magicAttack(pc,(AttackSpell) chosenSpell,characterList.get(targetIndex));
            }if(chosenSpell.getSpellType().equals("Healing")){
                HealingSpell hs = (HealingSpell) chosenSpell;
                int hp = hs.restoreHP(pc.getMaxHP(), pc.getSpirit());
                pc.recoverHP(hp,false);
                System.out.printf("%s has restored %d health!!\n",pc.getName(),hp);
            }
        }else{
            System.out.println("You cannot cast this spell.");
        }
    }

    private void meleeAttack(TemplateCharacter attacker, TemplateCharacter target){
        int diceRoll = r.nextInt(19) + 1;
        int attackScore = diceRoll + pc.getMeleeAttackRollModifiers(0);
        if(attackScore >= target.getArmorClass(false) || diceRoll == 20){
            int dmg = pc.dealDamage(diceRoll == 20);
            DamageType damageType = pc.getWeaponDamageType();
            targetTakesDamage(target,dmg,damageType);
        }else
            System.out.println(target.getName() + " dodged " + attacker.getName() + "'s attack...");
    }

    private void magicAttack(TemplateCharacter attacker, AttackSpell spell, TemplateCharacter target){
        int diceRoll = r.nextInt(19) + 1;
        int attackScore = diceRoll + pc.getMagicAttackModifiers(0);
        if(attackScore >= target.getArmorClass(false) || diceRoll == 20){
            int dmg = spell.dealDamage(diceRoll == 20) + attacker.getIntellect();
            DamageType damageType = spell.getDamageType();
            targetTakesDamage(target,dmg,damageType);
        }else
            System.out.println(target.getName() + " dodged " + attacker.getName() + "'s attack...");
    }

    private void targetTakesDamage(TemplateCharacter target,int dmg, DamageType damageType){
        dmg = target.takeDamage(dmg,damageType); 
        System.out.printf("%s takes %d %s damage!!\n",target.getName(),dmg,damageType.name().toLowerCase());
         if(!target.isAlive()){
            characterList.remove(target);
            System.out.println(target.getName() + " has been slained!");
         }  
    }

    private int getEnemyEXP(){
        Stream<TemplateCharacter> enemyList = characterList.stream().filter(enemy -> enemy != this.pc);
        return enemyList.mapToInt(e -> e.getEXP()).sum();
    }

    private void enemyFlee(Enemy target){
        characterList.remove(target);
        System.out.printf("%s has cowardly left the battle...",target.getName());
    }

    private void decideEnemyAttack(){
        Enemy attacker = (Enemy) characterList.get(0);
        String action = attacker.decideAction();
        switch(action){
            case "weapon": meleeAttack(attacker,pc); break;
            case "flee": enemyFlee(attacker); break;
        }
        if(action.contains("spell - ")){
            String spellName = action.substring(action.indexOf('-')+1).trim();
            Spells spell = attacker.getSpell(spellName);
            if(spell.getSpellType().equals("Attack")){
                magicAttack(attacker,(AttackSpell) spell,pc);
            }else if(spell.getSpellType().equals("Healing")){
                HealingSpell hs = (HealingSpell) spell;
                int hp = hs.restoreHP(attacker.getMaxHP(), attacker.getSpirit());
                attacker.recoverHP(hp, false);
                System.out.printf("%s has recovered %d health!!\n",attacker.getName(),hp);
            }
        }
        endTurn();
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
            if(characterList.get(0) != pc){
                decideEnemyAttack();
            }else{
                while(pc.hasTurnsLeft()){
                    displayTurnOrder();
                    pc.printOverworldStats();
                    System.out.println("[1] Attack [2] Magic [3] Items [4] Stats [q] Escape\nWhat would you like to do?");
                    choice = kb.next().charAt(0); kb.nextLine();
                    switch(choice){
                        case '1': turnMade = true; attackMenu(kb); break;
                        case '2': turnMade = true; magicMenu(kb); break;
                        case '3': turnMade = true; break;
                        case '4': break;
                }
            }
            if(turnMade)
                endTurn();
            }  
        }while(choice != 'q' && pc.isAlive() && characterList.size() > 1);
        
        if(characterList.size() == 1){
            System.out.println("You were victorious!!");
            pc.addEXP(winningEXP);
            
        }
    }
}
