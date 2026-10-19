package com.example.donuttracker;

public class ProfitTracker {
    private double net = 0;
    private int wins = 0, losses = 0;

    public void recordWin(double amount)  { net += amount; wins++; }
    public void recordLoss(double amount) { net -= amount; losses++; }
    public void reset() { net = 0; wins = 0; losses = 0; }

    public double getNet() { return net; }

    public String summary() {
        int total = wins + losses;
        double rate = total == 0 ? 0 : (wins * 100.0 / total);
        return String.format("Net: %s | W/L: %d/%d (%.1f%%)", format(net), wins, losses, rate);
    }

    public static String format(double v) {
        double a = Math.abs(v);
        String sign = v < 0 ? "-" : "+";
        if (a >= 1_000_000_000) return String.format("%s$%.2fB", sign, a / 1_000_000_000);
        if (a >= 1_000_000)     return String.format("%s$%.2fM", sign, a / 1_000_000);
        if (a >= 1_000)         return String.format("%s$%.1fK", sign, a / 1_000);
        return String.format("%s$%.0f", sign, a);
    }

    /** EV of one bet. payoutMultiplier is total returned on win (2.0 = double your bet). */
    public static double expectedValue(double bet, double winChance, double payoutMultiplier) {
        return winChance * bet * (payoutMultiplier - 1) - (1 - winChance) * bet;
    }
}
