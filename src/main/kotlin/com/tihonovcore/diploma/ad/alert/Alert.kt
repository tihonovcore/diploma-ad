package com.tihonovcore.diploma.ad.alert

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilationResult

/*
TODO:
 вообще Fixed[Memory, Time]Alert'ы - штука странная
 надо добавить алерт на память/время пропорциональное размеру АСТ

TODO:
 нужны алерты со сравнением аутпутов
*/
interface Alert {
    fun check(compilationResults: List<CompilationResult>): List<Anomaly>
}
