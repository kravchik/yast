package yk.yast.common.utils;

import yk.jcommon.match2.Matcher;
import yk.yast.common.YastNode;

/**
 * Created by Yuri Kravchik on 24.10.2018
 */
public class Utils {
    public static Matcher nodeMatcher() {
        Matcher result = new Matcher();
        result.classMatchers.put(YastNode.class, new MatchNodeClass());
        return result;
    }

    public static YastNode mn(Object k, Object v, Object... kv) {
        return new YastNode((String) k, v, kv);
    }

    //public static void testExecutionTime(Runnable c) {
    //    //we cannot use Runnable for the FIRST call, because class resolving time will not be accounted
    //    nextXCalls(c, 10);
    //    nextXCalls(c, 100);
    //    nextXCalls(c, 1000);
    //}
    //
    //private static void nextXCalls(Runnable c, int x) {
    //    StopWatchNs sw = new StopWatchNs();
    //    long minTime = Long.MAX_VALUE;
    //    for (int i = 0; i < x; i++) {
    //        sw.restart();
    //        c.run();
    //        sw.stop();
    //        minTime = Math.min(sw.getLastCount(), minTime);
    //    }
    //    long K = 1000;
    //    System.out.println("min time in next " + x + " calls: " + (minTime / K * K / 1e6f) + "ms");
    //}
}
