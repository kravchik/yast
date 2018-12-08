package yk.yast.common;

import yk.jcommon.collections.YList;
import yk.jcommon.collections.YMap;
import yk.jcommon.collections.YSet;
import yk.jcommon.utils.Reflector;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static yk.jcommon.collections.YHashMap.hm;

/**
 * Created with IntelliJ IDEA.
 * User: yuri
 * Date: 08/01/15
 * Time: 12:56
 */
public class Words {
    // CLASS_DEF(NAME=World, FULL_NAME=hello.World, ...)
    //... TYPE = TYPE(NAME=World, FULL_NAME=hello.World, TYPE=TYPE(NAME=Class), RESOLVED_TYPE=^Class)

    //type: Class instance? class instance?
    // TYPE(NAME...) TYPE(NAME..., STATIC, true)

    public static final String TYPE_RESOLVED_REF = "TYPE_RESOLVED_REF";//id of the type node of the sentence result type

    public static final String SENTENCE_RESULT_TYPE_FULL_NAME = "SENTENCE_RESULT_TYPE_FULL_NAME";//id of the type node of the sentence result type

    public static final String METHOD_REF = "METHOD_REF";

    public static final String OP_ADD = "OP_ADD";
    public static final String OP_SUB = "OP_SUB";
    public static final String OP_MUL = "OP_MUL";
    public static final String OP_DIV = "OP_DIV";

    public static final String ID = "ID";
    public static final String VALUE = "VALUE";
    public static final String LITERAL = "LITERAL";//TODO types in InlineUtils

    public static final String SEQ = "SEQ";
    public static final String GENERATED = "GENERATED";
    public static final String FINAL = "FINAL";
    public static final String CONST = "CONST";
    public static final String CONTINUE = "CONTINUE";
    public static final String BREAK = "BREAK";
    public static final String IMPORT = "IMPORT";
    public static final String IMPORTS = "IMPORTS";
    public static final String STATIC_IMPORTS = "STATIC_IMPORTS";
    public static final String FILE = "FILE";
    public static final String SCRIPT = "SCRIPT";
    public static final String CLASS_DEF = "CLASS_DEF";
    public static final String CLASS_REF = "CLASS_REF";
    public static final String METHOD_DEF = "METHOD_DEF";
    public static final String START_POS = "START_POS";
    public static final String POSTFIX = "POSTFIX";//OPERATOR, EXPRESSION
    public static final String PREFIX = "PREFIX";//OPERATOR, EXPRESSION
    public static final String END_POS = "END_POS";
    public static final String METHOD_CALL = "METHOD_CALL";
    public static final String ENHANCED_FOR_LOOP = "ENHANCED_FOR_LOOP";
    public static final String VARIABLE = "VARIABLE";
    public static final String ARRAY_ACCESS = "ARRAY_ACCESS";//EXPRESSION, ARGS
    public static final String INDEX = "INDEX";
    public static final String IS_STATIC = "IS_STATIC";
    public static final String ANNOTATIONS = "ANNOTATIONS";
    public static final String ANNOTATION_OVERRIDES = "ANNOTATION_OVERRIDES";
    public static final String IS_ABSTRACT = "IS_ABSTRACT";
    public static final String IS_PUBLIC = "IS_PUBLIC";
    public static final String IS_PRIVATE = "IS_PRIVATE";
    public static final String PARAMETERIZED = "PARAMETERIZED";
    public static final String MAIN_TYPE = "MAIN_TYPE";
    public static final String TYPE_ARGUMENTS = "TYPE_ARGUMENTS";
    public static final String TYPE_PARAMETER = "TYPE_PARAMETER";
    public static final String TYPE_PARAMETERS = "TYPE_PARAMETERS";
    public static final String PARENS = "PARENS";
    public static final String EXPRESSION = "EXPRESSION";
    public static final String STATEMENT = "STATEMENT";
    public static final String CAST = "CAST";//TYPE, EXPRESSION [OPERATOR]
    public static final String ARGS = "ARGS";
    public static final String PARAMETERS = "PARAMETERS";
    public static final String MEMBERS = "MEMBERS";
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    public static final String BLOCK = "BLOCK";//STATEMENTS
    public static final String FIELD = "FIELD";
    public static final String IF = "IF";//CONDITION THEN ELSE?
    public static final String NEW_VAR = "NEW_VAR";//TYPE NAME EXPRESSION?
    public static final String NEW_INSTANCE = "NEW_INSTANCE";
    public static final String INSTANCE_OF = "INSTANCE_OF";
    public static final String REF = "REF";
    public static final String RESOLVED_REF = "RESOLVED_REF";
    public static final String LOCAL_VAR_REF = "LOCAL_VAR_REF";
    public static final String DOT = "DOT";//LEFT NAME OPERATOR
    public static final String RIGHT_DOT = "RIGHT_DOT";//LEFT RIGHT OPERATOR
    public static final String STANDARD = "STANDARD";
    public static final String NODE_TYPE = "NODE_TYPE";
    public static final String NAME = "NAME";
    public static final String FULL_NAME = "FULL_NAME";
    public static final String NAMESPACE = "NAMESPACE";//NAME {REF} BODY {List}
    public static final String PACKAGE = "PACKAGE";
    public static final String TYPE = "TYPE";
    public static final String EXPRESSION_TYPE = "EXPRESSION_TYPE";
    public static final String RETURN_TYPE = "RETURN_TYPE";
    public static final String METHOD = "METHOD";
    public static final String BODY = "BODY";//NODE_TYE BLOCK, STATEMENTS []
    public static final String THIS = "this";
    public static final String ASSIGNMENT = "ASSIGNMENT";//LEFT RIGHT
    public static final String ASSIGNMENT_OP = "ASSIGNMENT_OP";//+=, -=, etc  LEFT, RIGHT, OPERATOR
    public static final String OPERATOR = "OPERATOR";//+, -, etc
    public static final String FOR_LOOP = "FOR_LOOP";
    public static final String WHILE_LOOP = "WHILE_LOOP";
    public static final String DO_WHILE_LOOP = "DO_WHILE_LOOP";
    public static final String INITIALIZERS = "INITIALIZERS";
    public static final String CONDITION = "CONDITION";
    public static final String CONDITIONAL = "CONDITIONAL";//CONDITION THEN ELSE
    public static final String THEN = "THEN";
    public static final String ELSE = "ELSE";
    public static final String UPDATES = "UPDATES";
    public static final String CANDIDATES = "CANDIDATES";
    public static final String STATEMENTS = "STATEMENTS";
    public static final String RETURN = "RETURN";//EXPRESSION?
    public static final String THROW = "THROW";
    public static final String EXTENDS = "EXTENDS";
    public static final String IMPLEMENTS = "IMPLEMENTS";
    public static final String INCREMENT = "INCREMENT";
    public static final String DECREMENT = "DECREMENT";
    public static final String PRE_INCREMENT = "PRE_INCREMENT";
    public static final String PRE_DECREMENT = "PRE_DECREMENT";
    public static final String BRACKETS = "BRACKETS";
    public static final String IN_OUT = "IN_OUT";

