package com.witcher.chat.impl.api

import akka.NotUsed
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.stream.javadsl.Source
import com.google.inject.Inject
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.lightbend.lagom.javadsl.api.transport.MessageProtocol
import com.lightbend.lagom.javadsl.api.transport.ResponseHeader
import com.lightbend.lagom.javadsl.server.HeaderServiceCall
import com.witcher.chat.api.ChatRoomService
import com.witcher.chat.api.InputMessageData
import com.witcher.chat.api.OutputMessageData
import com.witcher.chat.impl.domain.actors.OutputMessage
import com.witcher.chat.impl.domain.actors.Room
import com.witcher.chat.impl.domain.actors.UserSession
import com.witcher.chat.impl.util.ask
import java.time.Duration
import java.util.Optional

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
class ChatRoomServiceImpl @Inject constructor(private val system: ActorSystem) : ChatRoomService {

    private val timeout: Duration = Duration.ofSeconds(5)
    private val roomActor: ActorRef = system.actorOf(Room.probs())

    override fun getHomePage(): HeaderServiceCall<NotUsed, String> = headerServiceCall { _, _ ->
        val page = ChatRoomServiceImpl::class.java.getResource("/chat.html").readText(Charsets.UTF_8)
        akka.japi.Pair(
                ResponseHeader.OK.withProtocol(MessageProtocol.fromContentTypeHeader(Optional.of("text/html"))),
                page
        )
    }

    override fun socket(): ServiceCall<Source<InputMessageData, NotUsed>, Source<OutputMessageData, NotUsed>> = serviceCall { request ->
        val sessionActor = system.actorOf(UserSession.probs(roomActor))
        request.mapAsync(8) { msg ->
            ask<OutputMessage>(sessionActor, msg.toInputMessage(), timeout)
                    .thenApply { it.toOutputMessageData() }
        }
    }
}
