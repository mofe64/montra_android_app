package com.nubari.montra.auth.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nubari.montra.auth.events.AuthEvent
import com.nubari.montra.auth.events.AuthProcessEvent
import com.nubari.montra.auth.events.AuthUIEvent
import com.nubari.montra.auth.state.AuthState
import com.nubari.montra.data.models.UserDetails
import com.nubari.montra.data.remote.requests.LoginRequest
import com.nubari.montra.data.remote.requests.RegistrationRequest
import com.nubari.montra.domain.usecases.authUsecases.AuthenticationUseCases
import com.nubari.montra.general.util.Resource
import com.nubari.montra.preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authenticationUseCases: AuthenticationUseCases
) : ViewModel() {
    private val _state = mutableStateOf(
        AuthState(
            isAuthenticated = preferences.authenticationToken != ""
        )
    )
    val state: State<AuthState> = _state

    fun createEvent(event: AuthEvent) {
        onEvent(event = event)
    }

    private val _eventFlow = MutableSharedFlow<AuthEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthUIEvent.Register -> {
                val request = RegistrationRequest(
                    name = event.name,
                    email = event.email,
                    password = event.password
                )
                register(request = request)

            }
            is AuthUIEvent.Login -> {
                val request = LoginRequest(
                    email = event.email,
                    password = event.password
                )
                login(request = request)
            }
            is AuthUIEvent.VerifyEmail -> {
                verifyEmail(id = event.id, token = event.verificationCode)
            }
            else -> {}
        }
    }

    private fun verifyEmail(id: String, token: String) {
        authenticationUseCases.verifyEmailUseCase(id, token)
            .onEach {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isProcessing = true,
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isProcessing = false,
                            errorOccurred = true,
                            errorMessage = it.message
                                ?: "An unexpected error occurred, Please try again later"
                        )
                        _eventFlow.emit(
                            AuthProcessEvent.FailedVerification(errorMessage = state.value.errorMessage)
                        )
                    }

                    is Resource.Success -> {
                        val userDetails = UserDetails(
                            name = it.data?.user?.name ?: "",
                            email = it.data?.user?.email ?: "",
                            id = it.data?.user?.id ?: "",
                            token = it.data?.token ?: ""
                        )
                        _state.value = state.value.copy(
                            isProcessing = false,
                            userDetails = userDetails
                        )
                        _eventFlow.emit(
                            AuthProcessEvent.SuccessfulVerification
                        )
                    }
                }
            }
    }


    private fun register(request: RegistrationRequest) {
        authenticationUseCases.registerUseCase(request = request)
            .onEach {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isProcessing = true,
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isProcessing = false,
                            errorOccurred = true,
                            errorMessage = it.message
                                ?: "An unexpected error occurred, Please try again later"
                        )
                        _eventFlow.emit(
                            AuthProcessEvent.FailedRegistration(errorMessage = state.value.errorMessage)
                        )
                    }
                    is Resource.Success -> {
                        val userDetails = UserDetails(
                            name = it.data?.user?.name ?: "",
                            email = it.data?.user?.email ?: "",
                            id = it.data?.user?.id ?: ""
                        )
                        _state.value = state.value.copy(
                            isProcessing = false,
                            userDetails = userDetails
                        )
                        _eventFlow.emit(
                            AuthProcessEvent.SuccessfulRegistration
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun login(request: LoginRequest) {
        authenticationUseCases.loginUseCase(request = request)
            .onEach {
                when (it) {
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isProcessing = true,
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isProcessing = false,
                            errorOccurred = true,
                            errorMessage = it.message
                                ?: "An unexpected error occurred, Please try again later"
                        )
                        _eventFlow.emit(
                            AuthProcessEvent.FailedLogin(errorMessage = state.value.errorMessage)
                        )
                    }
                    is Resource.Success -> {
                        val userDetails = UserDetails(
                            name = it.data?.user?.name ?: "",
                            email = it.data?.user?.email ?: "",
                            id = it.data?.user?.id ?: "",
                            token = it.data?.token ?: ""
                        )
                        _state.value = state.value.copy(
                            isProcessing = false,
                            userDetails = userDetails,
                            isAuthenticated = true
                        )
                        preferences.authenticationToken = userDetails.token
                        preferences.userId = userDetails.id
                        _eventFlow.emit(
                            AuthProcessEvent.SuccessfulLogin
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}