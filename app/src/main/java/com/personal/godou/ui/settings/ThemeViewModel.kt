package com.personal.godou.ui.settings

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personal.godou.data.preferences.AppLanguage
import com.personal.godou.data.preferences.AppFont
import com.personal.godou.data.preferences.BackdropPattern
import com.personal.godou.data.preferences.GlowIntensity
import com.personal.godou.data.preferences.TouchSynesthesia
import com.personal.godou.data.preferences.DarkThemePreference
import com.personal.godou.data.preferences.TopAppBarBackground
import com.personal.godou.data.preferences.ThemeStyle
import com.personal.godou.data.preferences.ThemeSettings
import com.personal.godou.data.preferences.DynamicTonalStyle
import com.personal.godou.data.preferences.ColorIntensityPreset
import com.personal.godou.data.preferences.UserPreferencesRepository
import com.personal.godou.data.preferences.StudyPreferences
import com.personal.godou.data.preferences.FuriganaMode
import com.personal.godou.data.preferences.SrsAlgorithm
import com.personal.godou.data.preferences.DeckOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _isAuthenticated = mutableStateOf(false)
    val isAuthenticated: State<Boolean> = _isAuthenticated

    private val _onAddActionButtonClicked = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val onAddActionButtonClicked: SharedFlow<String> = _onAddActionButtonClicked.asSharedFlow()

    fun triggerAddActionButton(route: String) {
        viewModelScope.launch {
            _onAddActionButtonClicked.emit(route)
        }
    }

    val themeSettings: StateFlow<ThemeSettings> = preferencesRepository.themeSettings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeSettings()
        )

    val studyPreferences: StateFlow<StudyPreferences> = preferencesRepository.studyPreferences
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StudyPreferences()
        )

    fun setDarkThemePreference(value: DarkThemePreference) {
        viewModelScope.launch {
            preferencesRepository.setDarkThemePreference(value)
        }
    }

    fun setUseDynamicColor(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setUseDynamicColor(enabled)
        }
    }

    fun setAppLanguage(language: AppLanguage) {
        viewModelScope.launch {
            preferencesRepository.setAppLanguage(language)
        }
    }

    fun authenticate(activity: FragmentActivity, executor: Executor) {
        val biometricManager = BiometricManager.from(activity)
        val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
        
        if (biometricManager.canAuthenticate(authenticators) == BiometricManager.BIOMETRIC_SUCCESS) {
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Unlock Godou N1")
                .setSubtitle("Authenticate to access your JLPT N1 vocabulary")
                .setAllowedAuthenticators(authenticators)
                .build()

            val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    _isAuthenticated.value = true
                }
            })

            biometricPrompt.authenticate(promptInfo)
        } else {
            _isAuthenticated.value = true
        }
    }

    fun setThemeFlavor(flavor: com.personal.godou.data.preferences.ThemeFlavor) {
        viewModelScope.launch {
            preferencesRepository.setThemeFlavor(flavor)
        }
    }

    fun setDynamicColorChromaScale(scale: Float) {
        viewModelScope.launch {
            preferencesRepository.setDynamicColorChromaScale(scale)
        }
    }

    fun setSetupComplete(completed: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setSetupComplete(completed)
        }
    }

    fun setTopAppBarBackground(background: TopAppBarBackground) {
        viewModelScope.launch {
            preferencesRepository.setTopAppBarBackground(background)
        }
    }

    fun setThemeStyle(style: ThemeStyle) {
        viewModelScope.launch {
            preferencesRepository.setThemeStyle(style)
        }
    }

    fun setDynamicTonalStyle(style: DynamicTonalStyle) {
        viewModelScope.launch {
            preferencesRepository.setDynamicTonalStyle(style)
        }
    }

    fun setIntensityPreset(preset: ColorIntensityPreset) {
        viewModelScope.launch {
            preferencesRepository.setIntensityPreset(preset)
        }
    }

    fun setAppFont(font: AppFont) {
        viewModelScope.launch {
            preferencesRepository.setAppFont(font)
        }
    }

    fun setBackdropPattern(pattern: BackdropPattern) {
        viewModelScope.launch {
            preferencesRepository.setBackdropPattern(pattern)
        }
    }

    fun setGlowIntensity(intensity: GlowIntensity) {
        viewModelScope.launch {
            preferencesRepository.setGlowIntensity(intensity)
        }
    }

    fun setTouchSynesthesia(synesthesia: TouchSynesthesia) {
        viewModelScope.launch {
            preferencesRepository.setTouchSynesthesia(synesthesia)
        }
    }

    // ── Study Preference Updaters ──
    fun setFuriganaMode(mode: FuriganaMode) {
        viewModelScope.launch {
            preferencesRepository.setFuriganaMode(mode)
        }
    }

    fun setShowRomaji(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setShowRomaji(enabled)
        }
    }

    fun setDailyGoalWords(count: Int) {
        viewModelScope.launch {
            preferencesRepository.setDailyGoalWords(count)
        }
    }

    fun setSrsAlgorithm(algo: SrsAlgorithm) {
        viewModelScope.launch {
            preferencesRepository.setSrsAlgorithm(algo)
        }
    }

    fun setDeckOrder(order: DeckOrder) {
        viewModelScope.launch {
            preferencesRepository.setDeckOrder(order)
        }
    }

    fun setDailyReminderEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setDailyReminderEnabled(enabled)
        }
    }

    fun setDailyReminderTime(time: String) {
        viewModelScope.launch {
            preferencesRepository.setDailyReminderTime(time)
        }
    }

    fun resetAllSettings() {
        viewModelScope.launch {
            preferencesRepository.resetAllSettings()
        }
    }
}
