package com.tihonovcore.diploma.ad.alert

import com.tihonovcore.diploma.ad.model.Anomaly
import com.tihonovcore.diploma.ad.model.CompilationResult
import java.io.File

/*
TODO:
 вообще Fixed[Memory, Time]Alert'ы - штука странная
 надо добавить алерт на память/время пропорциональное размеру АСТ

TODO:
 нужны алерты со сравнением аутпутов для одного файла на разных компиляторах

TODO:
 сейчас можно делать два вида алертов: "один файл, один компилятор" и
 "один файл несколько компиляторов"
 Есть смысл в проверки нескольких файлов на одном/нескольких компиляторах? (Кажется, нет)

TODO:
 Для алертов вида "один файл несколько компиляторов" используются сразу все компиляторы.
 было бы разумно иметь возможность в алерте указывать какие компиляторы можно использовать,
 а какие нужно исключить

TODO:
 поддержать EvaluationResult и алерты на сравнение результатов выполнения - время память и аутпут
*/
interface Alert {
    fun check(file: File, compilationResults: List<CompilationResult>): List<Anomaly>
}
