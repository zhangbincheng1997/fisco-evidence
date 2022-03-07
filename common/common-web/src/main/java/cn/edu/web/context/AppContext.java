package cn.edu.web.context;

public class AppContext {

    private static final ThreadLocal<String> context = new ThreadLocal<>();

    public static void set(String appId) {
        context.set(appId);
    }

    public static String get() {
        return context.get();
    }

    public static void remove() {
        context.remove();
    }
}
