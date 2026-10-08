package com.dev.caiovinicius.rocketia.ui.event

sealed interface WelcomeUiEvent {
    object CheckHasSelectedStack : WelcomeUiEvent
}