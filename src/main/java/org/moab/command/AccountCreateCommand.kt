package org.moab.command

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.time.LocalDate
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.io.IOException
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.format.DateTimeFormatter

data class AccountCreateCommand (
        var clientName: String,
        var clientID: String,
        @get:JsonSerialize(using = LocalDateSerializer::class)
        @set:JsonDeserialize(using = LocalDateDeserializer::class)
        var clientDoB: LocalDate) : MOABCommand {

    val name: String
        @JsonIgnore get() = "createEvent"
}

class LocalDateSerializer: JsonSerializer<LocalDate>() {

  @Throws(IOException::class, JsonProcessingException::class)
  override fun serialize(date:LocalDate, jsonGen:JsonGenerator, provider:SerializerProvider) {
      val dateString =  date.format(DateTimeFormatter.ISO_LOCAL_DATE)
      jsonGen.writeString(dateString)
  }
}

class LocalDateDeserializer: JsonDeserializer<LocalDate>() {

  @Throws(IOException::class, JsonProcessingException::class)
  override fun deserialize(parser: JsonParser, context:DeserializationContext):LocalDate {
    return LocalDate.parse(parser.getText())
  }
}
