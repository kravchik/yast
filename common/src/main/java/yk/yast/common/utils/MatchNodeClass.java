package yk.yast.common.utils;

import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;
import yk.jcommon.match2.MatchCustomStatic;
import yk.jcommon.match2.Matcher;
import yk.jcommon.utils.BadException;
import yk.yast.common.YastNode;

import static yk.jcommon.collections.YHashSet.hs;

/**
 * Created by Yuri Kravchik on 03/01/17.
 */
public class MatchNodeClass implements MatchCustomStatic {

    @Override
    public YSet<YMap<String, Object>> match(Matcher matcher, Object data, Object pattern, YMap<String, Object> cur) {
        if (!(data instanceof YastNode)) return hs();
        if (!(pattern instanceof YastNode)) throw BadException.shouldNeverReachHere();
        return matcher.match(((YastNode)data).map, ((YastNode)pattern).map, cur);
    }
}
