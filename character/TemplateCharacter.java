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
	private Armor armor;
	private Weapon wpn;
	private ArrayList <Spells> spellList = new ArrayList<Spells>();
	private int exp;
	
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
	
	public void setArmor(Armor armor) {
		this.armor = armor;
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
	public int getSpirit(){return SPR;}
	
	public boolean isAlive() {
		return HP > 0;
	}
	
	public int dealDamage(boolean criticalHit) {
		if(wpn == null)
			return 0;
		return wpn.dealDamage(STR, criticalHit);
	}
	
	public int takeDamage(int dmg, DamageType type) {
		dmg = (armor != null) ? armor.takeDamage(dmg,type) : dmg; //Check if armor is equipped.  Resolve that first.
		if(type != DamageType.SLASHING && type != DamageType.PIERCING && type != DamageType.BLUDGEON) //Magic attacks use Spirit to reduce damage
			dmg -= SPR;
		else  //Physical attacks use Constitution to reduce damage
			dmg -= CON;
		if(dmg < 0) //If damage is less than 0, then dmg is 0 (otherwise, HP is restored)
			dmg = 0;
		
		HP -= dmg;
		return dmg;
		
	}
	
	public void rollInitiative(int modifiers) {
		initiative = r.nextInt(19) + 1 + getModifiers(DEX) + modifiers; //d20 + DEX + modifiers
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

	public int getMeleeAttackRollModifiers(int modifiers){
		return getModifiers(STR) + modifiers;
	}

	public int getMagicAttackModifiers(int modifiers){
		return getModifiers(INT) + modifiers;
	}

	public String getName(){
		return name;
	}

	public String getWeaponName(){
		return (wpn != null) ? wpn.getName() : "Unarmed";
	}

	public DamageType getWeaponDamageType(){
		return wpn.getDamageType();
	}

	public void setEXP(int exp){
		this.exp = exp;
	}

	public int getEXP(){
		return exp;
	}

	public Spells getSpell(String spellName){
		spellName = spellName.toLowerCase();
		for(Spells s: spellList){
			if(spellName.equals(s.getName().toLowerCase()))
				return s;
		}
		return null;
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
