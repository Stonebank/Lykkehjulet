package com.dtu.s205409.utility

enum class GameWord(val word: String) {

    PLACEHOLDER(""),
    BEGIVENHED("pinseguds-tjeneste"),
    STED("jammerbugt"),
    PERSON("politimand"),
    TING("hjulpisker"),
    LAND("danmark"),
    TITEL("dagens donna");


    fun getCategory(): String {
        return name
    }

}