package com.capstone.cricketmatch.entity;


public class PlayerStats {
    private String userId;
    private String userName;
    private int currentScore;
    private int currentWickets;

    public PlayerStats(String userId, String userName, int currentScore, int currentWickets) {
        this.userId = userId;
        this.userName = userName;
        this.currentScore = currentScore;
        this.currentWickets = currentWickets;
    }

    public PlayerStats() {
    }

    public String getPlayerId() {
        return userId;
    }

    public void setPlayerId(String playerId) {
        this.userId = userId;
    }

    public String getPlayerName() {
        return userName;
    }

    public void setPlayerName(String playerName) {
        this.userName = playerName;
    }

    public int getRunsScored() {
        return currentScore;
    }

    public void setRunsScored(int runsScored) {
        this.currentScore = runsScored;
    }

    public int getWicketsTaken() {
        return currentWickets;
    }

    public void setWicketsTaken(int wicketsTaken) {
        this.currentWickets = wicketsTaken;
    }
}
