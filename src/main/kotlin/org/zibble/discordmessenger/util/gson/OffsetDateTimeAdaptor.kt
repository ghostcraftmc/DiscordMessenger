package org.zibble.discordmessenger.util.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.time.OffsetDateTime

class OffsetDateTimeAdaptor : TypeAdapter<OffsetDateTime>() {

    override fun write(out: JsonWriter, value: OffsetDateTime) {
        out.value(value.toString())
    }

    override fun read(`in`: JsonReader): OffsetDateTime {
        return OffsetDateTime.parse(`in`.nextString())
    }

}