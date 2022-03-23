package com.tihonovcore.diploma.ad.model

import java.io.File

data class Anomaly(
    val file: File,
    val compilationResults: List<CompilationResult>,
    val alertMessage: String,
    val alertName: String
) {
    override fun toString(): String {
        return "$alertName: $alertMessage"
    }
}
