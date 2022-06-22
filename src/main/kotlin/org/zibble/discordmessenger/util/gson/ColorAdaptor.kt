package org.zibble.discordmessenger.util.gson

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.awt.Color

class ColorAdaptor : TypeAdapter<Color>() {

    override fun write(out: JsonWriter, value: Color) {
        out.value(Integer.toHexString(value.rgb))
    }

    override fun read(`in`: JsonReader): Color {
        return Color(Integer.parseUnsignedInt(`in`.nextString(), 16))
    }

}