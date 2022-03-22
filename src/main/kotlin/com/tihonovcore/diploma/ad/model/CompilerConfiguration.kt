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

        /*
        TODO: если в ad-output есть и jar, и CompilationResult, то просто прочитать
              CompilationResult и закончить метод.
         */

        val pathToOutputJar = "$compilationOutputPath/kotlinc-$version/${file.name}.jar"
        File(pathToOutputJar).parentFile.mkdirs()

        val process = ProcessBuilder(pathToKotlincJvmFile, file.absolutePath, "-d", pathToOutputJar).start()
        val output = process.inputStream.reader(Charsets.UTF_8).use {
            it.readText()
        }

        val errorOutput = process.errorStream.reader(Charsets.UTF_8).use { it.readText() }

        val success = process.waitFor() == 0

        //TODO: сохранять CompilationResult в ad-output
        return CompilationResult(
            compilerConfiguration = this,
            file,
            success,
            System.currentTimeMillis() - beginTime,
            100,
            output + errorOutput
        )
    }
}
