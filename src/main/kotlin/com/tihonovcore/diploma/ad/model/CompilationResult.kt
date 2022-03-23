package com.tihonovcore.diploma.ad.model

import java.io.File

/*
TODO:
 доставать из компилятора размер AST
 важно, что он может варьироваться в зависимости от
 версии компилятора, поэтому кажется логичным получать его при каждой компиляции

TODO:
 а что вообще такое usedMemory? максимальная занятая память? а что если 10% времени
 она была равна максимальной, а 90% в разы ниже? кажется, что использовать только
 такой показатель не очень надежно
 идея:
 использовать usedMemory: List<Int> - набор отметок занимаемой памяти в разные моменты
 времени, по ней при необходимости можно получить и максимальную используемую память
 */
data class CompilationResult(
    val compilerConfiguration: CompilerConfiguration,
    val file: File,
    val success: Boolean,
    val usedTime: Long,
    val usedMemory: Long,

    val output: String,
    val errorOutput: String
)
