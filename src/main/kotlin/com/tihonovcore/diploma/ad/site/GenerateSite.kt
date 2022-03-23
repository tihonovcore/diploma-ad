package com.tihonovcore.diploma.ad.site

import com.tihonovcore.diploma.ad.AdConfiguration.siteOutputPath
import com.tihonovcore.diploma.ad.model.Anomaly
import java.io.File

fun generateSite(anomalies: List<Anomaly>) {
    File(siteOutputPath).deleteRecursively()

    val anomalyList = anomalies.mapIndexed { index, anomaly ->
        """
            <div class="alert anomaly_$index">
                <a href="anomaly_$index.html">
                    ${index + 1}. ${anomaly.alert.javaClass.simpleName} 
                </a>
            </div>
        """.trimIndent()
    }.joinToString(separator = System.lineSeparator())

    anomalies.forEachIndexed { index, anomaly ->
        val alertMessage = "<h2 style=\"padding: 0% 2%\">GOT ALERT '${anomaly.alert.javaClass.simpleName}': ${anomaly.alertMessage}</h2>"

        //TODO: get file simpler
        val sourceCode = "<pre><code>${anomaly.compilationResults.first().file.readText()}</code></pre>"

        val compilationResults = anomaly.compilationResults.map { compilationResult ->
            val status = if (compilationResult.success) "succ" else "fail"
            """
                <div>
                    <div class="column $status compiler" style="width: 78%; padding: 1%">
                        kotlinc-${compilationResult.compilerConfiguration.version}
                    </div>                    
                    <div class="column $status time" style="width: 18%; padding: 1%">
                        ${compilationResult.usedTime}ms
                    </div>
                    <div class="column $status output" style="width: 98%; padding: 1%">
                        OUTPUT: ${compilationResult.output}
                    </div>
                </div>
            """.trimIndent()
        }.joinToString(separator = System.lineSeparator())

        val page = page(
            anomalyList,
            alertMessage,
            sourceCode,
            compilationResults,
            index
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
    compilationResults: String,
    pageIndex: Int
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
                
                .compiler {
                    border-top-left-radius: 10px;
                }
                
                .time {
                    border-top-right-radius: 10px;
                }
                
                .output {
                    border-bottom-left-radius: 10px;
                    border-bottom-right-radius: 10px;
                    margin-bottom: 5px;
                }
                
                .code {
                    background-color: #F1F2F2;
                    padding: 1% 2%;
                }
                
                .alert {
                    padding: 2%;
                }
                
                .anomaly_$pageIndex {
                    background-color: #B7D4FF;
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
                    <div class="column code" style="width: 33.5%">
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
