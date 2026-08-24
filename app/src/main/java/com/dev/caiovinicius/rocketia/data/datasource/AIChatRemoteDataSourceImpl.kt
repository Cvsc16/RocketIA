package com.dev.caiovinicius.rocketia.data.datasource

import com.dev.caiovinicius.rocketia.data.remote.api.AIAPIService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AIChatRemoteDataSourceImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val aiAPIService: AIAPIService
) : AIChatRemoteDataSource {

    override suspend fun sendPrompt(stack: String, question: String): String? =
        withContext(ioDispatcher) {
            aiAPIService.sendPrompt(stack, question)
        }

}