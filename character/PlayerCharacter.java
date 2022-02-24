package character;

import java.util.ArrayList;
import java.util.Hashtable;

import enums.DamageType;
import interfaces.Damage;
import items.Items;
import magic.Spells;
import weapon.Weapon;

public class PlayerCharacter extends TemplateCharacter implements Damage {

	private int posX = 0, posY = 0; //Where the hero is in the dungeon?
	private int gold = 150; //How much gold does the hero have during the journey
	private ArrayList<Items> inventory = new ArrayList<>();
	private double carryingCapacity = 0.0; //How much can the hero carry during the game.

	private int exp = 0;
	private int nextLevel = 1500;

	public PlayerCharacter(String name, Hashtable<String, Integer> attributes) {
		super(name, attributes);
		setCarryingCapacity();
		super.setWeapon(new Weapon("Rapier",5,15,DamageType.PIERCING,2,5,35)); //For testing purpose, remove later.
	}
	
	public void moveCharacter(int x, int y) {
		posX += x;
		posY += y;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void printOverworldStats() {
		if(super.isAlive()) {
			int hp = super.getHP();
			int maxHP = super.getMaxHP();
			int totalHPBar = maxHP / 20 + 1;
			int fullHPBar = (hp == maxHP) ? totalHPBar : hp / 20;
			int emptyHPBar = totalHPBar - fullHPBar;
			System.out.printf("HP: [%s%s] [%d/%d]\n","=".repeat(fullHPBar),"-".repeat(emptyHPBar),hp,maxHP);
		}
		
	}
	
	public void printSpells() {
		for(Spells s: super.getSpells()) {
			s.PrintSpellMenuOption();
		}
	}

	public void addSpell(Spells s){
		ArrayList<Spells> spellList = super.getSpells();
		if(spellList.indexOf(s) != -1){
			System.out.println("You already have this spell!");
		}else{
			spellList.add(s);
			super.setSpells(spellList);
		}
	}

	private void setCarryingCapacity(){
		carryingCapacity = getStrength() * 5;
	}

	public void changeGoldAmount(int amount){
		gold += amount;
	}
	
	public boolean addItemToInventory(Items i){
		double weight = 0.0;
		for(Items inInventoryItem: inventory){
			weight += inInventoryItem.getWeight();
		}
		boolean res = weight + i.getWeight() <= carryingCapacity; //Check if including the new item would still make the inventory below the carry capacity.
		if(res) //If it would be below the carrying capacity, add the item.
			inventory.add(i); 
		return res;
	}

	public void removeItemFromInventory(Items i){
		if(inventory.isEmpty()){}
			System.out.println("You do not have anything in your inventory!");
		int itemIndex = inventory.indexOf(i);
		if(itemIndex == -1)
			System.out.println(i.getName() + " is not apart of your inventory.");
		else{
			inventory.remove(i);
			System.out.println(i.getName() + " has been removed.");
		}		
	}

	public void addGold(int amount){
		gold += amount;
	}

	public boolean spendGold(int amount){
		if(gold < amount)
			return false;
		gold -= amount;
		return true;
	}

	public void addEXP(int exp){
		double expMultiplier = 1 + (getIntellect() * 0.01);
		exp = (int)(exp * expMultiplier);
		this.exp += exp;
		System.out.println(super.getName() + " gained " + exp + " experience points!");
		if(this.exp == this.nextLevel){
			this.nextLevel *= 2;
			System.out.println("You have gained a level!");
		}		
	}

	@Override
	public int compareTo(TemplateCharacter o) {
		// TODO Auto-generated method stub
		return this.getInitaitive() - o.getInitaitive();
	}

	@Override
	public double getElementalResistance(DamageType type) {
		// TODO Auto-generated method stub
		return 0;
	}

}
