package weapon;
import java.util.Random;

import enums.DamageType;
import items.Items;

public class Weapon extends Items{
	private DamageType damageType;
	private int minDamage, maxDamage;
	private double critModifier;
	private Random r = new Random();
	
	public Weapon(String name, int minDamage, int maxDamage, DamageType damageType, double critModifier, double weight, int value) {
		super(name,weight,value);
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.critModifier = critModifier;
		this.damageType = damageType;
	}
	
	public int dealDamage(int attributeScore, boolean criticalHit) {
		int dmgDiff = maxDamage - minDamage;
		int dmg = r.nextInt(dmgDiff) + minDamage + attributeScore;
		return (criticalHit) ? (int)(dmg * critModifier) : dmg;
	}
	
	public DamageType getDamageType() {
		return damageType;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Weapon rapier = new Weapon("Rapier",5,15,DamageType.PIERCING,2,5,35);
		for(int i = 0; i < 10; i++)
			System.out.println(rapier.getName() + " Dealt " + rapier.dealDamage(18, false) + " Damage!!");

	}

}
