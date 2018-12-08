package yk.yast.kotlin;

import org.junit.Test;
import yk.kotlin.gen.KotlinParser;
import yk.lang.yads.YadsSerializer;
import yk.yast.common.YastNode;

import java.io.ByteArrayInputStream;

import static yk.jcommon.collections.YArrayList.al;
import static yk.yast.common.Words.*;
import static yk.yast.common.YastNode.*;
import static yk.yast.kotlin.TestUtils.assertMatch;
import static yk.yast.kotlin.WordsKotlin.*;

/**
 * Created by Yuri Kravchik on 14.10.2018
 */
public class TestParserExpressions {

    @Test
    public void testAssignment() {
        assertMatch(TestUtils.parseExpression("a = b"), node(ASSIGNMENT, LEFT, ref("a"), RIGHT, ref("b")));

        System.out.println(YadsSerializer.serialize(TestUtils.parseExpression("a *= b")));
        assertMatch(TestUtils.parseExpression("a *= b"), node(ASSIGNMENT_OP, ASSIGNMENT_OP, "*=", LEFT, ref("a"), RIGHT, ref("b")));
        assertMatch(TestUtils.parseExpression("a /= b"), node(ASSIGNMENT_OP, ASSIGNMENT_OP, "/=", LEFT, ref("a"), RIGHT, ref("b")));
        assertMatch(TestUtils.parseExpression("a %= b"), node(ASSIGNMENT_OP, ASSIGNMENT_OP, "%=", LEFT, ref("a"), RIGHT, ref("b")));
        assertMatch(TestUtils.parseExpression("a += b"), node(ASSIGNMENT_OP, ASSIGNMENT_OP, "+=", LEFT, ref("a"), RIGHT, ref("b")));
        assertMatch(TestUtils.parseExpression("a -= b"), node(ASSIGNMENT_OP, ASSIGNMENT_OP, "-=", LEFT, ref("a"), RIGHT, ref("b")));
        assertMatch(TestUtils.parseExpression("a <<= b"), node(ASSIGNMENT_OP, ASSIGNMENT_OP, "<<=", LEFT, ref("a"), RIGHT, ref("b")));
        assertMatch(TestUtils.parseExpression("a >>= b"), node(ASSIGNMENT_OP, ASSIGNMENT_OP, ">>=", LEFT, ref("a"), RIGHT, ref("b")));
        assertMatch(TestUtils.parseExpression("a >>>= b"), node(ASSIGNMENT_OP, ASSIGNMENT_OP, ">>>=", LEFT, ref("a"), RIGHT, ref("b")));
        assertMatch(TestUtils.parseExpression("a &= b"), node(ASSIGNMENT_OP, ASSIGNMENT_OP, "&=", LEFT, ref("a"), RIGHT, ref("b")));
        assertMatch(TestUtils.parseExpression("a ^= b"), node(ASSIGNMENT_OP, ASSIGNMENT_OP, "^=", LEFT, ref("a"), RIGHT, ref("b")));
        assertMatch(TestUtils.parseExpression("a |= b"), node(ASSIGNMENT_OP, ASSIGNMENT_OP, "|=", LEFT, ref("a"), RIGHT, ref("b")));

        assertMatch(TestUtils.parseExpression("a = b = c"), node(ASSIGNMENT, ASSIGNMENT_OP, "=",
                LEFT, node(ASSIGNMENT, ASSIGNMENT_OP, "=", LEFT, ref("a"), RIGHT, ref("b")),
                RIGHT, ref("c")));
    }

