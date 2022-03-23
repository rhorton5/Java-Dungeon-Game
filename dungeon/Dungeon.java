package dungeon;
import java.util.Random;
import java.util.Scanner;

import character.PlayerCharacter;
public class Dungeon {
	private final char wall = 'x', tile = '-', heroTile = 'H', exit = 'E';
	private int startPosX, startPosY, turnsSinceCombat = 0;
	private PlayerCharacter pc;
	private char [][] map;
	private boolean completedDungeon = false;
	
	public Dungeon(char [][] map, int startPosX, int startPosY, PlayerCharacter pc) {
		this.map = map;
		this.startPosX = startPosX;
		this.startPosY = startPosY;
		this.pc = pc;
		this.pc.moveCharacter(startPosX, startPosY);
		map[startPosY][startPosX] = heroTile;
	}
	
	
	public void printDungeon() {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println("");
		}
	}
	
	public void movePC(int x, int y) {
		int oldPCX = pc.getPosX();
		int oldPCY = pc.getPosY();
		int pcX = oldPCX + x;
		int pcY = oldPCY + y;
		if(pcX >= map.length || pcY >= map[0].length) {
			System.out.println("You cannot move there.");
		}else if(map[pcY][pcX] == wall) {
			System.out.println("There's a wall there...");
		}
		else {
			completedDungeon = map[pcY][pcX] == exit;
			map[pcY][pcX] = heroTile;
			map[oldPCY][oldPCX] = tile;
			pc.moveCharacter(x, y);
		}
			
	}
	
	public boolean startRandomEncounter() {
		if(turnsSinceCombat >= 5) {
			Random r = new Random();
			double encounterRate = 0.05 * (turnsSinceCombat - 4);
			boolean startCombat = r.nextDouble() + encounterRate >= 1.00;
			if(startCombat) {
				turnsSinceCombat = 0;
				return true;
			}
		}
		turnsSinceCombat++;
		return false;
	}
	
	public void dungeonOverworldMenu(Scanner kb) {
		
		char choice = 0;
		do {
			if(startRandomEncounter()) {
				Combat combat = new Combat(pc,1,kb); //Could be written better.  Make a method to initialize combat.
			}
			if(completedDungeon){
				System.out.println("Congradulation, you have made it through the dungeon!!!");
				return;
			}
			if(pc.isAlive()) {
				printDungeon();
				pc.printOverworldStats();
				System.out.println("Where would you to go adventurer?\n(w) Up  (s) Down (d) Right (a) Left\n(m) View Spells (q) Quit Game");
				choice = kb.next().charAt(0); kb.nextLine();
				switch(choice) {
				case 'w': movePC(0,-1); break;
				case 's': movePC(0,1); break;
				case 'd': movePC(1,0); break;
				case 'a': movePC(-1,0); break;
				case 'm': pc.printSpells(); break;
				case 'v': System.out.println(pc.toString() + "\nPress enter to continue..."); break;
				}
			}else
				System.out.println("You have perished...");
		}while(choice != 'q' && pc.isAlive());
	}
}
