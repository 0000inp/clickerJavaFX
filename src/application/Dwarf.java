package application;

public class Dwarf extends Character{
	private String name;
	
	public Dwarf() {
		this.name = "Dwarf";
		this.buyPrice = 100;
		this.mineSpeed = 1;
	}
	
	/*public Dwarf() {
		super("Dwarf",100,1,1);
	}*/
	
	public String toString() {
        return this.name +" / MineSpeed: " + this.mineSpeed + " / Price: " + this.buyPrice + " Gold";
    }
	
	
}
