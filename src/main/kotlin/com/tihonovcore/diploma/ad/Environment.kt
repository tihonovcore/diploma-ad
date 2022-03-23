package com.tihonovcore.diploma.ad

import com.google.gson.Gson
import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilerConfiguration
import com.tihonovcore.diploma.ad.alert.Alert
import com.tihonovcore.diploma.ad.alert.CompareCompilationSuccessAlert
import com.tihonovcore.diploma.ad.alert.FixedTimeAlert
import com.tihonovcore.diploma.ad.model.CompilationResult
import java.io.File

class Environment {
    val files: List<File> = initFiles()
    val compilers: List<CompilerConfiguration> = initCompilers()
    val alerts: List<Alert> = initAlerts()

    val anomalies: MutableList<Anomaly> = mutableListOf()

    fun registerAnomaly(anomaly: Anomaly) {
        if (anomaly !in anomalies) {
            anomalies += anomaly
        }
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
     * TODO: после добавления профилировщика возможно придется пересобирать компиляторы
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
        CompareCompilationSuccessAlert(),
//        FixedMemoryAlert(5000L, 7500L),
        FixedTimeAlert(1500L, 1900L)
    )

    fun findCompilationResult(file: File, compilerConfiguration: CompilerConfiguration): CompilationResult? {
        val fileName = getCompilationResultId(file, compilerConfiguration)
        val compilationResultFile = File(AdConfiguration.compilationResultOutputPath + "/$fileName.json")
        if (!compilationResultFile.exists()) {
            return null
        }

        return Gson().fromJson(compilationResultFile.readText(), CompilationResult::class.java)
    }

    fun saveCompilationResult(compilationResult: CompilationResult) {
        val fileName = getCompilationResultId(compilationResult.file, compilationResult.compilerConfiguration)
        val file = File(AdConfiguration.compilationResultOutputPath + "/$fileName.json")
        file.parentFile.mkdirs()
        file.createNewFile()

        val json = Gson().toJson(compilationResult)
        file.writeText(json)
    }

    private fun getCompilationResultId(file: File, compilerConfiguration: CompilerConfiguration): String {
        return file.name + "__kotlinc-" + compilerConfiguration.version
    }
}
