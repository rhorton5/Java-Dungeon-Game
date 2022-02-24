package dungeon;

import java.util.Scanner;

import character.PlayerCharacter;

public class Combat {
    private int level;
    public Combat(PlayerCharacter pc, int level, Scanner kb){
        this.level = level;
        combatMenu(kb,pc);
    }

    private void combatMenu(Scanner kb,PlayerCharacter pc){
        pc.rollInitiative(0);
        char choice = 0;
        do{
            System.out.println("[1] Attack [2] Magic [3] Items [4] Stats [q] Escape\nWhat would you like to do?");
            pc.printOverworldStats();
            choice = kb.next().charAt(0); kb.nextLine();
        }while(choice != 'q' && pc.isAlive());
    }
}
