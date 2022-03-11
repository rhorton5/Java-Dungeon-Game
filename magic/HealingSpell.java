package magic;

public class HealingSpell extends Spells {

    private double effectiveness;
    public HealingSpell(String name, int mpCost, double effectiveness) {
        super(name, mpCost);
        this.effectiveness = effectiveness;
    }

    public int restoreHP(int maxHP, int SPR){
        int base = (int)(maxHP * effectiveness);
        return base * SPR/10;
    }

    @Override
    public String getSpellType() {
        return "Healing";
    }
    
}
