package com.dev.caiovinicius.rocketia.domain.usecase

import com.dev.caiovinicius.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow

class GetSelectedStackUseCase(
    private val repository: AIChatRepository
) {

    operator fun invoke(): Flow<String?> = repository.selectedStack

}