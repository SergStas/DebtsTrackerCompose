@file:OptIn(ExperimentalMaterialApi::class)

package composables.screens.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import composables.screens.tabmenu.TabMenuScreen
import composables.theme.AppTextField
import composables.theme.AppTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import res.StringResources
import util.static.getVm

class AuthScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel = getVm(::AuthViewModel)
        val navigator = LocalNavigator.current
        subscribeOnAuthEvent(viewModel, navigator)
        viewModel.checkAuth()
        val mode = viewModel.mode.collectAsState()
        val error = viewModel.validationResult.collectAsState()
        var username by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var passwordRepeat by rememberSaveable { mutableStateOf("") }
        Box(Modifier.fillMaxSize().padding(AppTheme.Sizes.windowPadding.dp)) {
            Card(Modifier.fillMaxHeight()) {
                Column(Modifier.padding(
                    horizontal = AppTheme.Sizes.paddingLarge.dp,
                    vertical = AppTheme.Sizes.paddingNormal.dp,
                )) {
                    Text(
                        text = StringResources.get().run {
                            when(mode.value) {
                                AuthViewModel.Mode.Login -> authTitleLogin
                                AuthViewModel.Mode.Registration -> authTitleRegister
                            }
                        },
                        textAlign = TextAlign.Center,
                        style = AppTheme.fonts().h2.copy(fontSize = 32.sp),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ClickableText(
                        style = AppTheme.fonts().h5.copy(textAlign = TextAlign.Center),
                        modifier = Modifier.fillMaxWidth().padding(top = AppTheme.Sizes.paddingLarge.dp),
                        text = getRedirectAnnotatedString(mode),
                        onClick = { viewModel.switchMode() },
                    )
                    Text(
                        text = StringResources.get().authLabelUsername,
                        style = AppTheme.fonts().h2,
                        modifier = Modifier.fillMaxWidth().padding(top = AppTheme.Sizes.paddingLarge.dp),
                        textAlign = TextAlign.Center,
                    )
                    AppTextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier.fillMaxWidth().padding(top = AppTheme.Sizes.paddingNormal.dp),
                    )
                    Text(
                        text = StringResources.get().authLabelPassword,
                        style = AppTheme.fonts().h2,
                        modifier = Modifier.fillMaxWidth().padding(top = AppTheme.Sizes.paddingLarge.dp),
                        textAlign = TextAlign.Center,
                    )
                    AppTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth().padding(top = AppTheme.Sizes.paddingNormal.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password,
                        ),
                    )
                    if (mode.value == AuthViewModel.Mode.Registration) {
                        Text(
                            text = StringResources.get().authLabelPasswordRepeat,
                            style = AppTheme.fonts().h2,
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = AppTheme.Sizes.paddingLarge.dp),
                            textAlign = TextAlign.Center,
                        )
                        AppTextField(
                            value = passwordRepeat,
                            onValueChange = { passwordRepeat = it },
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = AppTheme.Sizes.paddingNormal.dp),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                autoCorrect = false,
                                keyboardType = KeyboardType.Password,
                            ),
                        )
                    }
                    if (error.value.isError) {
                        Text(
                            modifier = Modifier
                                .padding(top = AppTheme.Sizes.paddingLarge.dp)
                                .align(Alignment.CenterHorizontally),
                            text = StringResources.get().run {
                                when(error.value) {
                                    AuthViewModel.Validation.None -> ""
                                    AuthViewModel.Validation.Success -> ""
                                    AuthViewModel.Validation.UsernameIsEmpty -> authErrorUsernameEmpty
                                    AuthViewModel.Validation.PasswordIsEmpty -> authErrorPasswordEmpty
                                    AuthViewModel.Validation.UsernameTooShort -> authErrorUsernameTooShort
                                    AuthViewModel.Validation.UsernameOccupied -> authErrorUsernameOccupied
                                    AuthViewModel.Validation.PasswordTooShort -> authErrorPasswordTooShort
                                    AuthViewModel.Validation.InvalidCredentials -> authErrorInvalidCredentials
                                    AuthViewModel.Validation.PasswordsNotMatch -> authErrorPasswordsNotMatch
                                    AuthViewModel.Validation.UnknownError -> authErrorUnknownError
                                }
                            },
                            style = AppTheme.fonts().h3.copy(color = AppTheme.ColorCodes.c6),
                        )
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth().padding(top = AppTheme.Sizes.paddingLarge.dp),
                        onClick = { viewModel.submit(username, password, passwordRepeat) },
                    ) {
                        Text(
                            text = StringResources.get().run {
                                when(mode.value) {
                                    AuthViewModel.Mode.Login -> authButtonLogin
                                    AuthViewModel.Mode.Registration -> authButtonRegister
                                }
                            },
                            style = AppTheme.fonts().button,
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun getRedirectAnnotatedString(
        mode: State<AuthViewModel.Mode>,
    ) =
        buildAnnotatedString {
            append(StringResources.get().run {
                when (mode.value) {
                    AuthViewModel.Mode.Login -> authTextRedirectToRegisterPrefix
                    AuthViewModel.Mode.Registration -> authTextRedirectToLoginPrefix
                }
            })
            withStyle(style = SpanStyle(color = AppTheme.colors().primary)) {
                append(StringResources.get().run {
                    when (mode.value) {
                        AuthViewModel.Mode.Login -> authTextRedirectToRegisterPostfix
                        AuthViewModel.Mode.Registration -> authTextRedirectToLoginPostfix
                    }
                })
            }
        }

    private fun subscribeOnAuthEvent(
        viewModel: AuthViewModel,
        navigator: Navigator?
    ) = MainScope().launch {
        viewModel.authed.collect {
            if (it) {
                navigator?.push(TabMenuScreen())
            }
        }
    }
}