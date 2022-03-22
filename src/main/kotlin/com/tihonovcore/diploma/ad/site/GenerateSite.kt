package com.tihonovcore.diploma.ad.site

import com.tihonovcore.diploma.ad.AdConfiguration.siteOutputPath
import com.tihonovcore.diploma.ad.model.Anomaly
import java.io.File

fun generateSite(anomalies: List<Anomaly>) {
    File(siteOutputPath).deleteRecursively()

    val anomalyList = anomalies.mapIndexed { index, anomaly ->
        """
            <div>
                <a href="anomaly_$index.html">
                    ${anomaly.alertMessage}
                </a>    
            </div>
        """.trimIndent()
    }.joinToString(separator = System.lineSeparator())

    anomalies.forEachIndexed { index, anomaly ->
        val alertMessage = "<h2>GOT ALERT '${anomaly.alert.javaClass.simpleName}': ${anomaly.alertMessage}</h2>"

        //TODO: get file simpler
        val sourceCode = "<pre><code>${anomaly.compilationResults.first().file.readText()}</code></pre>"

        val compilationResults = anomaly.compilationResults.map { compilationResult ->
            val status = if (compilationResult.success) "succ" else "fail"
            """
                <div>
                    <div class="column $status" style="width: 80%">kotlinc-${compilationResult.compilerConfiguration.version}</div>                    
                    <div class="column $status" style="width: 20%">${compilationResult.usedTime}ms</div>
                    <div class="column $status" style="width: 100%">OUTPUT: ${compilationResult.output}</div>
                </div>
            """.trimIndent()
        }.joinToString(separator = System.lineSeparator())

        val page = page(
            anomalyList,
            alertMessage,
            sourceCode,
            compilationResults
        )

        val indexHtmlFile = File("$siteOutputPath/anomaly_$index.html")
        indexHtmlFile.parentFile.mkdirs()
        indexHtmlFile.createNewFile()

        indexHtmlFile.writeText(page)
    }
}

private fun page(
    anomalyList: String,
    alertMessage: String,
    sourceCode: String,
    compilationResults: String
): String {
    return """
        <html>
        <head>
            <style>
                table, th, td {
                    border: 1px solid black;
                    border-collapse: collapse;
                }
                
                .column {
                    float: left;
                }
                
                .succ {
                    background-color: darkseagreen;
                }

                .fail {
                    background-color: indianred;
                }
            </style>
        </head>
        <body>
            <div class="column" style="width: 20%">
                $anomalyList
            </div>
                <div class="column" style="width: 80%">
                    <div class="column" style="width: 100%">
                        $alertMessage
                    </div>
                    <div class="column" style="width: 37.5%">
                        $sourceCode
                    </div>
                    <div class="column" style="width: 62.5%">
                        $compilationResults
                    </div>
                </div>
            </div>
        </body>
        </html>
    """.trimIndent()
}
