package com.witcher.chat.impl.domain.actors

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props
import java.time.ZonedDateTime.now
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
class UserSession private constructor(private val room: ActorRef) : AbstractActor() {

    private val log: Logger = LoggerFactory.getLogger(UserSession::class.java)
    private var userName: String? = null

    companion object {
        fun probs(room: ActorRef): Props = Props.create(UserSession::class.java) { UserSession(room) }
    }

    override fun createReceive(): Receive = receiveBuilder()
            .match(InputMessage::class.java) { processInputMessage(it) }
            .match(InviteAccepted::class.java) { processInviteAccepted(it) }
            .match(InviteDenied::class.java) { processInviteDenied(it) }
            .match(ChatMessage::class.java) { precessChatMessage(it) }
            .match(Broadcast::class.java) { processBroadcast(it) }
            .matchAny { log.warn("Unhandled message: $it") }
            .build()

    override fun preStart() {
        super.preStart()
        log.debug("${self.path().name()} started.")
    }

    override fun postStop() {
        super.postStop()
        log.debug("${self.path().name()} stopped.")
        if (userName != null) room.tell(Unsubscribe(userName!!), self)
    }

    private fun processInputMessage(msg: InputMessage) {
        log.debug("processInputMessage, msg=$msg")
        when (msg.messageType) {
            MessageType.SET_NAME -> requestNewName(msg.messageText)
            MessageType.SEND_MESSAGE -> sendMsgToRoom(msg.messageText)
        }
    }

    private fun processInviteAccepted(msg: InviteAccepted) {
        log.debug("processInviteAccepted, msg=$msg")
        setName(msg.name)
    }

    private fun processInviteDenied(msg: InviteDenied) {
        log.debug("processInviteDenied, msg=$msg")
        replyToClient(msg = "taken", from = "system")
    }

    private fun precessChatMessage(msg: ChatMessage) {
        log.debug("precessChatMessage, msg=$msg")
        replyToClient(msg = msg.text, from = msg.from)
    }

    private fun processBroadcast(msg: Broadcast) {
        log.debug("processBroadcast, msg=$msg")
        replyToClient(msg = msg.text)
    }

    private fun requestNewName(name: String) {
        if (name.isNotBlank()) room.tell(InviteRequest(name), self)
    }

    private fun sendMsgToRoom(msg: String) {
        if (userName != null) room.tell(ChatMessage(userName!!, msg), self)
    }

    private fun replyToClient(msg: String, from: String? = null) {
        // TODO sender - не тот акторо, которому нужнго отправить сообщение!
        sender.tell(OutputMessage(time = now(), from = from ?: "toAll", messageText = msg), self)
    }

    private fun setName(name: String) {
        userName = name
        replyToClient(msg = "welcome", from = "system")
    }
}
