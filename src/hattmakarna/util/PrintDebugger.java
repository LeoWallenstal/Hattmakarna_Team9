package hattmakarna.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author james
 * All messages (debug(), info(), warn(), error()) can be toggled with setting
 * the public static boolean 'enabled' true or false, preferably in main.
 */

public class PrintDebugger {
    // Toggle to enable or disable debug output
    public static boolean enabled = true;

    // ANSI Color Codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN = "\u001B[36m";

    /**
     * Prints in cyan - [<Timestamp>][<Loglevel>] <package>.<classname># - <function>()
     * 
     * @param args Each argument is printed on a newline, and indented.
     */
    public static void debug(Object... args) {
        if (!enabled) return;
        logWithLevel("DEBUG", CYAN, args);
    }
    
    /**
     * Prints in green - [<Timestamp>][<Loglevel>] <package>.<classname># - <function>():
     * 
     * @param args Each argument is printed on a newline, and indented.
     */
    public static void info(Object... args) {
        if (!enabled) return;
        logWithLevel("INFO", GREEN, args);
    }
    
    /**
     * Prints in yellow - [<Timestamp>][<Loglevel>] <package>.<classname># - <function>():
     * 
     * @param args Each argument is printed on a newline, and indented.
     */
    public static void warn(Object... args) {
        if (!enabled) return;
        logWithLevel("WARN", YELLOW, args);
    }
    
    /**
     * Prints - [<Timestamp>][<Loglevel>] <package>.<classname># - <function>():
     * 
     * @param args Each argument is printed on a newline, and indented.
     */
    public static void error(Object... args) {
        if (!enabled) return;
        logWithLevel("ERROR", RED, args);
    }

    // Common logging routine that formats the message
    private static void logWithLevel(String level, String color, Object... args) {
        String timestamp = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("HH:mm:ss"));
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        String caller = stack.length >= 4
            ? stack[3].getClassName() + "#" + stack[3].getMethodName() + "()"
            : "Unknown";

        StringBuilder b = new StringBuilder();
        b.append(color)
         .append("[").append(timestamp).append("]")
         .append("[").append(level).append("] ")
         .append(caller).append(":\n");

        for (Object arg : args) {
            String text = String.valueOf(arg);
            // Split at newlines so we can color every line
            String[] lines = text.split("\\R");
            for (int i = 0; i < lines.length; i++) {
                b.append(color)              // re-apply color before each line
                 .append("\t-> ")
                 .append(lines[i])
                 .append("\n");
            }
        }

        b.append(RESET);
        System.out.print(b.toString());
    }
}