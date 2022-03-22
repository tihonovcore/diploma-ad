package com.tihonovcore.diploma.ad.site

import com.tihonovcore.diploma.ad.AdConfiguration
import com.tihonovcore.diploma.ad.AdConfiguration.siteOutputPath
import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilerConfiguration
import java.io.File

//TODO: реализована группировка по конфигурации, добавить группировку по аномалии, по файлу

//TODO: рендерить красивее

fun generateSite(map: MutableMap<CompilerConfiguration, MutableList<Anomaly>>) {
    File(siteOutputPath).deleteRecursively()

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
    val indexHtmlFile = File("$siteOutputPath/index.html")
    indexHtmlFile.parentFile.mkdirs()
    indexHtmlFile.createNewFile()

    indexHtmlFile.writeText(page)

    createAnomaliesPages(map)
}

fun createAnomaliesPages(map: MutableMap<CompilerConfiguration, MutableList<Anomaly>>) {
    /*
        TODO: так как одна аномалия может встречаться для нескольких конфигураций
         то нужна глобальная нумерация аномалий, инчае страницы будут создаваться
         с косой нумерацией
     */

    map.toList().forEachIndexed { configurationIndex, (_, anomalies) ->
        anomalies.forEachIndexed { anomalyIndex, anomaly ->
            val body = StringBuilder().apply {
                append("<a href=\"index.html\">Config2Anomalies</a>")
                append("<h2>GOT ALERT '${anomaly.alert.javaClass.simpleName}': ${anomaly.alertMessage}</h2>")
                append("<h4>WHILE COMPILING</h4> '${anomaly.compilationResults.first().file}'")

                anomaly.compilationResults.forEach { compilationResult ->
                    val onHoverStyle = """
                        style="background: #FFFFFF; padding: 10px;"
                        onmouseover="this.style.backgroundColor='#F9DC5C';" 
                        onmouseout="this.style.backgroundColor='#FFFFFF';"
                    """.trimIndent()

                    append("<div $onHoverStyle>")

                    append("<h4>COMPILER kotlinc-${compilationResult.compilerConfiguration.version}</h4>")
                    val code = compilationResult.file.readText()
                    append("<pre><code>$code</code></pre>")

                    if (compilationResult.success) {
                        append("<h4>COMPILATION SUCCESS, SPENT ${compilationResult.usedTime}ms</h4>")
                    } else {
                        append("<h4>COMPILATION FAILED</b4>")
                    }

                    append("<h4>OUTPUT</h4>")
                    append(compilationResult.output)

                    append("</div>")
                }
            }

            val page = page(body.toString())
            val anomalyFile = File("${AdConfiguration.siteOutputPath}/anomaly_${configurationIndex}_$anomalyIndex.html")
            anomalyFile.parentFile.mkdirs()
            anomalyFile.createNewFile()

            anomalyFile.writeText(page)
        }
    }
}

private fun  page(body: String): String {
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
