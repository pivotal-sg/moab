package org.moab.dto

import java.time.LocalDate
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.moab.command.LocalDateSerializer
import org.moab.command.LocalDateDeserializer

/**
 * Created by neo on 20/4/16.
 */

data class Account(val accountNumber: String,
                   val clientName: String,
                   val clientID: String,
                   @get:JsonSerialize(using = LocalDateSerializer::class)
                   val clientDoB: LocalDate)


