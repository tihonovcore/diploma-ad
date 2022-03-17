package com.tihonovcore.diploma.ad.alert

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilationResult

class FixedMemoryAlert(val lowerBound: Long, val upperBound: Long) : Alert {
    override fun check(compilationResults: List<CompilationResult>): List<Anomaly> {
        val anomalies = mutableListOf<Anomaly>()
        for (compilationResult in compilationResults) {
            if (compilationResult.usedMemory < lowerBound) {
                anomalies += Anomaly(compilationResult, "compilationResult.usedMemory < lowerBound", this)
            }

            if (compilationResult.usedMemory > upperBound) {
                anomalies += Anomaly(compilationResult, "compilationResult.usedMemory > upperBound", this)
            }
        }

        return anomalies
    }
}
