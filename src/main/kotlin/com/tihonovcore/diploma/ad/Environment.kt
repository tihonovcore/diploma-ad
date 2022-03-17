package com.tihonovcore.diploma.ad

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilerConfiguration
import com.tihonovcore.diploma.ad.alert.Alert
import com.tihonovcore.diploma.ad.alert.FixedMemoryAlert
import com.tihonovcore.diploma.ad.alert.FixedTimeAlert
import java.io.File

class Environment {
    val files: List<File> = initFiles()
    val compilers: List<CompilerConfiguration> = initCompilers()
    val alerts: List<Alert> = initAlerts()

    val anomalies: MutableMap<File, Pair<CompilerConfiguration, Alert>> = mutableMapOf()

    fun registerAnomaly(anomaly: Anomaly) {
        val file = anomaly.compilationResult.file
        val compilerConfiguration = anomaly.compilationResult.compilerConfiguration

        anomalies[file] = Pair(compilerConfiguration, anomaly.alert)
    }

    private fun initFiles(): List<File> {
        //TODO
        return listOf()
    }

    private fun initCompilers(): List<CompilerConfiguration> = listOf(
        //TODO
    )

    private fun initAlerts() = listOf(
        FixedMemoryAlert(5000L, 7500L),
        FixedTimeAlert(100L, 200L)
    )
}