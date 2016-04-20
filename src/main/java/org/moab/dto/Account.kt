package org.moab.dto

import java.time.LocalDate

/**
 * Created by neo on 20/4/16.
 */

data class Account(val accountNumber: String,
                   val clientName: String,
                   val clientID: String,
                   val clientDoB: LocalDate)

