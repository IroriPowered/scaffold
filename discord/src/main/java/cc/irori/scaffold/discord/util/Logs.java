package cc.irori.scaffold.discord.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logs {

    private static final String BASE_NAME = "Scaffold";

    // Private constructor to prevent instantiation
    private Logs() {
    }

    public static Logger logger() {
        return LoggerFactory.getLogger(BASE_NAME);
    }
}
