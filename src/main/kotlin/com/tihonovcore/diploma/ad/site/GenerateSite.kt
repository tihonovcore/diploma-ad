package com.tihonovcore.diploma.ad.site

import com.tihonovcore.diploma.ad.AdConfiguration
import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilerConfiguration
import java.io.File

//TODO: реализована группировка по конфигурации, добавить группировку по аномалии, по файлу

//TODO: рендерить красивее

fun generateSite(map: MutableMap<CompilerConfiguration, MutableList<Anomaly>>) {
    val body = StringBuilder()
    body.append("<table>")
    map.toList().forEachIndexed { configurationIndex, (compilerConfiguration, anomalies) ->
        body.append("<tr>")
        body.append("<td rowspan=${anomalies.size + 1}>") //TODO: why +1?)))
        body.append(compilerConfiguration.version)
        body.append("</td>")
        body.append("</tr>")
        anomalies.forEachIndexed { anomalyIndex, anomaly ->
            body.append("<tr>")
            body.append("<td>")
            body.append("<a href=\"anomaly_${configurationIndex}_$anomalyIndex.html\">")
            body.append(anomaly.alertMessage)
            body.append("</a>")
            body.append("</td>")
            body.append("</tr>")
        }
    }
    body.append("</table>")

    val page = page(body.toString())
    val indexHtmlFile = File("${AdConfiguration.siteOutputPath}/index.html")
    indexHtmlFile.parentFile.mkdirs()
    indexHtmlFile.createNewFile()

    indexHtmlFile.writeText(page)

    createAnomaliesPages(map)
}

fun createAnomaliesPages(map: MutableMap<CompilerConfiguration, MutableList<Anomaly>>) {
    map.toList().forEachIndexed { configurationIndex, (_, anomalies) ->
        anomalies.forEachIndexed { anomalyIndex, anomaly ->
            val body = with(StringBuilder()) {
                append("<a href=\"index.html\">Config2Anomalies</a>")
                append("<h2>GOT ALERT '${anomaly.alert.javaClass.simpleName}': ${anomaly.alertMessage}</h2>")
                append("<h4>WHILE COMPILING</h4> '${anomaly.compilationResult.file}' <h4>WITH kotlinc-${anomaly.compilationResult.compilerConfiguration.version}</h4>")
                val code = anomaly.compilationResult.file.readText()
                append("<pre><code>$code</code></pre>")

                if (anomaly.compilationResult.success) {
                    append("<h4>COMPILATION SUCCESS, SPENT ${anomaly.compilationResult.usedTime}ms</h4>")
                } else {
                    append("<h4>COMPILATION FAILED</b4>")
                }

                append("<h4>OUTPUT</h4>")
                append(anomaly.compilationResult.output)
            }

            val page = page(body.toString())
            val anomalyFile = File("${AdConfiguration.siteOutputPath}/anomaly_${configurationIndex}_$anomalyIndex.html")
            anomalyFile.parentFile.mkdirs()
            anomalyFile.createNewFile()

            anomalyFile.writeText(page)
        }
    }
}

private fun page(body: String): String {
    return """
        <html>
        <head>
            <style>
                table, th, td {
                    border: 1px solid black;
                    border-collapse: collapse;
                }
            </style>
        </head>
        <body>
        $body
        </body>
        </html>
    """.trimIndent()
}