    @Test
    public void testExpressions() {
        assertMatch(TestUtils.parseExpression("a is com.Object"), node(IS, EXPRESSION, ref("a"), IS, node(TYPE, SEQ,
                al(node(TYPE, NAME, "com"), node(TYPE, NAME, "Object")))));

        assertMatch(TestUtils.parseExpression("a is Object<Foo>"), node(IS, EXPRESSION, ref("a"), IS, node(TYPE, NAME, "Object",
                TYPE_PARAMETERS, al(node(TYPE, NAME, "Foo")))));

        assertMatch(TestUtils.parseExpression("a !is com.Object"), node(NOT_IS, EXPRESSION, ref("a"), IS, node(TYPE, SEQ,
                al(node(TYPE, NAME, "com"), node(TYPE, NAME, "Object")))));

        assertMatch(TestUtils.parseExpression("a !is Object<Foo>"), node(NOT_IS, EXPRESSION, ref("a"), IS, node(TYPE, NAME, "Object",
                TYPE_PARAMETERS, al(node(TYPE, NAME, "Foo")))));

        assertMatch(TestUtils.parseExpression("a in foo"), node(IN, EXPRESSION, ref("a"), IN, ref("foo")));
        assertMatch(TestUtils.parseExpression("a !in foo"), node(NOT_IN, EXPRESSION, ref("a"), IN, ref("foo")));

        assertMatch(TestUtils.parseExpression("a ?: b"), node(ELVIS, EXPRESSION, ref("a"), ELSE, ref("b")));
        assertMatch(TestUtils.parseExpression("a ?: b ?: c"), node(ELVIS, EXPRESSION, node(ELVIS, EXPRESSION, ref("a"), ELSE, ref("b")), ELSE, ref("c")));

        assertMatch(TestUtils.parseExpression("a foo b"), node(METHOD_CALL, NAME, "foo", ARGS, al(ref("a"), ref("b"))));
        assertMatch(TestUtils.parseExpression("a m1 b m2 c"), node(METHOD_CALL, NAME, "m2", ARGS, al(node(METHOD_CALL, NAME, "m1", ARGS, al(ref("a"), ref("b"))), ref("c"))));

        assertMatch(TestUtils.parseExpression("a .. b"), node(RANGE, LEFT, ref("a"), RIGHT, ref("b")));

        assertMatch(TestUtils.parseExpression("a as b"), node(CAST, EXPRESSION, ref("a"), OPERATOR, "as", TYPE, ref("b")));
        assertMatch(TestUtils.parseExpression("a as? b"), node(CAST, EXPRESSION, ref("a"), OPERATOR, "as?", TYPE, ref("b")));
        assertMatch(TestUtils.parseExpression("a : b"), node(CAST, EXPRESSION, ref("a"), OPERATOR, ":", TYPE, ref("b")));

    }

    @Test
    public void testConditional() {
        assertMatch(TestUtils.parseExpression("if (b) t"), node(IF, CONDITION, ref("b"), THEN, ref("t")));
        assertMatch(TestUtils.parseExpression("if (b) t else e"), node(IF, CONDITION, ref("b"), THEN, ref("t"), ELSE, ref("e")));
        assertMatch(TestUtils.parseExpression("if (b) if (b2) t"), node(IF, CONDITION, ref("b"), THEN, node(IF, THEN, ref("t"))));
        assertMatch(TestUtils.parseExpression("if (b) if (b2) t else e"), node(IF, CONDITION, ref("b"), THEN, node(IF, THEN, ref("t"), ELSE, ref("e"))));
        assertMatch(TestUtils.parseExpression("if (b) if (b2) t else e else e2"), node(IF, CONDITION, ref("b"), THEN, node(IF, THEN, ref("t"), ELSE, ref("e")), ELSE, ref("e2")));
        assertMatch(TestUtils.parseExpression("a * if (b) t else e"), node(METHOD_CALL, EXPRESSION, ref("*"), ARGS, al(ref("a"), node(IF, CONDITION, ref("b"), THEN, ref("t")))));
        assertMatch(TestUtils.parseExpression("a * if (b) if (b2) t else e"), node(METHOD_CALL, EXPRESSION, ref("*"), ARGS, al(ref("a"), node(IF, CONDITION, ref("b"), THEN, node(IF, CONDITION, ref("b2"), THEN, ref("t"), ELSE, ref("e"))))));
        assertMatch(TestUtils.parseExpression("if (b) if (b2) t else e * a"), node(IF, CONDITION, ref("b"), THEN, node(IF, CONDITION, ref("b2"), THEN, ref("t"), ELSE, node(METHOD_CALL, EXPRESSION, ref("*"), ARGS, al(ref("e"), ref("a"))))));
    }

