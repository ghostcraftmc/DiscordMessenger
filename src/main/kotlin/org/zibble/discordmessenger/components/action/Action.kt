package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.JsonSerializable

abstract class Action(
    open val id: Long
) : JsonSerializable {

    abstract fun getKey() : String

    abstract fun getName() : String

}