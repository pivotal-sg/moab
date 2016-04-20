package org.moab.command

import java.time.LocalDate

/**
 * Created by neo on 20/4/16.
 *
 */
data class AccountCreateCommand(var clientName: String, var clientID: String, var clientDoB: LocalDate ) : MOABCommand {
    val version: Int
        get() = 0

    val name: String
        get() = "createEvent"
}
