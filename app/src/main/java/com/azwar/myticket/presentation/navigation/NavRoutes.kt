package com.azwar.myticket.presentation.navigation

object NavRoutes {
    const val HOME = "home"
    const val NEW_TICKET = "new_ticket"
    const val DETAIL = "detail/{ticketId}"
    const val SETTINGS = "settings"

    fun detail(ticketId: Long): String = "detail/$ticketId"
}