    @Test
    public void testPostfix() {
        assertMatch(TestUtils.parseExpression("a[b]"), node(ARRAY_ACCESS, EXPRESSION, ref("a"), ARGS, al(ref("b"))));
        assertMatch(TestUtils.parseExpression("a[b+c, d-e]"), node(ARRAY_ACCESS, EXPRESSION, ref("a"), ARGS, al(
                node(METHOD_CALL, EXPRESSION, ref("+"), ARGS, al(ref("b"), ref("c"))),
                node(METHOD_CALL, EXPRESSION, ref("-"), ARGS, al(ref("d"), ref("e")))
        )));

    }

    @Test
    public void testTryCatch() {
        assertMatch(TestUtils.parseExpression("try{}catch(e:Exception){}"), node(TRY,
                BODY, node(BLOCK, STATEMENTS, al()),
                CATCH, al(node(CATCH, NAME, "e", TYPE, node(TYPE, NAME, "Exception"), BODY, node(BLOCK, STATEMENTS, al())))));

        assertMatch(TestUtils.parseExpression("try{}catch(e:Exception){}finally{}"), node(TRY,
                BODY, node(BLOCK, STATEMENTS, al()),
                CATCH, al(node(CATCH, NAME, "e", TYPE, node(TYPE, NAME, "Exception"), BODY, node(BLOCK, STATEMENTS, al()))),
                FINALLY, node(BLOCK, STATEMENTS, al())
                ));
        assertMatch(TestUtils.parseExpression("try{}catch(e:Exception){}catch(e:Exception2){}finally{}"), node(TRY,
                BODY, node(BLOCK, STATEMENTS, al()),
                CATCH, al(
                        node(CATCH, NAME, "e", TYPE, node(TYPE, NAME, "Exception"), BODY, node(BLOCK, STATEMENTS, al())),
                        node(CATCH, NAME, "e", TYPE, node(TYPE, NAME, "Exception2"), BODY, node(BLOCK, STATEMENTS, al()))
                ),
                FINALLY, node(BLOCK, STATEMENTS, al())
                ));
    }

    @Test
    public void testLoops() {
        assertMatch(TestUtils.parseExpression("for (a in b) foo()"), node(FOR_IN,
                VARIABLE, node(NEW_VAR, NAME, "a"),
                FOR_IN, ref("b"),
                BODY, node(METHOD_CALL)));

        assertMatch(TestUtils.parseExpression("for (a in b) {foo()}"), node(FOR_IN,
                VARIABLE, node(NEW_VAR, NAME, "a"),
                FOR_IN, ref("b"),
                BODY, node(BLOCK, STATEMENTS, al(node(METHOD_CALL)))));

        assertMatch(TestUtils.parseExpression("while (a == b) {foo()}"), node(WHILE_LOOP, EXPRESSION, node(METHOD_CALL), BODY, node(BLOCK, STATEMENTS, al(node(METHOD_CALL)))));
        assertMatch(TestUtils.parseExpression("do foo() while (a == b)"), node(DO_WHILE_LOOP, EXPRESSION, node(METHOD_CALL), BODY, node(METHOD_CALL)));

    }

    @Test
    public void testJump() throws Exception {
        assertMatch(TestUtils.parseExpression("throw a + b"), node(THROW, EXPRESSION, node(METHOD_CALL)));
    }

    @Test
    public void testReturns() throws Exception {
        //YastNode parsed = new KotlinParser(new ByteArrayInputStream("{return a}".getBytes("UTF-8"))).Block();
        //System.out.println(YadsSerializer.serialize(parsed));
        //TODO asserts
        YastNode parsed = new KotlinParser(new ByteArrayInputStream("{if (a) return a}".getBytes("UTF-8"))).Block();
        System.out.println(YadsSerializer.serialize(parsed));
        //YastNode parsed = new KotlinParser(new ByteArrayInputStream("{if (a) return a}".getBytes("UTF-8"))).Block();
        //System.out.println(YadsSerializer.serialize(parsed));


        assertMatch(TestUtils.parseExpression("return @hello"), node(RETURN, LABEL_REF, node(LABEL_REF, NAME, "hello")));
        assertMatch(TestUtils.parseExpression("continue @hello"), node(CONTINUE, LABEL_REF, node(LABEL_REF, NAME, "hello")));
        assertMatch(TestUtils.parseExpression("break @hello"), node(BREAK, LABEL_REF, node(LABEL_REF, NAME, "hello")));

    }

