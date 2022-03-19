package com.tihonovcore.diploma.ad.alert

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilationResult

/*
TODO:
 вообще Fixed[Memory, Time]Alert'ы - штука странная
 надо добавить алерт на память/время пропорциональное размеру АСТ

TODO:
 нужны алерты со сравнением аутпутов для одного файла на разных компиляторах

TODO:
 поддержать EvaluationResult и алерты на сравнение результатов выполнения - время память и аутпут
*/
interface Alert {
    fun check(compilationResults: List<CompilationResult>): List<Anomaly>
}
