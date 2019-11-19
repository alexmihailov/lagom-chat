package com.witcher.chat.impl

import com.google.inject.AbstractModule
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport
import com.witcher.chat.api.ChatRoomService

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
class ChatRoomModule : AbstractModule(), ServiceGuiceSupport {

    override fun configure() {
        bindService(ChatRoomService::class.java, ChatRoomServiceImpl::class.java)
    }
}
