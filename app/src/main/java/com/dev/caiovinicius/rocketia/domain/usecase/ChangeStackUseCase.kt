package com.dev.caiovinicius.rocketia.domain.usecase

import com.dev.caiovinicius.rocketia.domain.model.AIChatTextType
import com.dev.caiovinicius.rocketia.domain.repository.AIChatRepository

class ChangeStackUseCase(
    private val repository: AIChatRepository
) {

    suspend operator fun invoke(stack: String) {
        repository.changeStack(stack)
    }

}