package cc.i9mc.uhc.loader;

public class Config {
    private static Runtime rt = Runtime.getRuntime();

    public static long Now() {
        return System.currentTimeMillis();
    }

    public static int AvailableMemory() {
        return (int) ((rt.maxMemory() - rt.totalMemory() + rt.freeMemory()) / 1048576);  // 1024*1024 = 1048576 (bytes in 1 MB)
    }

    public static boolean AvailableMemoryTooLow() {
        return AvailableMemory() < 500;
    }
}