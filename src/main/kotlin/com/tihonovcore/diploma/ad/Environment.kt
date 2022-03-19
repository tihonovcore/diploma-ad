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
        return listOf(
            File("/Users/tihonovcore/diploma/diploma-ad/kotlinFiles/file1.kt"),
            File("/Users/tihonovcore/diploma/diploma-ad/kotlinFiles/file2.kt"),
            File("/Users/tihonovcore/diploma/diploma-ad/kotlinFiles/file3.kt"),
        )
    }

    /**
     * Компиляторы берутся отсюда * https://github.com/JetBrains/kotlin/releases?
     *
     * TODO: после добавления профилировщика придется пересобирать компиляторы
     */
    private fun initCompilers(): List<CompilerConfiguration> {
        val commonPrefix = "/Users/tihonovcore/diploma/diploma-ad/compilers"
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