package com.ismail.openapi.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ismail.openapi.api.auth.network_responses.LoginResponse
import com.ismail.openapi.api.auth.network_responses.RegistrationResponse
import com.ismail.openapi.models.AuthToken
import com.ismail.openapi.repository.auth.AuthRepository
import com.ismail.openapi.ui.AbsentLiveData
import com.ismail.openapi.ui.BaseViewModel
import com.ismail.openapi.ui.DataState
import com.ismail.openapi.ui.auth.state.AuthStateEvent
import com.ismail.openapi.ui.auth.state.AuthStateEvent.*
import com.ismail.openapi.ui.auth.state.AuthViewState
import com.ismail.openapi.ui.auth.state.LoginFields
import com.ismail.openapi.ui.auth.state.RegistrationFields
import com.ismail.openapi.util.GenericApiResponse
import javax.inject.Inject

class AuthViewModel
@Inject
constructor(
    val authRepository: AuthRepository
): BaseViewModel<AuthStateEvent, AuthViewState>()
{
    override fun handleStateEvent(stateEvent: AuthStateEvent): LiveData<DataState<AuthViewState>> {
        when(stateEvent){
            is LoginAttemptEvent -> {
                return authRepository.attemptLogin(
                    stateEvent.email,
                    stateEvent.password
                )
            }

            is RegisterAttemptEvent -> {
                return authRepository.attemptRegistration(
                    stateEvent.email,
                    stateEvent.username,
                    stateEvent.password,
                    stateEvent.confirmpassword
                )
            }

            is CheckPreviousAuthEvent -> {
                return AbsentLiveData.create()
            }
        }
    }

    fun setRegistrationFields(registrationFields: RegistrationFields){
        val update = getCurrentViewStateOrNew()
        if (update.registrationFields == registrationFields){
            return
        }
        update.registrationFields = registrationFields
        _viewState.value = update
    }

    fun setLoginFields(loginFields: LoginFields){
        val update = getCurrentViewStateOrNew()
        if (update.loginFields == loginFields){
            return
        }
        update.loginFields = loginFields
        _viewState.value = update
    }

    fun setAuthToken(authToken: AuthToken){
        val update = getCurrentViewStateOrNew()
        if (update.authToken == authToken){
            return
        }
        update.authToken = authToken
        _viewState.value = update
    }

    override fun initNewViewState(): AuthViewState {
        return AuthViewState()
    }
}