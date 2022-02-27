package character;

import java.util.Hashtable;

import enums.DamageType;
import interfaces.Damage;

public class Goblin extends TemplateCharacter implements Damage {

	private int STR = 12, DEX = 15, CON = 8, SPR = 6, INT = 10, LCK = 10;
	public Goblin(String name) {
		super(name);
		Hashtable<String,Integer> attributes = new Hashtable<String,Integer>();
		attributes.put("HP", 25);
		attributes.put("MP", 0);
		attributes.put("Strength", STR);
		attributes.put("Dexterity", DEX);
		attributes.put("Constitution", CON);
		attributes.put("Spirit", SPR);
		attributes.put("Intellect", INT);
		attributes.put("Luck", LCK);
		super.setAttributes(attributes);
		super.setEXP(150);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(TemplateCharacter o) {
		if(o.getInitaitive() != this.getInitaitive())
			return this.getInitaitive() - o.getInitaitive();
		return -1;
	}

	@Override
	public double getElementalResistance(DamageType type) {
		switch(type.name().toLowerCase()){
			case "fire": return -0.5;
			case "poison": return 0.5;
		}
		return 0;
	}
}
