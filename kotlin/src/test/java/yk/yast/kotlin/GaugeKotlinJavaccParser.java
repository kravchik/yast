package yk.yast.kotlin;

import yk.jcommon.utils.BadException;
import yk.kotlin.gen.KotlinParser;
import yk.yast.common.YastNode;
import yk.yast.common.utils.GaugeTester;

import java.io.FileInputStream;

import static yk.yast.common.Words.FILE;
import static yk.yast.common.Words.NODE_TYPE;

/**
 * Created by Yuri Kravchik on 15.10.2018
 */
public class GaugeKotlinJavaccParser {

    public static void main(String[] args) {
        String fileName = "kotlin/src/test/resources/SimpleClass.kt";
        new GaugeTester("Kotlin JavaCC parser").testExecutionTime(() -> parseFile(fileName));
    }

    public static void parseFile(String fileName) {
        try {
            YastNode result = new KotlinParser(new FileInputStream(fileName)).File();
            if (!result.getString(NODE_TYPE).equals(FILE)) BadException.die("Error when parsing");
            //System.out.println(YadsSerializer.serialize(result));
            //return result;
        } catch (Exception e) {
            throw BadException.die(e);
        }

    }
}
