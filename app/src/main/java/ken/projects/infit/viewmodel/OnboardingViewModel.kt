package ken.projects.infit.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ken.projects.infit.data.models.OnboardingData
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    var onboardingData by mutableStateOf(OnboardingData())
        private set

    var currentScreen by mutableStateOf(0)
        private set

    var isOnboardingComplete by mutableStateOf(false)
        private set

    fun setGender(gender: String) {
        onboardingData = onboardingData.copy(gender = gender)
    }

    fun setHeight(height: Int) {
        onboardingData = onboardingData.copy(height = height)
        calculateBMI()
    }

    fun setWeight(weight: Int) {
        onboardingData = onboardingData.copy(weight = weight)
        calculateBMI()
    }

    private fun calculateBMI() {
        val data = onboardingData
        if (data.height > 0 && data.weight > 0) {
            val heightInMeters = data.height / 100.0
            val bmi = data.weight / (heightInMeters * heightInMeters)
            onboardingData = data.copy(bmi = bmi)
        }
    }

    fun nextScreen() {
        currentScreen += 1
    }

    fun previousScreen() {
        if (currentScreen > 0) {
            currentScreen -= 1
        }
    }

    fun completeOnboarding() {
        isOnboardingComplete = true
        // Here you can save the data to SharedPreferences or database
        // You might want to integrate with your UserRepository later
    }
}