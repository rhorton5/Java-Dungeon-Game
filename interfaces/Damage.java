package interfaces;

import enums.DamageType;

public interface Damage {
	public int takeDamage(int dmg, DamageType type);
	public double getElementalResistance(DamageType type);
	
}
