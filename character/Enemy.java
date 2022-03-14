package character;

import java.util.Hashtable;

public abstract class Enemy extends TemplateCharacter{

    public Enemy(String name,int HP, int MP, int STR, int DEX, int CON, int SPR, int INT, int LCK,int EXP){
        super(name);
        Hashtable<String,Integer> attributes = new Hashtable<String,Integer>();
		attributes.put("HP", HP);
		attributes.put("MP", MP);
		attributes.put("Strength", STR);
		attributes.put("Dexterity", DEX);
		attributes.put("Constitution", CON);
		attributes.put("Spirit", SPR);
		attributes.put("Intellect", INT);
		attributes.put("Luck", LCK);
		super.setAttributes(attributes);
		super.setEXP(EXP);
    }

    public abstract String decideAction();
    public abstract int getGoldDrop(int LCK);
}
