package com.tihonovcore.diploma.ad.alert

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilationResult

class FixedTimeAlert(val lowerBound: Long, val upperBound: Long) : Alert {
    override fun check(compilationResults: List<CompilationResult>): List<Anomaly> {
        val anomalies = mutableListOf<Anomaly>()
        for (compilationResult in compilationResults) {
            val usedTime = compilationResult.usedTime

            if (usedTime < lowerBound) {
                val alertMessage = "compilationResult.usedTime($usedTime) < lowerBound($lowerBound)"
                anomalies += Anomaly(compilationResult, alertMessage, this)
            }

            if (usedTime > upperBound) {
                val alertMessage = "compilationResult.usedTime($usedTime) > upperBound($upperBound)"
                anomalies += Anomaly(compilationResult, alertMessage, this)
            }
        }

        return anomalies
    }
}
