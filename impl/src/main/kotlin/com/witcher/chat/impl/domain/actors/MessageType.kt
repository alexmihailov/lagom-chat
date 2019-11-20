package com.witcher.chat.impl.domain.actors

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
enum class MessageType(val code: Int) {
    SET_NAME(0), SEND_MESSAGE(1), UNKNOWN(-1);

    companion object {
        fun fromCode(code: Int) = values().find { it.code == code } ?: UNKNOWN
    }
}
