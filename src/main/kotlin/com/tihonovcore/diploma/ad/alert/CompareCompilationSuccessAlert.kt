package com.tihonovcore.diploma.ad.alert

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilationResult

class CompareCompilationSuccessAlert : Alert {
    override fun check(compilationResults: List<CompilationResult>): List<Anomaly> {
        if (compilationResults.all { it.success } || compilationResults.none { it.success }) {
            return emptyList()
        }

        return listOf(
            Anomaly(compilationResults, "Some compilations are success, but some aren't", this)
        )
    }
}