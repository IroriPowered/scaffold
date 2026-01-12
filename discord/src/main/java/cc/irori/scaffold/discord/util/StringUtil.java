package cc.irori.scaffold.discord.util;

public class StringUtil {

    // Private constructor to prevent instantiation
    private StringUtil() {
    }

    public static String escapeDiscordMarkdown(String input) {
        if (input == null) {
            return null;
        }
        // Escape Discord markdown characters
        return input
                .replace("_", "\\_")
                .replace("*", "\\*")
                .replace("~", "\\~")
                .replace("`", "\\`")
                .replace("<", "\\<")
                .replace(">", "\\>")
                .replace("#", "\\#")
                .replace("+", "\\+")
                .replace("-", "\\-")
                .replace("=", "\\=")
                .replace("|", "\\|")
                .replace("@everyone", "@\u200Beveryone")
                .replace("@here", "@\u200Bhere");
    }
}
