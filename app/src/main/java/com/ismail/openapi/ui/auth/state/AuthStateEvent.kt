package com.ismail.openapi.ui.auth.state

sealed class AuthStateEvent {

    data class LoginAttemptEvent(
        val email: String,
        val password: String
    ): AuthStateEvent()

    data class RegisterAttemptEvent(
        val email: String,
        val username: String,
        val password: String,
        val confirmpassword: String
    ): AuthStateEvent()

    class CheckPreviousAuthEvent: AuthStateEvent()
}