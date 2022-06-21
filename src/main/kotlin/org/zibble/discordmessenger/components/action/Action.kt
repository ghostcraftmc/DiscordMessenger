package org.zibble.discordmessenger.components.action

import org.zibble.discordmessenger.components.JsonSerializable

abstract class Action : JsonSerializable {

    companion object {
        val actionTypes = HashMap<String, Class<out Action>>()
    }

    init {
        actionTypes[getKey()] = this.javaClass
    }

    abstract fun getKey() : String

    abstract fun getName() : String

}