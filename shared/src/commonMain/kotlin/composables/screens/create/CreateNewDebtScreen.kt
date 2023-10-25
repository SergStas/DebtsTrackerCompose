@file:OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)

package composables.screens.create

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Snackbar
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import cafe.adriel.voyager.core.screen.Screen
import composables.screens.create.models.DebtDirection
import composables.theme.AppTheme
import io.ktor.util.date.GMTDate
import res.StringResources
import util.composables.datepicker.DateTextField
import util.extensions.format
import util.static.getVm
import kotlin.math.absoluteValue

class CreateNewDebtScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel = getVm(::CreateNewDebtViewModel)
        viewModel.loadLists()
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.Sizes.windowPadding.dp)
                    .align(Alignment.TopCenter),
            ) {
                SwitchCard(viewModel)
                DebtMainInfo(viewModel)
                DescriptionCard(viewModel)
                DatePickerCard(viewModel)
                FinishButton(viewModel)
                ProgressBar(
                    viewModel = viewModel,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
            SnackbarMessage(
                viewModel,
                Modifier.align(Alignment.BottomCenter),
            )
        }
    }

    @Composable
    private fun ProgressBar(viewModel: CreateNewDebtViewModel, modifier: Modifier) {
        val visible = viewModel.loading.collectAsState()
        if (visible.value) {
            CircularProgressIndicator(
                modifier.then(
                    other = Modifier.padding(AppTheme.Sizes.paddingLarge.dp)
                )
            )
        }
    }

    @Composable
    private fun SnackbarMessage(viewModel: CreateNewDebtViewModel, modifier: Modifier) {
        val error = viewModel.validationResult.collectAsState()
        val hasError = error.value !in listOf(
            CreateNewDebtViewModel.ValidationResult.Waiting,
            CreateNewDebtViewModel.ValidationResult.Success,
        )
        if (hasError) {
            Snackbar(modifier.then(Modifier.padding(AppTheme.Sizes.paddingLarge.dp))) {
                Box(Modifier.fillMaxWidth()) {
                    Text(
                        text = error.value.name,
                        style = AppTheme.fonts().h5,
                        color = AppTheme.ColorCodes.c3,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }
    }

    @Composable
    private fun FinishButton(viewModel: CreateNewDebtViewModel) {
        Button(
            onClick = { viewModel.submit() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.Sizes.paddingLarge.dp),
        ) {
            Text(
                text = StringResources.get().newDebtButtonDone.uppercase(),
                style = AppTheme.fonts().h4,
                color = AppTheme.colors().onPrimary,
                modifier = Modifier.padding(
                    vertical = AppTheme.Sizes.paddingSmaller.dp,
                ),
            )
        }
    }

    @Composable
    private fun DatePickerCard(viewModel: CreateNewDebtViewModel) {
        val dateTextField = mutableStateOf(TextFieldValue(
            GMTDate(viewModel.expirationDate.value).run {
                if (viewModel.expirationDate.value == 0L) ""
                else "$dayOfMonth-${month.ordinal + 1}-$year"
            }
        ))
        val hasDate = viewModel.hasExpirationDate.collectAsState()
        val keyboardController = LocalSoftwareKeyboardController.current
        Card(
            modifier = Modifier
                .padding(top = AppTheme.Sizes.paddingLarge.dp)
                .fillMaxWidth(),
        ) {
            Column(Modifier.padding(AppTheme.Sizes.paddingNormal.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = hasDate.value,
                        onCheckedChange = { viewModel.enableExpirationDate(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = AppTheme.colors().primary,
                        ),
                    )
                    Text(
                        text = StringResources.get().newDebtLabelDate,
                        style = AppTheme.fonts().h5,
                        modifier = Modifier.padding(start = AppTheme.Sizes.paddingExtraSmall.dp),
                    )
                }
                if (hasDate.value) {
                    DateTextField(
                        value = dateTextField.value,
                        onValueChange = {
                            dateTextField.value = it
                            viewModel.updateDate(it.text)
                        },
                        placeholder = {
                            Text(
                                text = StringResources.get().newDebtDatePh,
                                color = AppTheme.ColorCodes.c8,
                                style = AppTheme.fonts().h5,
                            )
                        },
                        shape = RoundedCornerShape(AppTheme.Sizes.paddingSmaller),
                        onDone = { keyboardController?.hide() },
                        modifier = AppTheme.Sizes.paddingNormal.dp.let {
                            Modifier.padding(end = it, start = it, bottom = it)
                        },
                    )
                }
            }
        }
    }

    @Composable
    private fun DescriptionCard(viewModel: CreateNewDebtViewModel) {
        val isChecked = viewModel.descEnabled.collectAsState()
        val desc = viewModel.desc.collectAsState()
        val keyboardController = LocalSoftwareKeyboardController.current
        Card(
            modifier = Modifier
                .padding(top = AppTheme.Sizes.paddingLarge.dp)
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(AppTheme.Sizes.paddingNormal.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = isChecked.value,
                        onCheckedChange = { viewModel.enableDesc(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = AppTheme.colors().primary,
                        ),
                    )
                    Text(
                        text = StringResources.get().newDebtLabelDesc,
                        style = AppTheme.fonts().h5,
                        modifier = Modifier.padding(start = AppTheme.Sizes.paddingExtraSmall.dp),
                    )
                }
                if (isChecked.value) {
                    TextField(
                        modifier = AppTheme.Sizes.paddingNormal.dp.let {
                            Modifier
                                .padding(start = it, end = it, bottom = it)
                                .fillMaxWidth()
                        },
                        value = desc.value,
                        onValueChange = { viewModel.updateDesc(it) },
                        maxLines = 8,
                        textStyle = AppTheme.fonts().h5,
                        placeholder = {
                            Text(
                                text = StringResources.get().newDebtDescPh,
                                color = AppTheme.ColorCodes.c8,
                                style = AppTheme.fonts().h5,
                            )
                        },
                        shape = RoundedCornerShape(AppTheme.Sizes.paddingSmaller),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            autoCorrect = false,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { keyboardController?.hide() },
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun DebtMainInfo(viewModel: CreateNewDebtViewModel) {
        Card(Modifier.padding(top = AppTheme.Sizes.paddingLarge.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(AppTheme.Sizes.paddingLarger.dp),
            ) {
                ClientPicker(viewModel)
                SumTextField(viewModel)
            }
        }
    }

    @Composable
    private fun SumTextField(viewModel: CreateNewDebtViewModel) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val currency = viewModel.currency.collectAsState()
        val currencyList = viewModel.currencies.collectAsState()
        var dropdownExpanded by remember { mutableStateOf(false) }
        var sum by remember { mutableStateOf("") }
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(top = AppTheme.Sizes.paddingNormal.dp),
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = StringResources.get().newDebtSumLabel,
                style = AppTheme.fonts().h5,
            )
            TextField(
                modifier = Modifier
                    .padding(start = AppTheme.Sizes.paddingNormal.dp)
                    .fillMaxWidth(.7f),
                value = sum.let {
                    if ((it.toDoubleOrNull()?.absoluteValue ?: 0.0) < 1e-3) ""
                    else it
                },
                onValueChange = {
                    sum = it
                    sum.toDoubleOrNull()?.let(viewModel::updateSum)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    autoCorrect = false,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        sum = viewModel.sum.value.format(2)
                    },
                ),
                placeholder = {
                    Text(
                        text = 0.0.toString(),
                        color = AppTheme.ColorCodes.c8,
                    )
                },
                shape = RoundedCornerShape(AppTheme.Sizes.paddingSmaller),
                maxLines = 1,
            )
            Box(
                modifier = Modifier.align(Alignment.CenterVertically)
                    .padding(AppTheme.Sizes.paddingNormal.dp),
            ) {
                ClickableText(
                    text = AnnotatedString(
                        (currency.value ?: currencyList.value.firstOrNull())
                            ?.name?.uppercase() ?: "",
                    ),
                    style = AppTheme.fonts().h5,
                    onClick = { dropdownExpanded = true },
                )
                DropdownMenu(
                    modifier = Modifier.wrapContentWidth()
                        .padding(horizontal = AppTheme.Sizes.paddingNormal.dp),
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                    properties = PopupProperties()
                ) {
                    viewModel.currencies.collectAsState().value.forEach {
                        DropdownMenuItem(
                            onClick = {
                                dropdownExpanded = false
                                viewModel.updateCurrency(it)
                            },
                        ) {
                            Text(it.name.uppercase(),
                                modifier = Modifier.wrapContentWidth()
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ClientPicker(viewModel: CreateNewDebtViewModel) {
        val client = viewModel.client.collectAsState()
        var dropdownExpanded by remember { mutableStateOf(false) }
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = StringResources.get().newDebtClientLabel,
                style = AppTheme.fonts().h5,
            )
            Box(Modifier.padding(start = AppTheme.Sizes.paddingNormal.dp)) {
                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = AppTheme.ColorCodes.run {
                            if (client.value == null) c7 else c2
                        })) {
                            append(
                                text = client.value?.username ?: StringResources.get().newDebtClientDefaultValue
                            )
                        }
                    },
                    onHover = {},
                    style = AppTheme.fonts().h5,
                ) { dropdownExpanded = true }
                DropdownMenu(
                    modifier = Modifier.wrapContentWidth()
                        .padding(horizontal = AppTheme.Sizes.paddingNormal.dp),
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false },
                ) {
                    viewModel.friendsList.collectAsState().value.forEach {
                        DropdownMenuItem(
                            onClick = {
                                dropdownExpanded = false
                                viewModel.updateClient(it)
                            },
                        ) {
                            Text(it.username,
                                modifier = Modifier.wrapContentWidth()
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SwitchCard(viewModel: CreateNewDebtViewModel) {
        val mode = viewModel.debtDirection.collectAsState()
        Card(Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(AppTheme.Sizes.paddingSmall.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Switch(
                    checked = mode.value == DebtDirection.ToPay,
                    onCheckedChange = {
                        viewModel.switchDirection()
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = AppTheme.Sizes.paddingNormal.dp),
                    colors = object : SwitchColors {
                        @Composable
                        override fun thumbColor(enabled: Boolean, checked: Boolean) =
                            AppTheme.ColorCodes.run { mutableStateOf(c7) }

                        @Composable
                        override fun trackColor(enabled: Boolean, checked: Boolean) =
                            AppTheme.ColorCodes.run { mutableStateOf(c9) }
                    }
                )
                Text(
                    text = StringResources.get().run {
                        when (mode.value) {
                            DebtDirection.ToPay -> newDebtDirectionLabelOutgoing
                            DebtDirection.ToReceive -> newDebtDirectionLabelIncoming
                        }
                    },
                    modifier = Modifier.padding(start = AppTheme.Sizes.paddingNormal.dp),
                )
            }
        }
    }
}