package ken.projects.infit.ui.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ken.projects.infit.ui.onboarding.GenderScreen
import ken.projects.infit.ui.onboarding.HeightWeightScreen
import ken.projects.infit.ui.onboarding.WelcomeScreen
import ken.projects.infit.viewmodel.OnboardingViewModel

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    onboardingViewModel: OnboardingViewModel,
    scaffoldState: ScaffoldState
) {
    navigation(
        startDestination = Screens.OnboardingWelcome.route,
        route = ONBOARDING_ROUTE
    ) {
        composable(route = Screens.OnboardingWelcome.route) {
            WelcomeScreen(
                onNext = {
                    navController.navigate(Screens.OnboardingGender.route)
                }
            )
            bottomBarState.value = false
        }

        composable(route = Screens.OnboardingGender.route) {
            GenderScreen(
                selectedGender = onboardingViewModel.onboardingData.gender,
                onGenderSelected = { onboardingViewModel.setGender(it) },
                onNext = {
                    navController.navigate(Screens.OnboardingHeightWeight.route)
                },
                onBack = { navController.popBackStack() }
            )
            bottomBarState.value = false
        }

        composable(route = Screens.OnboardingHeightWeight.route) {
            HeightWeightScreen(
                height = onboardingViewModel.onboardingData.height,
                weight = onboardingViewModel.onboardingData.weight,
                bmi = onboardingViewModel.onboardingData.bmi,
                onHeightChanged = { onboardingViewModel.setHeight(it) },
                onWeightChanged = { onboardingViewModel.setWeight(it) },
                onNext = {
                    onboardingViewModel.completeOnboarding()
                    // Navigate to login after onboarding
                    navController.navigate(LOGIN_ROUTE) {
                        popUpTo(ONBOARDING_ROUTE) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
            bottomBarState.value = false
        }
    }
}