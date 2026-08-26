package com.dev.caiovinicius.rocketia.data.repository

import com.dev.caiovinicius.rocketia.data.datasource.AIChatLocalDataSource
import com.dev.caiovinicius.rocketia.data.datasource.AIChatRemoteDataSource
import com.dev.caiovinicius.rocketia.data.local.database.AIChatTextEntity
import com.dev.caiovinicius.rocketia.data.mapper.toDomain
import com.dev.caiovinicius.rocketia.domain.model.AIChatText
import com.dev.caiovinicius.rocketia.domain.model.AIChatTextType
import com.dev.caiovinicius.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AIChatRepositoryImpl(
    private val aiChatRemoteDataSource: AIChatRemoteDataSource,
    private val aiChatLocalDataSource: AIChatLocalDataSource
) : AIChatRepository {

    override val selectedStack: Flow<String>
        get() = aiChatLocalDataSource.selectedStack

    override val firstLaunch: Flow<Boolean>
        get() = aiChatLocalDataSource.firstLaunch

    override val aiChatBySelectedStack: Flow<List<AIChatText>>
        get() = aiChatLocalDataSource.aiCurrentChatBySelectedStack.map { currentChatEntity ->
            currentChatEntity.toDomain()
        }

    override suspend fun sendUserQuestion(question: String, stack: String) {
        val answer = aiChatRemoteDataSource.sendPrompt(stack, question)

        answer?.let {
            aiChatLocalDataSource.insertAIChatConversation(
                question = createUserQuestionEntity(question, stack),
                answer = createAIAnswerEntity(answer, stack)
            )
        }
    }

    private fun createUserQuestionEntity(question: String, stack: String): AIChatTextEntity =
        AIChatTextEntity(
            stack = stack,
            from = AIChatTextType.USER_QUESTION.name,
            text = question,
            datetime = System.currentTimeMillis()
        )

    private fun createAIAnswerEntity(answer: String, stack: String): AIChatTextEntity =
        AIChatTextEntity(
            stack = stack,
            from = AIChatTextType.AI_ANSWER.name,
            text = answer,
            datetime = System.currentTimeMillis()
        )

    override suspend fun changeStack(stack: String) {
        aiChatLocalDataSource.changeSelectedStack(stack)
    }

    override suspend fun changeFirstLaunch() {
        aiChatLocalDataSource.changeFirstLaunch()
    }
}
