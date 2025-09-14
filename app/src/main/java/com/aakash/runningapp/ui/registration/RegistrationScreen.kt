package com.aakash.runningapp.ui.registration

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aakash.runningapp.R
import com.aakash.runningapp.ui.appcomponent.common.NameInput
import com.aakash.runningapp.ui.appcomponent.snackbar.SnackEvent
import com.aakash.runningapp.ui.appcomponent.snackbar.SnackbarManager
import com.aakash.runningapp.ui.navigation.AppScreen
import com.aakash.runningapp.ui.registration.ui.AlbumComponent
import com.aakash.runningapp.ui.theme.LocalDimens
import com.aakash.runningapp.ui.theme.appTypography
import com.aakash.runningapp.ui.theme.backgroundDark
import com.aakash.runningapp.ui.theme.borderColor
import com.aakash.runningapp.ui.theme.inputBackground
import com.aakash.runningapp.ui.theme.onPrimary
import com.aakash.runningapp.ui.theme.primaryGreen
import com.aakash.runningapp.util.UiEvent
import com.aakash.runningapp.util.extension.PastOrPresentSelectableDates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar


@Composable
fun RegistrationScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
) {
    val registrationViewModel: RegistrationViewModel = viewModel()

    val registrationState by registrationViewModel.registrationState.collectAsStateWithLifecycle()
    val validationState by registrationViewModel.validationErrorState.collectAsStateWithLifecycle()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    LaunchedEffect(registrationState.isRegistrationSuccessful) {
        if (registrationState.isRegistrationSuccessful) {
            onNavigate(UiEvent.Navigate(AppScreen.Main.Dashboard.route))
        }
    }

    LaunchedEffect(registrationState.registrationErrorState.serverErrorState) {
        if (registrationState.registrationErrorState.serverErrorState.hasError) {
            SnackbarManager.sendEvent(
                event = SnackEvent(
                    message = registrationState.registrationErrorState.serverErrorState.serverErrorMsg
                )
            )
        }
    }

    LaunchedEffect(key1 = registrationState.registrationErrorState.serverErrorState.hasError) {
        if (registrationState.registrationErrorState.serverErrorState.serverErrorMsg.isNotEmpty()) {
            coroutineScope.launch {
                SnackbarManager.sendEvent(
                    SnackEvent(registrationState.registrationErrorState.serverErrorState.serverErrorMsg)
                )
            }
        }
    }

    RegistrationContent(
        firstName = registrationState.firstName,
        lastName = registrationState.lastName,
        dob = registrationState.dob,
        profilePicture = registrationState.profilePicture,
        validationErrorState = validationState,
        onFirstNameChanged = {
            registrationViewModel.handleRegistrationUiEvent(RegistrationUiEvent.FirstNameChanged(it))
        },
        onLastNameChanged = {
            registrationViewModel.handleRegistrationUiEvent(RegistrationUiEvent.LastNameChanged(it))
        },
        onDobChanged = { year, month, day ->
            registrationViewModel.handleRegistrationUiEvent(
                RegistrationUiEvent.DobChanged(
                    year,
                    month,
                    day
                )
            )
        },

        onProfilePictureChanged = {
            registrationViewModel.handleRegistrationUiEvent(
                RegistrationUiEvent.ProfilePictureChanged(
                    it
                )
            )
        },
        onRegisterClick = {
            registrationViewModel.handleRegistrationUiEvent(RegistrationUiEvent.OnSubmit)
        }
    )

}

@Composable
fun RegistrationContent(
    firstName: String,
    lastName: String,
    dob: String,
    profilePicture: String,
    validationErrorState: RegistrationFieldValidationState,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onDobChanged: (Int, Int, Int) -> Unit,
    onProfilePictureChanged: (String) -> Unit,
    onRegisterClick: () -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.ime),
        color = backgroundDark
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = backgroundDark,
            bottomBar = {
                ButtonComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = LocalDimens.current.paddingExtraLarge,
                            vertical = LocalDimens.current.paddingLarge // Padding for the button itself
                        ),
                    onRegisterClick = onRegisterClick
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = LocalDimens.current.paddingExtraLarge,
                        end = LocalDimens.current.paddingExtraLarge,
                        top = LocalDimens.current.paddingExtraLarge,
                        bottom = paddingValues.calculateBottomPadding()
                    )
            ) {
                TopBarComponent()

                AccountTextField(
                    label = stringResource(R.string.first_name),
                    validationErrorState = validationErrorState,
                    onValueChange = onFirstNameChanged,
                    currentValue = firstName
                )
                AccountTextField(
                    label = stringResource(R.string.last_name),
                    validationErrorState = validationErrorState,
                    onValueChange = onLastNameChanged,
                    currentValue = lastName
                )
                AccountTextField(
                    label = stringResource(R.string.dob),
                    validationErrorState = validationErrorState,
                    isDateField = true,
                    currentValue = dob,
                    onDobChange = { year, month, day ->
                        onDobChanged(year, month, day)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.profile_picture),
                    style = appTypography.bodyMedium.copy(color = onPrimary)
                )

                Spacer(modifier = Modifier.height(8.dp))

                AlbumComponent(
                    viewModel = viewModel(),
                )

                Spacer(modifier = Modifier.height(LocalDimens.current.paddingExtraLarge))
            }
        }
    }

}


