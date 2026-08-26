package com.dev.caiovinicius.rocketia.data.mapper

import com.dev.caiovinicius.rocketia.data.local.database.AIChatTextEntity
import com.dev.caiovinicius.rocketia.domain.model.AIChatText
import com.dev.caiovinicius.rocketia.domain.model.AIChatTextType

fun AIChatTextEntity.toDomain(): AIChatText =
    when (this.from) {
        AIChatTextType.USER_QUESTION.name -> AIChatText.UserQuestion(this.text)
        AIChatTextType.AI_ANSWER.name -> AIChatText.AIAnswer(this.text)
        else -> throw IllegalArgumentException("Invalid from value")
    }

fun List<AIChatTextEntity>.toDomain(): List<AIChatText> =
    this.map { entity -> entity.toDomain() }