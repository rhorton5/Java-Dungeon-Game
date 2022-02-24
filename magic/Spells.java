package magic;

public abstract class Spells {
	private final int mpCost;
	private final String name;
	public Spells(String name, int mpCost) {
		this.mpCost = mpCost;
		this.name = name;
	}
	
	public int getMPCost() {return mpCost;}
	public String getName() {return name;}
	public void PrintSpellMenuOption() {System.out.printf("%s [%d MP]\n", name,mpCost);}
}
