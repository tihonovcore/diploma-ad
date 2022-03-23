package com.tihonovcore.diploma.ad.model

import com.tihonovcore.diploma.ad.AdConfiguration.compilationOutputPath
import java.io.File

class CompilerConfiguration(
    val version: String,
    //TODO: use jar
    val pathToKotlincJvmFile: String
) {
    fun compile(file: File): CompilationResult {
        val beginTime = System.currentTimeMillis()

        val pathToOutputJar = "$compilationOutputPath/kotlinc-$version/${file.name}.jar"
        File(pathToOutputJar).parentFile.mkdirs()

        val process = ProcessBuilder(pathToKotlincJvmFile, file.absolutePath, "-d", pathToOutputJar).start()
        val output = process.inputStream.reader(Charsets.UTF_8).use {
            it.readText()
        }

        val errorOutput = process.errorStream.reader(Charsets.UTF_8).use { it.readText() }

        val success = process.waitFor() == 0

        return CompilationResult(
            compilerConfiguration = this,
            file,
            success,
            output,
            errorOutput,
            System.currentTimeMillis() - beginTime,
            100
        )
    }
}
