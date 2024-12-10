package util;

public class ConsoleUtils {
    // ANSI escape codes for text colors
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // ANSI escape codes for background colors
    public static final String BG_BLACK = "\u001B[40m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";
    public static final String BG_WHITE = "\u001B[47m";

    // ANSI escape codes for text styles
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String REVERSED = "\u001B[7m";

    /**
     * Prints formatted text to the terminal.
     * @param text
     * @param styles
     */
    public static void print(String text, String... styles) {
        StringBuilder sb = new StringBuilder();
        for (String style : styles) {
            sb.append(style);
        }
        sb.append(text).append(RESET);
        System.out.print(sb);
    }

    /**
     * Prints formatted text with a newline.
     *
     * @param text The text to print
     * @param styles The styles to apply (color, background, etc.).
     */
    public static void println(String text, String... styles) {
        print(text, styles);
        System.out.println();
    }

    /**
     * Clears the terminal screen.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
