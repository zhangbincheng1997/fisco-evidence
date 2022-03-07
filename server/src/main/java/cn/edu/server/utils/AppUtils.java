package cn.edu.server.utils;

import java.util.Random;

public class AppUtils {

    private final static int APP_ID_LENGTH = 8;
    private final static int APP_SECRET_LENGTH = 32;
    private final static String[] CHARS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private final static Random random = new Random();

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }

    public static String getAppId() {
        return randomString(APP_ID_LENGTH);
    }

    public static String getAppSecret() {
        return randomString(APP_SECRET_LENGTH);
    }
}
