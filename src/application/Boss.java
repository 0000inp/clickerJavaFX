package application;

import javafx.scene.control.ProgressBar;

public class Boss {
	
	private double bossMaxHP;
    private int bossHP;
    private int countDownTime;
    private int count;
    
    public Boss() {
    	double bossMaxHP = 100;
        bossHP = (int) bossMaxHP;
        countDownTime = 3;
        count = countDownTime;
    }
    
    public Boss(double bossMaxHP, int countDownTime) {
    	this.bossMaxHP = bossMaxHP;
    	this.bossHP = (int) bossMaxHP;
    	this.countDownTime = countDownTime;
    	this.count = countDownTime;
    }
	
	public void setBossHP(int hp, ProgressBar bar) {
    	double HPpercentage = hp/bossMaxHP;
    	bar.setProgress(HPpercentage);
    }
    
	public void bossHPdecrease(int damage,  ProgressBar bar) {
    	bossHP -= damage;
    	double HPpercentage = bossHP/bossMaxHP;
    	bar.setProgress(HPpercentage);
    }

    public double getBossMaxHP() {
        return bossMaxHP;
    }

    public void setBossMaxHP(double bossMaxHP) {
        this.bossMaxHP = bossMaxHP;
    }

    public int getBossHP() {
        return bossHP;
    }

    public void setBossHP(int bossHP) {
        this.bossHP = bossHP;
    }

    public int getCountDownTime() {
        return countDownTime;
    }

    public void setCountDownTime(int countDownTime) {
        this.countDownTime = countDownTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}


