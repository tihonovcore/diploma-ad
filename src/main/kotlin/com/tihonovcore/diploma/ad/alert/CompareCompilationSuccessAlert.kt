package com.tihonovcore.diploma.ad.alert

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilationResult
import java.io.File

class CompareCompilationSuccessAlert : Alert {
    private val alertMessage = "Some compilations are success, but some aren't"
    private val alertName = this.javaClass.simpleName

    override fun check(file: File, compilationResults: List<CompilationResult>): List<Anomaly> {
        if (compilationResults.all { it.success } || compilationResults.none { it.success }) {
            return emptyList()
        }

        return listOf(
            Anomaly(file, compilationResults, alertMessage, alertName)
        )
    }
}
