package com.example.cyberescaperoom1.controller;

import java.util.HashSet;
import java.util.Set;

public class GameState {
    private int coins = 0;
    private int xp = 0;
    private int streak = 0;
    private int maxStreak = 0;
    private int currentSector = 1;
    private int completedSectors = 0;
    private Set<String> achievements = new HashSet<>();

    // Calculate level dynamically based on XP
    public String getLevel() {
        if (xp >= 200) return "SOC Commander";
        if (xp >= 150) return "Threat Hunter";
        if (xp >= 100) return "Threat Investigator";
        if (xp >= 50) return "Security Analyst";
        return "Cyber Trainee";
    }

    public int getLevelProgress() {
        // Simple progress calculation (resets every 50 XP)
        return (xp % 50) * 2; // 2% per 1 XP
    }
    // Inside your GameState class, add this at the top with your other variables:
    private String playerName;

    // Add these getters and setters at the bottom of the class:
    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public void addCorrectAnswer(int sector) {
        coins += 10;
        xp += 25;
        streak++;
        completedSectors = Math.max(completedSectors, sector);

        if (streak > maxStreak) maxStreak = streak;

        // Achievements and Streak Bonuses
        if (sector == 1) achievements.add("🏆 First Correct Answer");

        if (streak == 3) {
            coins += 20;
            achievements.add("🏆 3 Win Streak");
        }
        if (streak == 5) {
            coins += 50;
            achievements.add("🏆 Unstoppable (5 Streak)");
        }
        if (coins >= 100) achievements.add("🏆 100 Coins Earned");
        if (sector == 8) achievements.add("🏆 Password Expert");
        if (sector == 10) achievements.add("🏆 Escape Master");
    }

    public void resetStreak() {
        this.streak = 0;
    }

    // Getters and Setters
    public int getCoins() { return coins; }
    public int getXp() { return xp; }
    public int getStreak() { return streak; }
    public int getCurrentSector() { return currentSector; }
    public void setCurrentSector(int currentSector) { this.currentSector = currentSector; }
    public int getCompletedSectors() { return completedSectors; }
    public Set<String> getAchievements() { return achievements; }
    // Add this method to your existing GameState.java
    public void addInvestigationScore(int sector, int tasksCorrect, int totalTasks, boolean finalDecisionCorrect) {
        int pointsEarned = (tasksCorrect * 10); // 10 coins per standard task
        int xpEarned = (tasksCorrect * 25);

        if (finalDecisionCorrect) {
            pointsEarned += 20; // 20 coins for the final decision
            xpEarned += 50;
        }

        // Bonus for perfect investigation
        if (tasksCorrect == totalTasks && finalDecisionCorrect) {
            pointsEarned += 30;
            streak++;
            if (streak > maxStreak) maxStreak = streak;
        } else {
            streak = 0; // Reset streak if not perfect
        }

        this.coins += pointsEarned;
        this.xp += xpEarned;
        this.completedSectors = Math.max(completedSectors, sector);
    }
}
