package yk.yast.kotlin;

import org.junit.Test;
import yk.lang.yads.YadsSerializer;

import static yk.jcommon.collections.YArrayList.al;
import static yk.yast.common.Words.*;
import static yk.yast.common.YastNode.node;
import static yk.yast.common.YastNode.ref;
import static yk.yast.kotlin.TestUtils.assertMatch;
import static yk.yast.kotlin.WordsKotlin.OBJECT_DEF;

/**
 * Created by Yuri Kravchik on 15.10.2018
 */
public class TestParserStatements {
    @Test
    public void testVars() {
        assertMatch(TestUtils.parseProperty("var a;"), node(NEW_VAR, NAME, "a"));
        assertMatch(TestUtils.parseProperty("val a"), node(NEW_VAR, NAME, "a", CONST, true));

        assertMatch(TestUtils.parseProperty("val a = b + c"), node(NEW_VAR, NAME, "a", CONST, true,
                EXPRESSION, node(METHOD_CALL, EXPRESSION, ref("+"), ARGS, al(ref("b"), ref("c")))));

        assertMatch(TestUtils.parseProperty("val a:Int = b + c"), node(NEW_VAR, NAME, "a", CONST, true,
                TYPE, node(TYPE, NAME, "Int"),
                EXPRESSION, node(METHOD_CALL, EXPRESSION, ref("+"), ARGS, al(ref("b"), ref("c")))));

        System.out.println(YadsSerializer.serialize(TestUtils.parseProperty("val<T> a:Foo = b + c")));
        assertMatch(TestUtils.parseProperty("val<T> a:Foo = b + c"), node(NEW_VAR, NAME, "a", CONST, true,
                TYPE, node(TYPE, NAME, "Foo"),
                TYPE_PARAMETERS, al(node(TYPE_PARAMETER, NAME, "T")),
                EXPRESSION, node(METHOD_CALL, EXPRESSION, ref("+"), ARGS, al(ref("b"), ref("c")))));

    }

    @Test
    public void testDeclarations() {
        assertMatch(TestUtils.parseFile("package a class Outer {fun foo(){class Inner{}}}"), node(FILE, MEMBERS, al(
                node(CLASS_DEF, NAME, "Outer", MEMBERS, al(
                        node(METHOD_DEF, NAME, "foo", BODY, node(BLOCK, STATEMENTS, al(
                                node(CLASS_DEF, NAME, "Inner", MEMBERS, al())
                        )))
                ))
        )));

        assertMatch(TestUtils.parseFile("package a class Outer {fun foo(){object Inner{}}}"), node(FILE, MEMBERS, al(
                node(CLASS_DEF, NAME, "Outer", MEMBERS, al(
                        node(METHOD_DEF, NAME, "foo", BODY, node(BLOCK, STATEMENTS, al(
                                node(OBJECT_DEF, NAME, "Inner", MEMBERS, al())
                        )))
                ))
        )));

    }

    @Test
    public void testMethods() {
        assertMatch(TestUtils.parseStetement("val result = SimpleClass(x, y, z)"), node(NEW_VAR, NAME, "result", CONST, true,
                EXPRESSION, node(METHOD_CALL, EXPRESSION, ref("SimpleClass"), ARGS, al(ref("x"), ref("y"), ref("z")))));


        //TODO
        System.out.println(YadsSerializer.serialize(TestUtils.parseStetement("base.helloFun().member")));
    }

}
