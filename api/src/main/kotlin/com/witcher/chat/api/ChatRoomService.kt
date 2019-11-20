package com.witcher.chat.api

import akka.NotUsed
import akka.stream.javadsl.Source
import com.lightbend.lagom.javadsl.api.Descriptor
import com.lightbend.lagom.javadsl.api.Service
import com.lightbend.lagom.javadsl.api.Service.named
import com.lightbend.lagom.javadsl.api.Service.namedCall
import com.lightbend.lagom.javadsl.api.Service.restCall
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.lightbend.lagom.javadsl.api.transport.Method
import kotlin.reflect.jvm.javaMethod

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
interface ChatRoomService : Service {

    fun getHomePage(): ServiceCall<NotUsed, String>
    fun socket(): ServiceCall<Source<InputMessageData, NotUsed>, Source<OutputMessageData, NotUsed>>

    @JvmDefault
    override fun descriptor(): Descriptor {
        return named("chat-room")
                .withCalls(
                        restCall<NotUsed, String>(Method.GET, "/home", ChatRoomService::getHomePage.javaMethod),
                        namedCall<Source<String, NotUsed>, Source<String, NotUsed>>("socket", ChatRoomService::socket.javaMethod)
                ).withAutoAcl(true)
    }
}