    @Test
    public void testOperations() {
        TestUtils.assertOp("||");

        TestUtils.assertOp("&&");

        TestUtils.assertOp("==");
        TestUtils.assertOp("!=");

        TestUtils.assertOp(">=");
        TestUtils.assertOp("<=");
        TestUtils.assertOp(">");
        TestUtils.assertOp("<");

        //TestUtils.assertOp(">>>");
        //TestUtils.assertOp(">>");
        //TestUtils.assertOp("<<");

        TestUtils.assertOp("+");
        TestUtils.assertOp("-");

        TestUtils.assertOp("*");
        TestUtils.assertOp("/");
        TestUtils.assertOp("%");
    }

    @Test
    public void testUnary() {
        assertMatch(TestUtils.parseExpression("-i"), node(PREFIX, OPERATOR, "-", EXPRESSION, ref("i")));
        assertMatch(TestUtils.parseExpression("++i"), node(PREFIX, OPERATOR, "++", EXPRESSION, ref("i")));
        assertMatch(TestUtils.parseExpression("--i"), node(PREFIX, OPERATOR, "--", EXPRESSION, ref("i")));
        assertMatch(TestUtils.parseExpression("i++"), node(POSTFIX, OPERATOR, "++", EXPRESSION, ref("i")));
        assertMatch(TestUtils.parseExpression("i--"), node(POSTFIX, OPERATOR, "--", EXPRESSION, ref("i")));
    }

    @Test
    public void testDotExpression() {
        assertMatch(TestUtils.parseExpression("a.b.c.d"), dot(dot(dot(ref("a"), "b"), "c"), "d"));

        assertMatch(TestUtils.parseExpression("5.compare"), dot(node(CONST, VALUE, 5, TYPE, node(TYPE, NAME, "Int")), "compare"));
        assertMatch(TestUtils.parseExpression("hello.world"), dot(ref("hello"), "world"));

        //System.out.println(YadsSerializer.serialize(parseExpression("a.b(arg).d.e(arg2)")));
        assertMatch(TestUtils.parseExpression("a.b(arg).d.e(arg2)"), node(METHOD_CALL,
                EXPRESSION, dot(dot(node(METHOD_CALL,
                        EXPRESSION, dot(ref("a"), "b"),
                        ARGS, al(ref("arg"))
                ), "d"), "e"),
                ARGS, al(ref("arg2"))
        ));
    }

    @Test
    public void testConsts() throws Exception {
        TestUtils.assertConst("5", "Int", 5);
        TestUtils.assertConst("5L", "Long", 5L);
        //TestUtils.assertConst("5l", "Long", 5L);

        TestUtils.assertConst("5.0", "Double", 5D);
        TestUtils.assertConst(".0", "Double", .0D);
        TestUtils.assertConst(".0D", "Double", .0D);
        TestUtils.assertConst(".0d", "Double", .0D);

        TestUtils.assertConst(".0F", "Float", .0F);
        TestUtils.assertConst(".0f", "Float", .0F);

        TestUtils.assertConst("'c'", "Char", 'c');
        TestUtils.assertConst("'c'", "Char", 'c');
        //assertConst("'\\n'", "char", '\n');TODO
        //assertConst("'\\\\'", "char", '\\');TODO

        TestUtils.assertConst("\" \"", "String", " ");
        //assertConst("\"\\t \"", "String", " ");TODO

        TestUtils.assertConst("true", "Boolean", true);
        TestUtils.assertConst("false", "Boolean", false);

    }

}
