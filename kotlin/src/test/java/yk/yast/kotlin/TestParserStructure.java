package yk.yast.kotlin;

import org.junit.Test;
import yk.lang.yads.YadsSerializer;

import static yk.jcommon.collections.YArrayList.al;
import static yk.yast.common.Words.*;
import static yk.yast.common.YastNode.node;
import static yk.yast.common.YastNode.ref;
import static yk.yast.kotlin.WordsKotlin.*;

/**
 * Created by Yuri Kravchik on 15.10.2018
 */
public class TestParserStructure {
    @Test
    public void testFile() {
        TestUtils.assertMatch(TestUtils.parseFile(""), node(FILE, IMPORTS, al(), MEMBERS, al()));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world"), node(FILE, PACKAGE, "hello.world", IMPORTS, al(), MEMBERS, al()));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello"), node(FILE, PACKAGE, "hello.world",
                IMPORTS, al(node(IMPORT, VALUE, "world.hello")), MEMBERS, al()));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello var x"), node(FILE, PACKAGE, "hello.world",
                IMPORTS, al(node(IMPORT, VALUE, "world.hello")),
                MEMBERS, al(node(NEW_VAR, NAME, "x"))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {}"), node(FILE, PACKAGE, "hello.world",
                IMPORTS, al(node(IMPORT, VALUE, "world.hello")),
                MEMBERS, al(node(CLASS_DEF, NAME, "Hello", MEMBERS, al()))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello public class Hello<T> {}"), node(FILE, PACKAGE, "hello.world",
                IMPORTS, al(node(IMPORT, VALUE, "world.hello")),
                MEMBERS, al(node(CLASS_DEF,
                        NAME, "Hello",
                        MODIFIERS, al("public"),
                        TYPE_PARAMETERS, al(node(TYPE_PARAMETER, NAME, "T")),
                        MEMBERS, al()))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {val x:Int}"), node(FILE, PACKAGE, "hello.world",
                IMPORTS, al(node(IMPORT, VALUE, "world.hello")),
                MEMBERS, al(node(CLASS_DEF, NAME, "Hello", MEMBERS, al(node(FIELD, NAME, "x"))))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello public fun foo(){}"), node(FILE, PACKAGE, "hello.world",
                IMPORTS, al(node(IMPORT, VALUE, "world.hello")),
                MEMBERS, al(node(METHOD_DEF, NAME, "foo", PARAMETERS, al()))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {class HelloChild {}}"), node(FILE, PACKAGE, "hello.world",
                IMPORTS, al(node(IMPORT, VALUE, "world.hello")),
                MEMBERS, al(node(CLASS_DEF, NAME, "Hello", MEMBERS, al(node(CLASS_DEF, NAME, "HelloChild", MEMBERS, al()))))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world object Hello {}"), node(FILE, PACKAGE, "hello.world",
                MEMBERS, al(node(OBJECT_DEF, NAME, "Hello", MEMBERS, al()))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {object HelloChild {}}"), node(FILE, PACKAGE, "hello.world",
                IMPORTS, al(node(IMPORT, VALUE, "world.hello")),
                MEMBERS, al(node(CLASS_DEF, NAME, "Hello", MEMBERS, al(node(OBJECT_DEF, NAME, "HelloChild", MEMBERS, al()))))));
    }

    @Test
    public void testClass() {
        System.out.println(YadsSerializer.serialize(TestUtils.parseFile("package hello.world import world.hello class Hello(x:Int)")));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello(x:Int)"), node(FILE, PACKAGE, "hello.world", MEMBERS, al(
                node(CLASS_DEF, NAME, "Hello", MEMBERS, al(node(METHOD_DEF, NAME, "<primary>", PARAMETERS, al(node(NEW_VAR, NAME, "x", TYPE, node(TYPE, NAME, "Int")))))))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {init{}init{}}"), node(FILE, PACKAGE, "hello.world", MEMBERS, al(
                node(CLASS_DEF, NAME, "Hello", MEMBERS, al(
                        node(ANONYMOUS_INITIALIZER, BODY, node(BLOCK, STATEMENTS, al())),
                        node(ANONYMOUS_INITIALIZER, BODY, node(BLOCK, STATEMENTS, al()))
                )))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {constructor(foo:Int){}}"), node(FILE, PACKAGE, "hello.world", MEMBERS, al(
                node(CLASS_DEF, NAME, "Hello", MEMBERS, al(
                    node(METHOD_DEF, NAME, "<init>", PARAMETERS, al(node(NEW_VAR, NAME, "foo")))
                ))
        )));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {constructor(foo:Int):this(foo){}}"), node(FILE, PACKAGE, "hello.world", MEMBERS, al(
                node(CLASS_DEF, NAME, "Hello", MEMBERS, al(
                        node(METHOD_DEF, NAME, "<init>",
                                CONSTRUCTOR_DELEGATION, node(CONSTRUCTOR_DELEGATION, NAME, "this", ARGS, al(ref("foo"))),
                                PARAMETERS, al(node(NEW_VAR, NAME, "foo")))
                ))
        )));
        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {constructor(foo:Int):super(foo){}}"), node(FILE, PACKAGE, "hello.world", MEMBERS, al(
                node(CLASS_DEF, NAME, "Hello", MEMBERS, al(
                        node(METHOD_DEF, NAME, "<init>",
                                CONSTRUCTOR_DELEGATION, node(CONSTRUCTOR_DELEGATION, NAME, "super", ARGS, al(ref("foo"))),
                                PARAMETERS, al(node(NEW_VAR, NAME, "foo")))
                ))
        )));
    }

    @Test
    public void testEnum() {
        TestUtils.assertMatch(TestUtils.parseFile("package hello.world enum class Hello {RED, GREEN}"), node(FILE, PACKAGE, "hello.world", MEMBERS, al(
                node(ENUM_DEF, NAME, "Hello", MEMBERS, al(node(ENUM_ENTRY, NAME, "RED"), node(ENUM_ENTRY, NAME, "GREEN"))))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world enum class Hello {RED, GREEN,}"), node(FILE, PACKAGE, "hello.world", MEMBERS, al(
                node(ENUM_DEF, NAME, "Hello", MEMBERS, al(node(ENUM_ENTRY, NAME, "RED"), node(ENUM_ENTRY, NAME, "GREEN"))))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world enum class Hello {RED, GREEN{}}"), node(FILE, PACKAGE, "hello.world", MEMBERS, al(
                node(ENUM_DEF, NAME, "Hello", MEMBERS, al(node(ENUM_ENTRY, NAME, "RED"), node(ENUM_ENTRY, NAME, "GREEN", MEMBERS, al()))))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world enum class Hello {RED, GREEN;var x:Int}"), node(FILE, PACKAGE, "hello.world", MEMBERS, al(
                node(ENUM_DEF, NAME, "Hello", MEMBERS, al(
                        node(ENUM_ENTRY, NAME, "RED"),
                        node(ENUM_ENTRY, NAME, "GREEN"),
                        node(FIELD, NAME, "x")
                )))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world enum class Hello {RED, GREEN,;var x:Int}"), node(FILE, PACKAGE, "hello.world", MEMBERS, al(
                node(ENUM_DEF, NAME, "Hello", MEMBERS, al(
                        node(ENUM_ENTRY, NAME, "RED"),
                        node(ENUM_ENTRY, NAME, "GREEN"),
                        node(FIELD, NAME, "x")
                )))));

    }


    @Test
    public void testScript() {
        System.out.println(YadsSerializer.serialize(TestUtils.parse("Script", "@file:JvmName<SomeType>(\"Foo\")\n(callBar())")));

        TestUtils.assertMatch(TestUtils.parse("Script", "@file:JvmName<SomeType>(\"Foo\")\n(callBar())"), node(SCRIPT,
                ANNOTATIONS, al(node(UNESCAPED_ANNOTATION, NAME, "JvmName",
                        TYPE_ARGUMENTS, al(node(TYPE, NAME, "SomeType")),
                        ARGS, al(node(CONST, VALUE, "Foo", TYPE, node(TYPE, NAME, "String", FULL_NAME, "kotlin.String"))))),
                BODY, al(node(METHOD_CALL))
        ));
    }

    @Test
    public void testFileAnnotation() {
        System.out.println(YadsSerializer.serialize(TestUtils.parseFile("@file:JvmName<SomeType>(\"Foo\")\npackage hello.world")));

        TestUtils.assertMatch(TestUtils.parseFile("@file:JvmName<SomeType>(\"Foo\")\npackage hello.world"), node(FILE,
                ANNOTATIONS, al(node(UNESCAPED_ANNOTATION, NAME, "JvmName",
                        TYPE_ARGUMENTS, al(node(TYPE, NAME, "SomeType")),
                        ARGS, al(node(CONST, VALUE, "Foo", TYPE, node(TYPE, NAME, "String", FULL_NAME, "kotlin.String"))))),
                PACKAGE, "hello.world"));
    }

    @Test
    public void testTokenFail() {
        TestUtils.parseStetement("val file = \"\"");

    }

    @Test
    public void testMethod() {
        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {public fun foo()}"), node(FILE,
                MEMBERS, al(node(CLASS_DEF, MEMBERS, al(node(METHOD_DEF, NAME, "foo", PARAMETERS, al(), MODIFIERS, al("public")))))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {fun foo() {var x: Int}}"), node(FILE,
                MEMBERS, al(node(CLASS_DEF, MEMBERS, al(node(METHOD_DEF, NAME, "foo", PARAMETERS, al(), BODY, node(BLOCK, STATEMENTS, al(node(NEW_VAR, NAME, "x")))))))));


        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {fun foo(var i:Int):Float {var x: Int}}"), node(FILE,
                MEMBERS, al(
                        node(CLASS_DEF, MEMBERS, al(
                                node(METHOD_DEF, NAME, "foo",
                                        PARAMETERS, al(node(NEW_VAR, NAME, "i", TYPE, node(TYPE, NAME, "Int"))),
                                        TYPE, node(TYPE, NAME, "Float"),
                                        BODY, node(BLOCK, STATEMENTS, al(node(NEW_VAR, NAME, "x")))
                                ))))));

        TestUtils.assertMatch(TestUtils.parseFile("package hello.world import world.hello class Hello {fun foo(val i:Int):Float = i * 2}}"), node(FILE,
                MEMBERS, al(
                        node(CLASS_DEF, MEMBERS, al(
                                node(METHOD_DEF, NAME, "foo",
                                        PARAMETERS, al(node(NEW_VAR, NAME, "i", TYPE, node(TYPE, NAME, "Int"))),
                                        TYPE, node(TYPE, NAME, "Float"),
                                        BODY, node(METHOD_CALL, ARGS, al(ref("i"), node(CONST, VALUE, 2)), EXPRESSION, ref("*"))
                                ))))));

    }

}
