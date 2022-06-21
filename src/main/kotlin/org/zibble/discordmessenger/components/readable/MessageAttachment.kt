package org.zibble.discordmessenger.components.readable

import org.zibble.discordmessenger.components.JsonSerializable
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class MessageAttachment(
    val name: String,
    val data: ByteArray
) : JsonSerializable {

    constructor(name: String, stream: InputStream) : this(name, stream.readAllBytes()) {
        stream.close()
    }

    constructor(name: String, file: File) : this(name, FileInputStream(file)) {}

}