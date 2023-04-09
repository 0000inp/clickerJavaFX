package application;

public class Character {
	
	protected String name;
	protected int buyPrice;
	protected int mineSpeed;
	
	public Character() {}
	
	public Character(String name, int buyPrice, int mineSpeed) {
		this.name = name;
		this.buyPrice = buyPrice;
		this.mineSpeed = mineSpeed;
	}
	
	public String toString() {
		return this.name;
	}
    
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
    
	public int getBuyPrice() {
		return this.buyPrice;
	}
	
	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
    
	public int getMineSpeed() {
		return this.mineSpeed;
	}
	
	public void setMineSpeed(int mineSpeed) {
		this.mineSpeed = mineSpeed;
	}
}
