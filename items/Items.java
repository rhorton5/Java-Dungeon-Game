package items;

public abstract class Items {
	private final double weight;
	private final int value;
	private final String name;
	public Items(String name, double weight, int value) {
		this.name = name;
		this.weight = weight;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public int getValue() {
		return value;
	}
	@Override
	public String toString() {
		return String.format("------- %s --------\nWeight: %.2f lbs\nValue: %d Gold\n", name,weight,value);
	}
}
