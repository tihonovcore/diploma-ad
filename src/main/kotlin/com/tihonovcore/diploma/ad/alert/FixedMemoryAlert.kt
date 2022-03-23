package com.tihonovcore.diploma.ad.alert

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilationResult
import java.io.File

class FixedMemoryAlert(val lowerBound: Long, val upperBound: Long) : Alert {
    private val alertName = this.javaClass.simpleName

    override fun check(file: File, compilationResults: List<CompilationResult>): List<Anomaly> {
        val anomalies = mutableListOf<Anomaly>()
        for (compilationResult in compilationResults) {
            if (compilationResult.usedMemory < lowerBound) {
                val alertMessage = "compilationResult.usedMemory < lowerBound"
                anomalies += Anomaly(file, listOf(compilationResult), alertMessage, alertName)
            }

            if (compilationResult.usedMemory > upperBound) {
                val alertMessage = "compilationResult.usedMemory > upperBound"
                anomalies += Anomaly(file, listOf(compilationResult), alertMessage , alertName)
            }
        }

        return anomalies
    }
}
