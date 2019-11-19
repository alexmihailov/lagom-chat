package com.witcher.chat.api

import akka.NotUsed
import akka.stream.javadsl.Source
import com.lightbend.lagom.javadsl.api.Descriptor
import com.lightbend.lagom.javadsl.api.Service
import com.lightbend.lagom.javadsl.api.Service.named
import com.lightbend.lagom.javadsl.api.Service.namedCall
import com.lightbend.lagom.javadsl.api.ServiceCall
import kotlin.reflect.jvm.javaMethod

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
interface ChatRoomService : Service {

    fun helloWorld(): ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>>

    @JvmDefault
    override fun descriptor(): Descriptor {
        return named("chat-room")
                .withCalls(
                        namedCall<Source<String, NotUsed>, Source<String, NotUsed>>("hello-world", ChatRoomService::helloWorld.javaMethod)
                ).withAutoAcl(true)
    }
}
