package application;

public class Dwarf extends Character{
	private String name;
	
	public Dwarf() {
		this.name = "Dwarf";
		this.buyPrice = 100;
		this.mineSpeed = 1;
	}
	
	public Dwarf(String name, int buyprice, int minespeed) {
		this.name = name;
		this.buyPrice = buyprice;
		this.mineSpeed = minespeed;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
        return this.name +" / MineSpeed: " + this.mineSpeed + " / Price: " + this.buyPrice + " Gold";
    }
	
	
}
