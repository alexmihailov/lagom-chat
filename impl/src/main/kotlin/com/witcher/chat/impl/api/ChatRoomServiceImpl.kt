package com.witcher.chat.impl.api

import akka.NotUsed
import akka.stream.javadsl.Source
import com.lightbend.lagom.javadsl.api.ServiceCall
import com.witcher.chat.api.ChatRoomService
import java.util.concurrent.CompletableFuture.completedFuture

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
class ChatRoomServiceImpl : ChatRoomService {

    override fun helloWorld(): ServiceCall<Source<String, NotUsed>, Source<String, NotUsed>> = serviceCall { hellos ->
        hellos.mapAsync(8) { name -> completedFuture("Hello, $name") }
    }
}
