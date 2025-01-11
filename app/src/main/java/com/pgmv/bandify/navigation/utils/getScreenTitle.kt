package com.pgmv.bandify.navigation.utils

fun getScreenTitle(route: String): String {
    return when (route) {
        "home" -> "Bandify"
        "agenda" -> "Agenda"
        "repertório" -> "Repertório"
        "arquivos" -> "Arquivos"
        "perfil" -> "Perfil"
        "novo_evento" -> "Adicionar Evento"
        else -> "Bandify"
    }
}