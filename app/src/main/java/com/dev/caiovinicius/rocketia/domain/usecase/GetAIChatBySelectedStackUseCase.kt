package com.dev.caiovinicius.rocketia.domain.usecase

import com.dev.caiovinicius.rocketia.domain.model.AIChatText
import com.dev.caiovinicius.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow

class GetAIChatBySelectedStackUseCase(
    private val repository: AIChatRepository
) {

    operator fun invoke(): Flow<List<AIChatText>> = repository.aiChatBySelectedStack

}