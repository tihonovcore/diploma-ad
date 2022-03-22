package com.tihonovcore.diploma.ad

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilerConfiguration
import com.tihonovcore.diploma.ad.alert.Alert
import com.tihonovcore.diploma.ad.alert.FixedTimeAlert
import java.io.File

class Environment {
    val files: List<File> = initFiles()
    val compilers: List<CompilerConfiguration> = initCompilers()
    val alerts: List<Alert> = initAlerts()

    val anomalies: MutableMap<CompilerConfiguration, MutableList<Anomaly>> = mutableMapOf()

    fun registerAnomaly(anomaly: Anomaly) {
        for (compilationResult in anomaly.compilationResults) {
            anomalyList(compilationResult.compilerConfiguration) += anomaly
        }
    }

    private fun anomalyList(compilerConfiguration: CompilerConfiguration): MutableList<Anomaly> {
        anomalies.putIfAbsent(compilerConfiguration, mutableListOf())
        return anomalies[compilerConfiguration]!!
    }

    private fun initFiles(): List<File> {
        val commonPrefix = "/Users/tihonovcore/diploma/diploma-ad/src/main/resources/kotlinFiles"
        return listOf(
            File("$commonPrefix/file1.kt"),
            File("$commonPrefix/file2.kt"),
            File("$commonPrefix/file3.kt"),
        )
    }

    /**
     * Компиляторы берутся отсюда * https://github.com/JetBrains/kotlin/releases?
     *
     * TODO: после добавления профилировщика придется пересобирать компиляторы
     */
    private fun initCompilers(): List<CompilerConfiguration> {
        val commonPrefix = "/Users/tihonovcore/diploma/diploma-ad/src/main/resources/compilers"
        return listOf(
            CompilerConfiguration("1.4.10", "$commonPrefix/kotlinc-1.4.10/bin/kotlinc-jvm"),
            CompilerConfiguration("1.5.31", "$commonPrefix/kotlinc-1.5.31/bin/kotlinc-jvm"),
            CompilerConfiguration("1.6.10", "$commonPrefix/kotlinc-1.6.10/bin/kotlinc-jvm"),
        )
    }

    private fun initAlerts() = listOf(
//        FixedMemoryAlert(5000L, 7500L),
        FixedTimeAlert(1500L, 1900L)
    )
}
