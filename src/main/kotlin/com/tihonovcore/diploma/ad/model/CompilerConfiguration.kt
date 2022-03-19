package com.tihonovcore.diploma.ad.model

import java.io.File

class CompilerConfiguration(
    //TODO: use jar
    val pathToKotlincJvmFile: String
) {
    fun compile(file: File): CompilationResult {
        val beginTime = System.currentTimeMillis()
        val process = ProcessBuilder(pathToKotlincJvmFile, file.absolutePath).start()
        val output = process.inputStream.reader(Charsets.UTF_8).use {
            it.readText()
        }

        val errorOutput = process.errorStream.reader(Charsets.UTF_8).use { it.readText() }

        val success = process.waitFor() == 0

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
