package com.witcher.chat.api

import java.time.ZonedDateTime

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
data class OutputMessageData(val time: ZonedDateTime, val from: String, val messageText: String)
