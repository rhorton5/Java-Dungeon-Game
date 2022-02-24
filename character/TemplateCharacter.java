package character;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import enums.DamageType;
import interfaces.Damage;
import items.Armor;
import weapon.Weapon;
import magic.Spells;

public abstract class TemplateCharacter implements Comparable<TemplateCharacter>, Damage {
	private int HP, maxHP, MP, maxMP;
	private int STR, DEX, CON, SPR, INT, LCK;
	private String name;
	private final Random r = new Random();
	private Armor chest, head, rArm, lArm, rLeg, lLeg;
	private Weapon wpn;
	private ArrayList <Spells> spellList = new ArrayList<Spells>();
	
	private int initiative = 0;
	
	public TemplateCharacter(String name) {
		this.name = name;
	}
	
	void setAttributes(Hashtable <String,Integer> attributes) {
		this.HP = attributes.get("HP");
		this.MP = attributes.get("MP");
		this.STR = attributes.get("Strength");
		this.DEX = attributes.get("Dexterity");
		this.CON = attributes.get("Constitution");
		this.SPR = attributes.get("Spirit");
		this.INT = attributes.get("Intellect");
		this.LCK = attributes.get("Luck");
		this.maxHP = HP;
		this.maxMP = MP;
	}
	
	public TemplateCharacter(String name, Hashtable <String,Integer> attributes) {
		this.name = name;
		setAttributes(attributes);
	}
	
	public int getModifiers(int att) {
		return (int)((att-10)/2);
	}
	
	public int getArmorClass(boolean flatFooted) {
		int dexMod = getModifiers(DEX);
		return dexMod + 10;
		
	}
	
	public void setWeapon(Weapon wpn) {
		this.wpn = wpn;
	}
	
	public void setArmor(String location, Armor armor) {
		switch(location.toLowerCase()) {
		case "head": head = armor; break;
		case "chest": chest = armor; break;
		case "rarm": rArm = armor; break;
		case "larm": lArm = armor; break;
		case "lleg": lLeg = armor; break;
		case "rleg": rLeg = armor; break;
		}
	}
	
	private Armor getHitLocation() {
		int roll = r.nextInt(9);
		//0-1 head, 2-5 chest, 6 lArm, 7 rArm, 8 lLeg, 9 rLeg
		if(roll <= 1)
			return head;
		if(roll >= 2 && roll < 6)
			return chest;
		if(roll == 6)
			return lArm;
		if(roll == 8)
			return rArm;
		if(roll == 9)
			return lLeg;
		else
			return rLeg;
	}
	
	public int getHP() {return this.HP;}
	public int getMP() {return this.MP;}
	public int getMaxHP() {return maxHP;}
	public int getMaxMP() {return maxMP;}
	public int getStrength() { return STR;}
	public int getDexterity() {return DEX;}
	public int getConstitution() {return CON;}
	public int getIntellect() {return INT;}
	public int getLuck() {return LCK;}
	
	public boolean isAlive() {
		return HP > 0;
	}
	
	public int dealDamage(boolean criticalHit) {
		if(wpn == null)
			return 0;
		return wpn.dealDamage(STR, criticalHit);
	}
	
	public int takeDamage(int dmg, DamageType type) {
		if(getHitLocation() != null) dmg = chest.takeDamage(dmg, type);
		if(type != DamageType.SLASHING && type != DamageType.PIERCING && type != DamageType.BLUDGEON) 
			dmg -= SPR;
		else 
			dmg -= CON;
		HP -= dmg;
		return dmg;
		
	}
	
	public void rollInitiative(int modifiers) {
		initiative = r.nextInt(19) + 1 + getModifiers(DEX) + modifiers;
	}
	
	public int getInitaitive() {
		return initiative;
	}
	
	public int recoverHP(int points,boolean overheal) {
		HP += points;
		if(HP > maxHP && !overheal) HP = maxHP;
		return points;
	}
	
	ArrayList<Spells> getSpells(){return spellList;}
	
	public void setSpells(ArrayList<Spells> spellList) {
		this.spellList = spellList;
	}

	@Override
	public String toString(){
		String str = String.format("%s%s%s\n","-".repeat(50),name,"-".repeat(50));
		str += String.format("HP: %d/%d\nMP: %d/%d\n",HP,maxHP,MP,maxMP);
		str += String.format("Strength: %d [%d]\nDexterity: %d [%d]\nConstitituion: %d [%d]\nSpirit: %d [%d]\nIntellect: %d [%d]\nLuck: %d [%d]",
		STR, getModifiers(STR), DEX, getModifiers(DEX), CON, getModifiers(CON), SPR, getModifiers(SPR), INT, getModifiers(INT), LCK, getModifiers(LCK)
		);
		return str;
	}
	
}
