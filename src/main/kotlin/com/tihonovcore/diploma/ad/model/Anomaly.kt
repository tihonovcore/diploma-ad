package com.tihonovcore.diploma.ad.model

import com.tihonovcore.diploma.ad.alert.Alert

data class Anomaly(
    val compilationResult: CompilationResult,
    val message: String,
    val alert: Alert
) {
    override fun toString(): String {
        return "${alert.javaClass.name}: $message"
    }
}
