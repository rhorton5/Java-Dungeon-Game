package dungeon;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

import character.PlayerCharacter;
import enums.DamageType;
import magic.AttackSpell;
import magic.Spells;
import maps.MapLoader;

public class Dungeon {
	private final char wall = 'x', tile = '-', heroTile = 'H';
	private int startPosX, startPosY, turnsSinceCombat = 0;
	private PlayerCharacter pc;
	private char [][] map;
	
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
			map[pcY][pcX] = heroTile;
			map[oldPCY][oldPCX] = tile;
			pc.moveCharacter(x, y);
		}
			
	}
	
	public boolean startRandomEncounter() {
		/*if(turnsSinceCombat >= 5) {
			Random r = new Random();
			double encounterRate = 0.05 * (turnsSinceCombat - 4);
			boolean startCombat = r.nextDouble() + encounterRate >= 1.00;
			if(startCombat) {
				turnsSinceCombat = 0;
				return true;
			}
		}
		turnsSinceCombat++;
		return false;*/
		return true;
		
	}
	
	public void dungeonOverworldMenu(Scanner kb) {
		
		char choice = 0;
		Combat combat;
		do {
			if(startRandomEncounter()) {
				combat = new Combat(pc,1,kb); //Could be written better.  Make a method to initialize combat.
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
				}
			}else {
				System.out.println("You have perished...");
				return;
			}
				
		}while(choice != 'q' && pc.isAlive());
	}
	public static void main(String [] args) throws FileNotFoundException {
		MapLoader mp = new MapLoader();
		char [] [] map = mp.loadMap("test2.DUNGEON");
		Hashtable <String, Integer> att = new Hashtable<String,Integer>();
		att.put("HP", 999);
		att.put("MP", 5);
		att.put("Strength", 12);
		att.put("Dexterity", 12);
		att.put("Agility", 12);
		att.put("Constitution", 12);
		att.put("Resistance", 12);
		att.put("Intellect", 12);
		att.put("Wisdom", 12);
		att.put("Charisma", 12);
		att.put("Luck", 12);
		ArrayList<Spells> spellList = new ArrayList<Spells>();
		spellList.add(new AttackSpell("Fireball",1,15,25,1.5,DamageType.FIRE));
		spellList.add(new AttackSpell("Thunder",1,15,25,1.5,DamageType.ELECTRIC));
		PlayerCharacter pc = new PlayerCharacter("Name",att);
		pc.setSpells(spellList);
		Dungeon d = new Dungeon(map,1,2,pc);
		Scanner kb = new Scanner(System.in);
		d.dungeonOverworldMenu(kb);
		kb.close();
	}
	
	
}
