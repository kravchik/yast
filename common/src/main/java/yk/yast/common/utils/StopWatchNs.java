package yk.yast.common.utils;

/**
 * Created by Yuri Kravchik on 30.11.2018
 */
public class StopWatchNs {
    private long lastCount;
    private long start;

    public StopWatchNs() {
        start();
    }

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        lastCount = System.nanoTime() - start;
    }

    public void restart() {
        long cur = System.nanoTime();
        lastCount = cur - start;
        start = cur;
    }

    public long getLastCount() {
        return lastCount;
    }

    public String getCurrentTime() {
        return "" + (System.nanoTime() - start) / 1e9f + "s";
    }

    public String getCurrentTimeMs() {
        return "" + (System.nanoTime() - start) / 1000 * 1000 / 1e6f + "ms";
    }

    @Override
    public String toString() {
        return "" + lastCount / 1e9f + "s";
    }

}
