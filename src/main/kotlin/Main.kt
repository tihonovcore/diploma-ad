import com.tihonovcore.diploma.ad.Environment
import com.tihonovcore.diploma.ad.model.CompilationResult
import com.tihonovcore.diploma.ad.site.generateSite

/*
TODO:
 кажется разумнее отдавать компилятору сразу пачку файлов - сэкономит время
 */
fun main() {
    val environment = Environment()

    for (file in environment.files) {
        println("FILE: $file")

        val fileCompilationResults = mutableListOf<CompilationResult>()
        for (compiler in environment.compilers) {
            print("COMPILE WITH ${compiler.version} ")
            fileCompilationResults += compiler.compile(file)
            println("IS SUCCESS: " + fileCompilationResults.last().success)
        }

        for (alert in environment.alerts) {
            val anomalies = alert.check(fileCompilationResults)
            anomalies.forEach { anomaly ->
                val compilerVersion = anomaly.compilationResult.compilerConfiguration.version
                val isCompilationSuccess = anomaly.compilationResult.success

                println("!!!ALERT!!!: ${anomaly.alert.javaClass.name}")
                println("ALERT MESSAGE: ${anomaly.alertMessage}")
                println("COMPILATION WITH $compilerVersion IS SUCCESS: $isCompilationSuccess")
                println("COMPILER OUTPUT:")
                println(anomaly.compilationResult.output)

                environment.registerAnomaly(anomaly)
            }
        }

        println()
        println()
    }

    generateSite(environment.anomalies)
}
