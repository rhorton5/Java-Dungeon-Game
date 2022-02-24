package enums;

public enum CriticalDamageVariation {
	WEAK(1.5), NORMAL(2.0), STRONG(3.0), DEATHLY(4.0);

	private final double damageModifier;
	
	CriticalDamageVariation(double d) {
		// TODO Auto-generated constructor stub
		damageModifier = d;
	}
	
	public double getCriticalModifier() {
		return damageModifier;
	}
}
