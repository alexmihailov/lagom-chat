package com.witcher.chat.impl.domain.actors

import akka.actor.AbstractActor
import akka.actor.ActorLogging
import akka.actor.ActorRef

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
class Room private constructor() : AbstractActor(), ActorLogging {

    private val sessions: MutableMap<String, ActorRef> = mutableMapOf()

    override fun createReceive(): Receive = receiveBuilder()
            .match(InviteRequest::class.java) { tryRegisterUser(it) }
            .match(ChatMessage::class.java) { sendAll(it) }
            .match(Unsubscribe::class.java) { unsubscribe(it) }
            .matchAny { log().warning("Unhandled message: $it") }
            .build()

    private fun tryRegisterUser(msg: InviteRequest) {
        log().debug("tryRegisterUser, msg=$msg")
        if (sessions.containsKey(msg.name)) {
            sender().tell(InviteDenied, self())
        } else {
            sessions[msg.name] = sender()
            sender().tell(InviteAccepted(msg.name), self())
            sendAll(Broadcast("User ${msg.name} joined."))
        }
    }

    private fun unsubscribe(msg: Unsubscribe) {
        log().debug("unsubscribe, msg=$msg")
        sessions.remove(msg.name)
        sendAll(Broadcast("${msg.name} has left."))
    }

    private fun sendAll(msg: Any) = sessions.values.forEach { it.tell(msg, self()) }
}
