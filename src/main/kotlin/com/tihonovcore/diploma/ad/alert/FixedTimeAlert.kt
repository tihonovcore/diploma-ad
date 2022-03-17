package com.tihonovcore.diploma.ad.alert

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilationResult

class FixedTimeAlert(val lowerBound: Long, val upperBound: Long) : Alert {
    override fun check(compilationResults: List<CompilationResult>): List<Anomaly> {
        val anomalies = mutableListOf<Anomaly>()
        for (compilationResult in compilationResults) {
            if (compilationResult.usedTime < lowerBound) {
                anomalies += Anomaly(compilationResult, "compilationResult.usedTime < lowerBound", this)
            }

            if (compilationResult.usedTime > upperBound) {
                anomalies += Anomaly(compilationResult, "compilationResult.usedTime > upperBound", this)
            }
        }

        return anomalies
    }
}
