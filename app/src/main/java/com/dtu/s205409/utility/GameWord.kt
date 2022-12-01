package com.dtu.s205409.utility

import java.util.*

enum class GameWord(var word: String) {

    PLACEHOLDER(""),
    BEGIVENHED("pinseguds-tjeneste"),
    STED("jammerbugt"),
    PERSON("politimand"),
    TING("hjulpisker"),
    TITEL("dagens donna"),
    LAND("danmark");

    fun getCategory(): String {
        return name.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    }

}