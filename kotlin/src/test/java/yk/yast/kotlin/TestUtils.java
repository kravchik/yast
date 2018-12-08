package yk.yast.kotlin;

import yk.jcommon.match2.Matcher;
import yk.jcommon.utils.BadException;
import yk.jcommon.utils.Reflector;
import yk.kotlin.gen.KotlinParser;
import yk.lang.yads.YadsSerializer;
import yk.yast.common.YastNode;
import yk.yast.common.utils.MatchNodeClass;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static yk.jcommon.collections.YArrayList.al;
import static yk.yast.common.Words.*;
import static yk.yast.common.YastNode.node;
import static yk.yast.common.YastNode.ref;

/**
 * Created by Yuri Kravchik on 23.10.2018
 */
public class TestUtils {
    static void assertOp(final String op) {
        System.out.println(YadsSerializer.serialize(parseExpression("a " + op + " b")));
        assertMatch(parseExpression("a " + op + " b"), node(METHOD_CALL, EXPRESSION, ref(op), ARGS, al(ref("a"), ref("b"))));

    }

    static void assertConst(String code, String type, Object value) {
        YastNode result;
        result = parseExpression(code);
        YastNode literal = result;
        assertEquals(CONST, literal.getString(NODE_TYPE));
        assertEquals(type, literal.getNode(TYPE).getString(NAME));
        assertEquals(value, literal.map.get(VALUE));
    }


    public static Matcher nodeMatcher() {
        Matcher result = new Matcher();
        result.classMatchers.put(YastNode.class, new MatchNodeClass());
        return result;
    }

    public static YastNode parseExpression(String code) {
        return (YastNode) parse("Expression", code);
    }

    public static YastNode parseProperty(String code) {
        return (YastNode) parse("Property", code);
    }

    public static YastNode parseStetement(String code) {
        return (YastNode) parse("Statement", code);
    }

    public static void assertMatch(Object parsed, Object expected) {
        if (nodeMatcher().match(parsed, expected).size() == 1) {
            return;
        }
        System.out.println("parsed:");
        System.out.println(YadsSerializer.serialize(parsed));
        fail("Not matched");
    }

    public static YastNode parseFile(String code) {
        return (YastNode) parse("File", code);
    }

    public static Object parse(String method, String code) {
        try {
            KotlinParser parser = new KotlinParser(new ByteArrayInputStream(code.getBytes("UTF-8")));
            return Reflector.invokeMethod(parser, method);
        } catch (Exception e) {
            throw BadException.die(e);
        }
    }
}
