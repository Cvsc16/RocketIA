package com.dev.caiovinicius.rocketia.domain.usecase

import com.dev.caiovinicius.rocketia.domain.repository.AIChatRepository

class SendUserQuestionUseCase(
    private val repository: AIChatRepository
) {

    suspend operator fun invoke(question: String) {
        repository.sendUserQuestion(
            question
        )
    }

}