package ken.projects.infit.ui.onboarding

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import ken.projects.infit.viewmodel.OnboardingViewModel

@Composable
fun OnboardingScreen(
    onComplete: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = viewModel.isOnboardingComplete) {
        if (viewModel.isOnboardingComplete) {
            onComplete()
        }
    }

    when (viewModel.currentScreen) {
        0 -> WelcomeScreen(
            onNext = { viewModel.nextScreen() }
        )
        1 -> GenderScreen(
            selectedGender = viewModel.onboardingData.gender,
            onGenderSelected = { viewModel.setGender(it) },
            onNext = { viewModel.nextScreen() },
            onBack = { viewModel.previousScreen() }
        )
        2 -> HeightWeightScreen(
            height = viewModel.onboardingData.height,
            weight = viewModel.onboardingData.weight,
            bmi = viewModel.onboardingData.bmi,
            onHeightChanged = { viewModel.setHeight(it) },
            onWeightChanged = { viewModel.setWeight(it) },
            onNext = {
                viewModel.completeOnboarding()
            },
            onBack = { viewModel.previousScreen() }
        )
    }
}