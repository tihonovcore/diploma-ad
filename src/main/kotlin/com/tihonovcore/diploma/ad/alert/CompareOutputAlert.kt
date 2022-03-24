package com.tihonovcore.diploma.ad.alert

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilationResult
import java.io.File

class CompareOutputAlert : Alert {
    private val alertMessage = "Output different for compilers"
    private val alertName = this.javaClass.simpleName

    override fun check(file: File, compilationResults: List<CompilationResult>): List<Anomaly> {
        val anomalies = mutableListOf<Anomaly>()

        for (i in compilationResults.indices) {
            for (j in compilationResults.indices) {
                if (i >= j) continue

                val first = compilationResults[i]
                val second = compilationResults[j]

                if (first.output != second.output || first.errorOutput != second.errorOutput) {
                    anomalies += Anomaly(
                        file,
                        listOf(first, second),
                        alertMessage,
                        alertName
                    )
                }
            }
        }

        return anomalies
    }
}