    public static final String ELVIS = "ELVIS";//EXPRESSION, ELSE


    public static final String TRY = "TRY";//body, catch[], finally?
    public static final String CATCH = "CATCH";
    public static final String FINALLY = "FINALLY";

    //public static final String TYPE_REF = "TYPE_REF";//TODO simple REF, because we can float.class, and can access static members... or TYPE_REF ok?

    public static final String _SEEN = "_SEEN";

    public static final String MODIFIERS = "MODIFIERS";

    public static final String CS_PARAMETER_MODIFIER = "CS_PARAMETER_MODIFIER";
    public static final String CS_ARGUMENT_MODIFIER = "CS_ARGUMENT_MODIFIER";
    public static final String CS_ATTRIBUTE_DIRTY = "CS_ATTRIBUTE_DIRTY";//TODO structured




    //move?
    public static final String ADD_FLOATS = "ADD_FLOATS";
    public static final String SUB_FLOATS = "SUB_FLOATS";
    public static final String MUL_FLOATS = "MUL_FLOATS";
    public static final String DIV_FLOATS = "DIV_FLOATS";
    public static final String MALLOC = "MALLOC";
    public static final String MALLOC_SIZE = "MALLOC_SIZE";
    public static final String GET_FIELD = "GET_FIELD";


    public static YSet<String> allWords = Reflector.getAllFieldsInHierarchy(Words.class).map(f -> f.getName()).toSet();

    public static YMap nodeMap(String nodeType, Object... other) {
        YMap result = hm(NODE_TYPE, nodeType);
        for (int i = 0; i < other.length; i += 2) result.put(other[i], other[i+1]);
        return result;
    }

    public static YMap mapRef(String name) {
        return nodeMap(REF, NAME, name);
    }

    public static YMap mapDot(Object left, String right) {
        return nodeMap(DOT, LEFT, left, NAME, right);
    }

    public static void main(String[] args) {
        checkFields();
    }

    private static void checkFields() {
        YList<Field> fields = Reflector.getAllFieldsInHierarchy(Words.class);
        for (Field field : fields) {
            if (field.getType() == String.class && Modifier.isStatic(field.getModifiers())) {
                String value = Reflector.get(null, field);
                if (!value.toLowerCase().equals(field.getName().toLowerCase())) System.out.println("WARNING: " + field.getName() + " != " + value);
            }
        }
    }
}
