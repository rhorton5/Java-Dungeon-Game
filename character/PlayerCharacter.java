package character;

import java.util.Hashtable;

import enums.DamageType;
import interfaces.Damage;
import magic.Spells;

public class PlayerCharacter extends TemplateCharacter implements Damage {

	private int posX = 0, posY = 0; //Where the hero is in the dungeon?
	
	public PlayerCharacter(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public PlayerCharacter(String name, Hashtable<String, Integer> attributes) {
		super(name, attributes);
		// TODO Auto-generated constructor stub
	}
	
	public void moveCharacter(int x, int y) {
		posX += x;
		posY += y;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void printOverworldStats() {
		if(super.isAlive()) {
			int hp = super.getHP();
			int maxHP = super.getMaxHP();
			int totalHPBar = maxHP / 20 + 1;
			int fullHPBar = (hp == maxHP) ? totalHPBar : hp / 20;
			int emptyHPBar = totalHPBar - fullHPBar;
			System.out.printf("HP: [%s%s] [%d/%d]\n","=".repeat(fullHPBar),"-".repeat(emptyHPBar),hp,maxHP);
		}
		
	}
	
	public void printSpells() {
		for(Spells s: super.getSpells()) {
			s.PrintSpellMenuOption();
		}
	}

	@Override
	public int compareTo(TemplateCharacter o) {
		// TODO Auto-generated method stub
		return this.getInitaitive() - o.getInitaitive();
	}

	@Override
	public double getElementalResistance(DamageType type) {
		// TODO Auto-generated method stub
		return 0;
	}

}
