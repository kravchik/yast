package yk.yast.common.utils;

/**
 * Created by Yuri Kravchik on 08.12.2018
 * @deprecated JMH should be used instead (see ubenchmark module)
 */
@Deprecated
public class GaugeTester {
    private StopWatchNs wholeTime;
    private StopWatchNs firstCall;

    public GaugeTester(String message) {
        //we want to preload lambda mechanisms to avoid it accounted in the timings
        Runnable r = () -> System.out.println(message);
        r.run();
        //we have to create this tester and its timers BEFORE the first mention of a testing code
        //  because we want to look at the class loading time
        wholeTime = new StopWatchNs();
        firstCall = new StopWatchNs();
    }

    public void testExecutionTime(Runnable c) {
        c.run();
        System.out.println("first call elapsed : " + firstCall.getCurrentTimeMs());

        nextXCalls(c, 10);
        nextXCalls(c, 100);
        nextXCalls(c, 1000);

        System.out.println("Whole time for 1111 calls: " + wholeTime.getCurrentTime());
    }

    private void nextXCalls(Runnable c, int x) {
        StopWatchNs sw = new StopWatchNs();
        long minTime = Long.MAX_VALUE;
        for (int i = 0; i < x; i++) {
            sw.restart();
            c.run();
            sw.stop();
            minTime = Math.min(sw.getLastCount(), minTime);
        }
        long K = 1000;
        System.out.println("min time in next " + x + " calls: " + (minTime / K * K / 1e6f) + "ms");
    }

}
