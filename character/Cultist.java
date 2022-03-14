package character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Random;

import enums.DamageType;
import items.Armor;
import magic.AttackSpell;
import magic.HealingSpell;
import magic.Spells;
import weapon.Weapon;

public class Cultist extends Enemy{
    /*
        Culitst Stats
        HP = 45
        MP = 2
        STR = 12
        DEX = 15
        CON = 14
        SPR = 10
        INT = 18
        LCK = 10
        EXP = 250

        --Weapon
        --> Dagger: 10 - 20 Slashing Damage, x2.0, 150 Gold, 2 lbs.
        --> Broadsword: 15 - 25 Slashing Damage, x1.5, 200, 5 lbs.

        --Armor
        --> Culist Robe: 5 Physical, 10 Magical, 0.25 Darkness Resistance, -0.25 Light Resistance, 10 lbs, 200 gold
    */
    private Weapon dagger = new Weapon("Dagger", 10, 20, DamageType.SLASHING, 2.0, 2, 150);
    private Weapon broadsword = new Weapon("Broadsword",15,25,DamageType.SLASHING,1.5,5,200);
    private Random r = new Random();
    private int lastDamageTaken = 0;

    public Cultist(String name) {
        super(name, 45,2,12,15,14,10,18,10,250);
        super.setArmor(createCultistRobe());
        super.setWeapon(broadsword);
        ArrayList <Spells> spells = new ArrayList<>(
            Arrays.asList(
                new AttackSpell("Dark Blast", 1, 15, 25, 1.5, DamageType.DARK),
                new HealingSpell("Rejuvinate", 2, 0.5)
        ));
        super.setSpells(spells);
    }

    private Armor createCultistRobe(){
        Hashtable<String,Integer> damageAtt = new Hashtable<>();
        Hashtable<String,Double> resAtt = new Hashtable<>();
        damageAtt.put("physical defense",5);
        damageAtt.put("magic defense",10);
        resAtt.put("light",-0.25);
        resAtt.put("dark",0.25);
        resAtt.put("piercing",-1.00);
        return new Armor("Cultist Robe", 10, 200, damageAtt, resAtt);
    }

    @Override
    public int takeDamage(int dmg, DamageType damageType){
        dmg = super.takeDamage(dmg, damageType);
        this.lastDamageTaken = dmg;
        return dmg;
    }

    @Override
    public int compareTo(TemplateCharacter o) {
        if(o.getInitaitive() != this.getInitaitive())
			return this.getInitaitive() - o.getInitaitive();
		return -1;
    }

    @Override
    public double getElementalResistance(DamageType type) {
        return 0;
    }

    @Override
    public String decideAction() {
        if(getHP() <= 10 && r.nextInt(0,99) <= 25)
            return "spell - Rejuvinate";
        if(lastDamageTaken >= getMaxHP()/4 && r.nextInt(0,99) <= 50)
            return "spell - Dark Blast";
        return "weapon";
        
    }

    @Override
    public int getGoldDrop(int LCK) {
        return (r.nextInt(0,99) + LCK >= 75) ? r.nextInt(10) + 15 + LCK: r.nextInt(0,10); 
    }
    
}
