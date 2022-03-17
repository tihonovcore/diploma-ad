package com.tihonovcore.diploma.ad.model

import java.io.File
import java.nio.file.Path

class CompilerConfiguration(
    val pathToKotlinCompilerJar: Path
) {
    fun compile(file: File): CompilationResult {
        TODO()
    }
}