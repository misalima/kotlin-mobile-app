package com.pgmv.bandify.navigation.utils

fun getScreenTitle(route: String): String {
    return when (route) {
        "home" -> "Bandify"
        "agenda" -> "Agenda"
        "repertorio" -> "Repertório"
        "arquivos" -> "Arquivos"
        "perfil" -> "Perfil"
        "novo_evento" -> "Adicionar Evento"
        "nova_musica" -> "Adicionar Música"
        else -> "Bandify"
    }
}