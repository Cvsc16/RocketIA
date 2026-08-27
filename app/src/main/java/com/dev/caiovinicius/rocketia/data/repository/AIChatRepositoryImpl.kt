package com.dev.caiovinicius.rocketia.data.repository

import com.dev.caiovinicius.rocketia.data.datasource.AIChatLocalDataSource
import com.dev.caiovinicius.rocketia.data.datasource.AIChatRemoteDataSource
import com.dev.caiovinicius.rocketia.data.local.database.AIChatTextEntity
import com.dev.caiovinicius.rocketia.data.mapper.toDomain
import com.dev.caiovinicius.rocketia.domain.model.AIChatText
import com.dev.caiovinicius.rocketia.domain.model.AIChatTextType
import com.dev.caiovinicius.rocketia.domain.repository.AIChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class AIChatRepositoryImpl(
    private val aiChatRemoteDataSource: AIChatRemoteDataSource,
    private val aiChatLocalDataSource: AIChatLocalDataSource
) : AIChatRepository {

    override val selectedStack: Flow<String?>
        get() = aiChatLocalDataSource.selectedStack

    override val aiChatBySelectedStack: Flow<List<AIChatText>>
        get() = aiChatLocalDataSource.aiCurrentChatBySelectedStack.map { currentChatEntity ->
            currentChatEntity.toDomain()
        }

    override suspend fun sendUserQuestion(question: String) {
        val currentStack = aiChatLocalDataSource.selectedStack.firstOrNull().orEmpty()
        val answer = aiChatRemoteDataSource.sendPrompt(currentStack, question)

        answer?.let {
            aiChatLocalDataSource.insertAIChatConversation(
                question = createUserQuestionEntity(question, currentStack),
                answer = createAIAnswerEntity(answer, currentStack)
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

}
