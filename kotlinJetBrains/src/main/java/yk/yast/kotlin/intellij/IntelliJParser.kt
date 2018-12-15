package yk.yast.kotlin.intellij

import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.local.CoreLocalFileSystem
import com.intellij.psi.PsiManager
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.psi.*
import yk.yast.common.Words
import yk.yast.common.YastNode
import java.io.File

public class IntelliJParser {
    private val proj by lazy {
        KotlinCoreEnvironment.createForProduction(
                Disposer.newDisposable(),
                CompilerConfiguration(),
                EnvironmentConfigFiles.JVM_CONFIG_FILES
        ).project
    }
    val fileSystem = CoreLocalFileSystem()

    public fun parse(fileName: String): YastNode {
        val file = fileSystem.findFileByIoFile(File(fileName))!!
        val ktFile = PsiManager.getInstance(proj).findFile(file) as KtFile
        return convert(ktFile) as YastNode
    }

    private fun convert(e: Any?): Any? {
        if (e == null) return null;
        //println(e::class.simpleName)
        if (e is KtFile) {
            return YastNode.node(Words.FILE, Words.MEMBERS, convertList(e.declarations))
        } else if (e is KtClass) {
            return YastNode.node(Words.CLASS_DEF, Words.NAME, e.name, Words.MEMBERS, convertList(e.declarations))
        } else if (e is KtProperty) {
            return YastNode.node(Words.NEW_VAR, Words.NAME, e.name)
        } else if (e is KtNamedFunction) {
            return YastNode.node(Words.METHOD_DEF, Words.NAME, e.name, Words.PARAMETERS, convertList(e.valueParameters), Words.BODY, convert(e.bodyExpression));
        } else if (e is KtBlockExpression) {
            return YastNode.node(Words.BLOCK, Words.STATEMENTS, convertList(e.statements));
        }
        return YastNode.node(Words.VALUE, Words.VALUE, e::class.simpleName)
    }

    private fun convertList(e: List<Any?>): Any {
        //println(e.size)
        val result: MutableCollection<Any?> = ArrayList()
        for (t in e) {
            result.add(convert(t))
        }
        return result
    }
}
