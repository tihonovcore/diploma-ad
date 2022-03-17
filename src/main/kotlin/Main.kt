import com.tihonovcore.diploma.ad.Environment
import com.tihonovcore.diploma.ad.model.CompilationResult

/*
TODO:
 кажется разумнее отдавать компилятору сразу пачку файлов - сэкономит время
 */
fun main() {
    val environment = Environment()

    for (file in environment.files) {
        val fileCompilationResults = mutableListOf<CompilationResult>()
        for (compiler in environment.compilers) {
            fileCompilationResults += compiler.compile(file)
        }

        for (alert in environment.alerts) {
            val anomalies = alert.check(fileCompilationResults)
            anomalies.forEach { anomaly -> environment.registerAnomaly(anomaly) }
        }
    }
}
