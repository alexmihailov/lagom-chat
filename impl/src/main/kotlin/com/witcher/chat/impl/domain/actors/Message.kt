package com.witcher.chat.impl.domain.actors

import java.time.ZonedDateTime

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

sealed class Message
data class InputMessage(val messageType: MessageType, val messageText: String) : Message()
data class OutputMessage(val time: ZonedDateTime, val from: String, val messageText: String) : Message()
data class InviteRequest(val name: String) : Message()
object InviteDenied : Message()
data class InviteAccepted(val name: String) : Message()
data class Unsubscribe(val name: String) : Message()
data class Broadcast(val text: String) : Message()
data class ChatMessage(val from: String, val text: String) : Message()
