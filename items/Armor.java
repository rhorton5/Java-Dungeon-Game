package items;

import java.util.Hashtable;

import enums.DamageType;
import interfaces.Damage;

public class Armor extends Items implements Damage{
	private int physicalDefense, magicDefense;
	private double fireRes, waterRes, earthRes, windRes, iceRes, electricRes, poisonRes, acidRes,
					slagRes, psychicRes, lightRes, darkRes, bludgeonRes, slashRes, pierceRes;
	public Armor(String name, double weight, int value, Hashtable<String,Integer> attributes, Hashtable<String,Double> elementalRes) {
		super(name,weight,value);
		this.physicalDefense = attributes.get("physical defense");
		this.magicDefense = attributes.get("magic defense");
		SetElementalResistance(elementalRes);
	}
	
	private void SetElementalResistance(Hashtable<String,Double> elementalResistance) {
		fireRes = elementalResistance.getOrDefault("fire", 0.0);
		waterRes = elementalResistance.getOrDefault("water", 0.0);
		earthRes = elementalResistance.getOrDefault("earth", 0.0);
		windRes = elementalResistance.getOrDefault("wind", 0.0);
		iceRes = elementalResistance.getOrDefault("ice", 0.0);
		electricRes = elementalResistance.getOrDefault("electric", 0.0);
		poisonRes = elementalResistance.getOrDefault("poison", 0.0);
		acidRes = elementalResistance.getOrDefault("acid", 0.0);
		slagRes = elementalResistance.getOrDefault("slag", 0.0);
		psychicRes = elementalResistance.getOrDefault("psychic", 0.0);
		lightRes = elementalResistance.getOrDefault("light", 0.0);
		darkRes = elementalResistance.getOrDefault("dark", 0.0);
		bludgeonRes = elementalResistance.getOrDefault("bludgeon", 0.0);
		slashRes = elementalResistance.getOrDefault("slash", 0.0);
		pierceRes = elementalResistance.getOrDefault("pierce", 0.0);
	}
	
	public int getPhysicalDefense() {
		return physicalDefense;
	}
	
	public int getMagicDefense() {
		return magicDefense;
	}
	
	@Override
	public String toString() {
		return super.toString() + String.format("Physical Defense: %d\nMagical Defense: %d\n", physicalDefense,magicDefense);
	}	

	public int takeDamage(int dmg, DamageType type) {
		if(type != DamageType.SLASHING && type != DamageType.PIERCING && type != DamageType.BLUDGEON)
			dmg -= magicDefense;
		else
			dmg -= physicalDefense;
		return (int)(dmg * (1 - getElementalResistance(type)));
	}
	
	public double getElementalResistance(DamageType type) {
		switch(type.name().toLowerCase()) {
		case "fire": return fireRes;
		case "water": return waterRes;
		case "earth": return earthRes;
		case "wind": return windRes;
		case "ice": return iceRes;
		case "poison": return poisonRes;
		case "acid": return acidRes;
		case "electric": return electricRes;
		case "slag": return slagRes;
		case "light": return lightRes;
		case "dark": return darkRes;
		case "slashing": return slashRes;
		case "bludgeon": return bludgeonRes;
		case "piercing": return pierceRes;
		case "psychic": return psychicRes;
		}
		return 0;
	}
	
	public static void main(String [] args) {
		Hashtable <String, Integer> armorAtt = new Hashtable<String, Integer>();
		armorAtt.put("physical defense", 5);
		armorAtt.put("magic defense", 3);
		Hashtable <String,Double> elementalAtt = new Hashtable<String, Double>();
		elementalAtt.put("fire", 0.25);
		Armor armor = new Armor("Chest Vest",14,140,armorAtt,elementalAtt);
		System.out.println(armor.toString());
		System.out.println(armor.takeDamage(20,DamageType.FIRE));
		System.out.println(armor.getName());
	}
}
