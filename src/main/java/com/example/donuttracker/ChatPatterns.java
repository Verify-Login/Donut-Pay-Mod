package com.example.donuttracker;

import java.util.regex.Pattern;

/**
 * PLACEHOLDER patterns. Copy real chat lines from the server and edit these
 * until they match. Group 1 = player name (for PAID), amount group is the number.
 */
public final class ChatPatterns {
    private ChatPatterns() {}

    public static final Pattern PAID =
        Pattern.compile("^(\\w{3,16}) paid you \\$([\\d,.]+[KMBkmb]?)", Pattern.CASE_INSENSITIVE);

    public static final Pattern WIN =
        Pattern.compile("you won \\$([\\d,.]+[KMBkmb]?)", Pattern.CASE_INSENSITIVE);

    public static final Pattern LOSS =
        Pattern.compile("you lost \\$([\\d,.]+[KMBkmb]?)", Pattern.CASE_INSENSITIVE);

    /** Parses "1,500", "2.5K", "3M", "1B" into a number. */
    public static double parseAmount(String s) {
        s = s.replace(",", "").trim().toUpperCase();
        double mult = 1;
        if (s.endsWith("K")) { mult = 1_000d; s = s.substring(0, s.length() - 1); }
        else if (s.endsWith("M")) { mult = 1_000_000d; s = s.substring(0, s.length() - 1); }
        else if (s.endsWith("B")) { mult = 1_000_000_000d; s = s.substring(0, s.length() - 1); }
        try { return Double.parseDouble(s) * mult; }
        catch (NumberFormatException e) { return 0; }
    }
}
