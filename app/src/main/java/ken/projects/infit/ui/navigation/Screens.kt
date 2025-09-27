package ken.projects.infit.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

const val ROOT_ROUTE = "root_route"
const val LOGIN_ROUTE = "login_route"
const val MAIN_ROUTE = "main_route"
const val ONBOARDING_ROUTE = "onboarding_route"
const val SPLASH_ROUTE = "splash_route"

sealed class Screens(
    val route: String,
    val title: String, // Changed from Int to String
    val icon: ImageVector? = null
) {
    // Add onboarding screens with string literals
    object Splash : Screens(route = "splash_screen", title = "Splash")
    object OnboardingWelcome : Screens(route = "onboarding_welcome", title = "Welcome")
    object OnboardingGender : Screens(route = "onboarding_gender", title = "Select Gender")
    object OnboardingHeightWeight : Screens(route = "onboarding_height_weight", title = "Body Metrics")

    // Your existing screens - update to string literals
    object Home : Screens(route = "home_screen", title = "Home", icon = Icons.Rounded.Home)
    object Login : Screens(route = "login_screen", title = "Login")
    object Signup : Screens(route = "signup_screen", title = "Signup")
    object Workout : Screens(route = "workout_screen", title = "Workout")
    object Stats : Screens(route = "stats_screen", title = "Stats", icon = Icons.Rounded.Analytics)
    object WorkoutDetails : Screens(route = "workout_details_screen", title = "Workout Details")
    object StatsDetails : Screens(route = "stats_details_screen", title = "Stats Details")
    object Profile : Screens(route = "profile_screen", title = "Profile", icon = Icons.Rounded.Chat)
    object Exercises : Screens(route = "exercises_screen", title = "Exercises", icon = Icons.Rounded.FitnessCenter)
    object ExerciseDetails : Screens(route = "exercises_details_screen", title = "Exercise Details")
    object WorkoutPlanSetUp : Screens(route = "workout_plan_setup_screen", title = "Set Up Workout Plan")
}