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

}
