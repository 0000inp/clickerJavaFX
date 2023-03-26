package application;

public class Character {
	
	protected String name;
	protected int buyPrice;
	protected int upgradePrice;
	protected int mineSpeed;
	
	public Character() {}
	
	public Character(String name, int buyPrice, int upgradePrice, int mineSpeed) {
		this.name = name;
		this.buyPrice = buyPrice;
		this.upgradePrice = upgradePrice;
		this.mineSpeed = mineSpeed;
	}
	
	public String toString() {
		return this.name;
	}
    
	public String getName() {
		return name;
	}
    
	public int getBuyPrice() {
		return buyPrice;
	}
    
	public int getUpgradePrice() {
		return upgradePrice;
	}
    
	public int getMineSpeed() {
		return mineSpeed;
	}
}
