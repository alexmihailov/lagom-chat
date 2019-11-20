package com.witcher.chat.impl.util

import akka.actor.ActorRef
import akka.pattern.Patterns
import java.time.Duration
import java.util.concurrent.CompletionStage

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */

@Suppress("UNCHECKED_CAST")
fun <REPLY> ask(actor: ActorRef, msg: Any, timeout: Duration): CompletionStage<REPLY> =
        Patterns.ask(actor, msg, timeout) as CompletionStage<REPLY>
