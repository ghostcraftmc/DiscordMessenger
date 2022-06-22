package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.JsonSerializable

abstract class Action : JsonSerializable {

    abstract fun getKey() : String

    abstract fun getName() : String

}