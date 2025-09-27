package ken.projects.infit.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ken.projects.infit.ui.composables.splash.SplashScreen
import ken.projects.infit.ui.theme.darkBlue
import ken.projects.infit.viewmodel.OnboardingViewModel
import ken.projects.infit.viewmodel.UserViewModel
import ken.projects.infit.viewmodel.WorkoutViewModel
import kotlinx.coroutines.delay

@Composable
fun RootNavGraph(
    navController: NavHostController = rememberNavController(),
    userViewModel: UserViewModel = viewModel(),
    workoutViewModel: WorkoutViewModel = viewModel(),
    onboardingViewModel: OnboardingViewModel = viewModel()
) {

    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val scaffoldState = rememberScaffoldState()

    // SIMPLE: Just wait 2 seconds then navigate to onboarding
    LaunchedEffect(Unit) {
        delay(2000) // Show splash for 2 seconds
        navController.navigate(ONBOARDING_ROUTE) {
            popUpTo(Screens.Splash.route) { inclusive = true }
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    when (navBackStackEntry?.destination?.route) {
        Screens.Splash.route -> bottomBarState.value = false
        ONBOARDING_ROUTE -> bottomBarState.value = false
        LOGIN_ROUTE -> bottomBarState.value = false
        MAIN_ROUTE -> bottomBarState.value = true
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            when (bottomBarState.value) {
                true -> BottomNavBar(navController = navController, bottomBarState)
                false -> {}
            }
        },
        backgroundColor = darkBlue,
    ) {

        NavHost(
            navController = navController,
            startDestination = Screens.Splash.route, // Use the screen route, not the constant
            route = ROOT_ROUTE,
            modifier = Modifier.padding(it)
        ) {

            // Splash Screen - FIXED: Use the screen route directly
            composable(route = Screens.Splash.route) {
                SplashScreen()
                bottomBarState.value = false
            }

            onboardingNavGraph(
                navController = navController,
                bottomBarState = bottomBarState,
                onboardingViewModel = onboardingViewModel,
                scaffoldState = scaffoldState
            )

            loginNavGraph(
                navController = navController,
                bottomBarState = bottomBarState,
                userViewModel = userViewModel,
                scaffoldState = scaffoldState
            )

            mainNavGraph(
                navController = navController,
                bottomBarState = bottomBarState,
                userViewModel = userViewModel,
                workoutViewModel = workoutViewModel,
                scaffoldState = scaffoldState
            )

        }
    }
}