@Composable
fun TopBarComponent() {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = LocalDimens.current.paddingExtraLarge)
            .fillMaxWidth()
    ) {

        Text(
            text = "Create Account",
            textAlign = TextAlign.Center,
            style = appTypography.titleLarge.copy(
                color = onPrimary,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTextField(
    modifier: Modifier = Modifier,
    label: String,
    isDateField: Boolean = false,
    currentValue: String,
    onValueChange: (String) -> Unit = {},
    onDobChange: (Int, Int, Int) -> Unit = { _, _, _ -> },
    validationErrorState: RegistrationFieldValidationState,
) {
    val focusManager: FocusManager = LocalFocusManager.current
    val lastNameFocusRequester = remember { FocusRequester() }
    val dobFocusRequester = remember { FocusRequester() }

    val context = LocalContext.current
    var dobText by remember { mutableStateOf(currentValue) }
    val calendar = remember { Calendar.getInstance() }

    var showDatePicker by remember { mutableStateOf(false) }

    val dateState = rememberDatePickerState(
        selectableDates = PastOrPresentSelectableDates
    )

    if (showDatePicker) {
        DisposableEffect(Unit) {
            val dialog = DatePickerDialog(

                context,
                { _, year, month, day ->
                    val selectedDate = "%04d-%02d-%02d".format(year, month + 1, day)
                    dobText = selectedDate // update local text
                    onValueChange(selectedDate) // notify parent
                    onDobChange(year, month, day)
                    showDatePicker = false

                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            dialog.datePicker.maxDate = dateState.selectedDateMillis ?: System.currentTimeMillis()
            dialog.setOnDismissListener { showDatePicker = false }
            dialog.show()

            onDispose {
                dialog.dismiss()
            }
        }
    }

    Column(modifier = modifier.padding(vertical = LocalDimens.current.paddingRegular)) {
        Text(
            text = label,
            style = appTypography.bodyMedium.copy(color = onPrimary)
        )
        Spacer(modifier = Modifier.height(4.dp))
        when (label) {
            stringResource(R.string.first_name) -> {
                NameInput(
                    currentValue = currentValue,
                    label = stringResource(R.string.first_name),
                    isError = validationErrorState.firstNameValidationStatus,
                    errorText = if (validationErrorState.firstNameValidationStatus) stringResource(
                        validationErrorState.errorMessageState.firstNameErrorMessage.message
                    ) else "",
                    placeholderText = stringResource(R.string.first_name_placeholder),
                    onValueChange = onValueChange,
                    keyboardActions = KeyboardActions(onNext = { lastNameFocusRequester.requestFocus() })
                )
            }

            stringResource(R.string.last_name) -> {
                NameInput(
                    label = stringResource(R.string.last_name),
                    isError = validationErrorState.lastNameValidationStatus,
                    errorText = if (validationErrorState.lastNameValidationStatus) stringResource(
                        validationErrorState.errorMessageState.lastNameErrorMessage.message
                    ) else "",
                    placeholderText = stringResource(R.string.last_name_placeholder),
                    onValueChange = onValueChange,
                    currentValue = currentValue,
                    keyboardActions = KeyboardActions(onNext = { dobFocusRequester.requestFocus() })
                )
            }

            stringResource(R.string.dob) -> {
                val dobLabel = stringResource(R.string.dob)
                val dobPlaceholder = stringResource(R.string.dob_placeholder)
                val dobError = if (validationErrorState.dobValidationStatus)
                    stringResource(validationErrorState.errorMessageState.dobErrorMessage.message)
                else ""
                NameInput(
                    onDateClick = {
                        showDatePicker = true
                    },
                    label = dobLabel,
                    isError = validationErrorState.dobValidationStatus,
                    errorText = dobError,
                    placeholderText = dobPlaceholder,
                    onValueChange = onValueChange,
                    isDateField = isDateField,
                    currentValue = dobText,
                    keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
                )
            }
        }
    }
}

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit,
) {
    Button(
        onClick = { onRegisterClick() },
        colors = ButtonDefaults.buttonColors(containerColor = primaryGreen),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp)
    ) {
        Text(
            text = "Register",
            style = appTypography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = onPrimary
            )
        )
    }
}

@Composable
fun DottedUploadBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .border(
                BorderStroke(1.dp, borderColor),
                shape = RoundedCornerShape(12.dp)
            )
            .background(inputBackground, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(R.drawable.ic_upload),
                contentDescription = "Upload",
                tint = onPrimary,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(LocalDimens.current.paddingRegular))
            Text(
                text = "Click to upload",
                color = onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
