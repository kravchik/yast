package yk.yast.kotlin.benchmark

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.profile.GCProfiler
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import yk.kotlin.gen.KotlinParser
import yk.yast.common.YastNode
import yk.yast.kotlin.intellij.IntelliJParser
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.util.concurrent.TimeUnit

@Fork(value = 1, jvmArgsPrepend = ["-Xmx128m"])
@Measurement(iterations = 7, time = 1, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 7, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Thread)
@Threads(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
open class ParserBenchmark {
    val fileName = "kotlin/src/test/resources/SimpleClass.kt"

    val intellijParser = IntelliJParser()

    val javaccParser = KotlinParser(ByteArrayInputStream(ByteArray(0)))

    @Benchmark
    fun intellij(): YastNode = intellijParser.parse(fileName)

    @Benchmark
    fun javacc(): YastNode =
            FileInputStream(fileName).use {
                javaccParser.ReInit(it)
                javaccParser.File()
            }
}

fun main(args: Array<String>) {
    println(ParserBenchmark::class.java.simpleName)
    val options = OptionsBuilder()
            .include(ParserBenchmark::class.java.simpleName)
            .addProfiler(GCProfiler::class.java)
            .detectJvmArgs()
            .build()
    Runner(options).run()
}
