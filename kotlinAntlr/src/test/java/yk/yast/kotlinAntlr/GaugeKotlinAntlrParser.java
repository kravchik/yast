package yk.yast.kotlinAntlr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.TerminalNode;
import yk.jcommon.utils.BadException;
import yk.kotlin.antlr.KotlinLexer;
import yk.kotlin.antlr.KotlinParser;
import yk.kotlin.antlr.KotlinParserBaseVisitor;
import yk.yast.common.utils.GaugeTester;

import java.nio.charset.Charset;
import java.nio.file.Paths;

/**
 * Created by Yuri Kravchik on 25.05.17.
 */
public class GaugeKotlinAntlrParser {

    public static void main(String[] args) {
        String fileName = "kotlin/src/test/resources/SimpleClass.kt";
        new GaugeTester("Kotlin ANTLR parser").testExecutionTime(() -> parse(fileName));
    }

    private static void parse(String fileName) {
        try {
            CharStream charStream = CharStreams.fromPath(Paths
                            .get(fileName)
                    , Charset.defaultCharset());
            Lexer lex = new KotlinLexer(charStream);

            CommonTokenStream tokenStream = new CommonTokenStream(lex);
            KotlinParser parser = new KotlinParser(tokenStream);
            KotlinParser.KotlinFileContext ctx = parser.kotlinFile();

            //KotlinParserBaseVisitor THIS = new MyKotlinParserBaseVisitor();
            //THIS.visitKotlinFile(ctx);
        } catch(Exception e) {
            BadException.die(e);
        }
    }

    private static class MyKotlinParserBaseVisitor extends KotlinParserBaseVisitor {
        @Override
        public Object visitKotlinFile(KotlinParser.KotlinFileContext ctx) {
            System.out.println("res: " + ctx.packageHeader().accept(this));
            return super.visitKotlinFile(ctx);
        }

        @Override
        public Object visitSimpleIdentifier(KotlinParser.SimpleIdentifierContext ctx) {
            System.out.println(ctx.getText());
            return super.visitSimpleIdentifier(ctx);
        }

        @Override
        public Object visitTerminal(TerminalNode node) {
            return node.getText();
        }
    }
}
