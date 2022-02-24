package magic;

import java.util.Random;

import enums.DamageType;
import interfaces.DamageDeal;

public class AttackSpell extends Spells implements DamageDeal{

	private int minDamage, maxDamage;
	private DamageType damageType;
	private String name;
	private double criticalModifier;
	private Random r = new Random();
	
	public AttackSpell(String name, int mpCost,int minDamage, int maxDamage, double criticalModifier, DamageType damageType) {
		super(name, mpCost);
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.criticalModifier = criticalModifier;
		this.damageType = damageType;
		
		// TODO Auto-generated constructor stub
	}
	
	public DamageType getDamageType() {
		return damageType;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int dealDamage(boolean criticalHit) {
		int dmg = r.nextInt(maxDamage-minDamage)+minDamage;
		return (criticalHit) ? (int)(dmg * criticalModifier) : dmg;
			
	}
}
