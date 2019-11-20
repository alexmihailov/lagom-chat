package com.witcher.chat.impl.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

/**
 * @author Alex Mihailov {@literal <avmikhaylov@phoenixit.ru>}.
 */
class JacksonCustomSetupModule : SimpleModule() {

    override fun setupModule(context: SetupContext) {
        super.setupModule(context)
        val owner = context.getOwner<ObjectMapper>()
        // Allows to write timezone (not TimeZoneId)
        owner.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        // Forces to write TimeZoneId
        owner.disable(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)
        // Disables converting to UTC timezone at deserialization
        owner.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)

        owner.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)

        // This doesn't seem to be required to serialize our data class, but it is required to deserialize
        owner.registerModule(KotlinModule())
    }
}
