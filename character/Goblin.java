package character;

import java.util.Random;

import enums.DamageType;
import weapon.Weapon;

public class Goblin extends Enemy {
	private Random r = new Random();
	private Weapon wpn = new Weapon("Shiv", 7, 15, DamageType.PIERCING, 1.5, 2, 15);

	public Goblin(String name) {
		super(name,25,0,12,15,8,6,10,10,150);
		super.setWeapon(wpn);
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

	@Override
	public String decideAction() {
		//Goblin under 15 HP will have a 25% chance to flee.
		if(getHP() < 5){
			if(r.nextInt(0,99) <= 25)
				return "flee";
		}
		return "weapon";
	}

	@Override
	public int getGoldDrop(int LCK) {
		return (r.nextInt(0,99) + LCK >= 50) ? 10 : (int)(10 * 2.25);
	}
}
