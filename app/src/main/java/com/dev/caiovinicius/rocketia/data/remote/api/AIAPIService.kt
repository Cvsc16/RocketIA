package com.dev.caiovinicius.rocketia.data.remote.api

interface AIAPIService {

    suspend fun sendPrompt(stack: String, question: String) : String?

}