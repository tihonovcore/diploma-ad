package com.tihonovcore.diploma.ad.alert

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilationResult
import java.io.File

class FixedTimeAlert(val lowerBound: Long, val upperBound: Long) : Alert {
    private val alertName = this.javaClass.simpleName

    override fun check(file: File, compilationResults: List<CompilationResult>): List<Anomaly> {
        val anomalies = mutableListOf<Anomaly>()
        for (compilationResult in compilationResults) {
            val usedTime = compilationResult.usedTime

            if (usedTime < lowerBound) {
                val alertMessage = "compilationResult.usedTime($usedTime) < lowerBound($lowerBound)"
                anomalies += Anomaly(file, listOf(compilationResult), alertMessage, alertName)
            }

            if (usedTime > upperBound) {
                val alertMessage = "compilationResult.usedTime($usedTime) > upperBound($upperBound)"
                anomalies += Anomaly(file, listOf(compilationResult), alertMessage, alertName)
            }
        }

        return anomalies
    }
}
