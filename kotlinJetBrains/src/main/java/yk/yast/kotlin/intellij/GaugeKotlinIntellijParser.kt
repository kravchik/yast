package yk.yast.kotlin.intellij

import com.intellij.openapi.util.Disposer
import com.intellij.psi.PsiManager
import com.intellij.testFramework.LightVirtualFile
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.psi.*
import yk.jcommon.utils.IO
import yk.yast.common.Words.*
import yk.yast.common.YastNode
import yk.yast.common.YastNode.node
import yk.yast.common.utils.GaugeTester

/**
 * Created by Yuri Kravchik on 05.12.2018
 */

fun main(args: Array<String>) {
    val path = "kotlin/src/test/resources/vec3.kt"
    GaugeTester("Kotlin IntelliJ parser (without analyzer)").testExecutionTime{ parseFile("vec3.kt", IO.readFile(path))}
}

fun parseFile(fileName: String, src: String) : Any {
    val proj = lazy {
        KotlinCoreEnvironment.createForProduction(
                Disposer.newDisposable(),
                CompilerConfiguration(),
                EnvironmentConfigFiles.JVM_CONFIG_FILES
        ).project
    }

    val ktFile = PsiManager.getInstance(proj.value).findFile(LightVirtualFile(fileName, KotlinFileType.INSTANCE, src)) as KtFile
    //return ktFile
    return convert(ktFile) as YastNode
}

private fun convert(e: Any?): Any? {
    if (e == null) return null;
    //println(e::class.simpleName)
    if (e is KtFile) {
        return node(FILE, MEMBERS, convertList(e.declarations))
    } else if (e is KtClass) {
        return node(CLASS_DEF, NAME, e.name, MEMBERS, convertList(e.declarations))
    } else if (e is KtProperty) {
        return node(NEW_VAR, NAME, e.name)
    } else if (e is KtNamedFunction) {
        return node(METHOD_DEF, NAME, e.name, PARAMETERS, convertList(e.valueParameters), BODY, convert(e.bodyExpression));
    } else if (e is KtBlockExpression) {
        return node(BLOCK, STATEMENTS, convertList(e.statements));
    }
    return node(VALUE, VALUE, e::class.simpleName)
}

private fun convertList(e: List<Any?>): Any {
    //println(e.size)
    val result:MutableCollection<Any?> = ArrayList()
    for (t in e) {
        result.add(convert(t))
    }
    return result
}


