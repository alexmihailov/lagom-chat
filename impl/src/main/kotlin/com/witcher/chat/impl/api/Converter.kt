package com.witcher.chat.impl.api

import com.witcher.chat.api.InputMessageData
import com.witcher.chat.api.OutputMessageData
import com.witcher.chat.impl.domain.actors.InputMessage
import com.witcher.chat.impl.domain.actors.MessageType
import com.witcher.chat.impl.domain.actors.OutputMessage

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

fun InputMessageData.toInputMessage() = InputMessage(messageType = MessageType.fromCode(this.messageType), messageText = this.messageText)

fun OutputMessage.toOutputMessageData() = OutputMessageData(time = this.time, from = this.from, messageText = this.messageText)
