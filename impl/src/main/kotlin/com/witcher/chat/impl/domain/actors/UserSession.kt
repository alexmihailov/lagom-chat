package com.witcher.chat.impl.domain.actors

import akka.actor.AbstractActor
import akka.actor.ActorLogging
import akka.actor.ActorRef

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
class UserSession private constructor(private val out: ActorRef, private val room: ActorRef) : AbstractActor(), ActorLogging {

    override fun createReceive(): Receive {
        TODO("not implemented") // To change body of created functions use File | Settings | File Templates.
    }
}
