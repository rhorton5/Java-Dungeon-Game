package character;

import java.util.Hashtable;

import enums.DamageType;
import interfaces.Damage;

public class Goblin extends TemplateCharacter implements Damage {

	public Goblin(String name) {
		super(name);
		Hashtable<String,Integer> attributes = new Hashtable<String,Integer>();
		attributes.put("HP", 25);
		attributes.put("MP", 0);
		attributes.put("Strength", 15);
		attributes.put("Dexterity", 17);
		attributes.put("Agility", 16);
		attributes.put("Constitution", 12);
		attributes.put("Resistance", 12);
		attributes.put("Intellect", 8);
		attributes.put("Wisdom", 8);
		attributes.put("Charisma", 10);
		attributes.put("Luck", 10);
		super.setAttributes(attributes);
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
	
	public static void main(String [] args) {
		Goblin g = new Goblin("Goblin");
		System.out.println(g.toString());
		
	}

}
