package application;

import java.util.ArrayList;

public class Team {

    private int gold;
    private int playerGoldPerClick;
    private ArrayList<Character> teamMember;
    private int teamGoldPerSec;
    private double difficultyScale;

    public Team() {
        this.gold = 0;
        this.playerGoldPerClick = 5;
        this.teamMember = new ArrayList<>();
        this.teamGoldPerSec = 0;
        this.difficultyScale = 1;
    }
    
    public Team(int gold, int playerGoldPerClick, ArrayList<Character> teamMember, int teamGoldPerSec) {
        this.gold = gold;
        this.playerGoldPerClick = playerGoldPerClick;
        this.teamMember = teamMember;
        this.teamGoldPerSec = teamGoldPerSec;
        this.difficultyScale = 1;
    }
    
    public void teamGoldPerSecUpdate() {
        int totalGoldPerSec = 0;
        for (Character character : teamMember) {
            totalGoldPerSec += character.getMineSpeed();
        }
        this.teamGoldPerSec = totalGoldPerSec;
    }
    
    public void incrementGold(int amount) {
        this.gold += amount;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getPlayerGoldPerClick() {
        return playerGoldPerClick;
    }

    public void setPlayerGoldPerClick(int playerGoldPerClick) {
        this.playerGoldPerClick = playerGoldPerClick;
    }

    public ArrayList<Character> getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(ArrayList<Character> teamMember) {
        this.teamMember = teamMember;
    }

    public int getTeamGoldPerSec() {
        return teamGoldPerSec;
    }

    public void setTeamGoldPerSec(int teamGoldPerSec) {
        this.teamGoldPerSec = teamGoldPerSec;
    }

    public double getDifficultyScale() {
        return difficultyScale;
    }

    public void setDifficultyScale(double difficultyScale) {
        this.difficultyScale = difficultyScale;
    }
}