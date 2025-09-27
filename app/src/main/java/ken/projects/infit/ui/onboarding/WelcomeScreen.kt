package ken.projects.infit.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ken.projects.infit.ui.theme.holoGreen
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun WelcomeScreen(
    onNext: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // This spacer pushes content down from top
        Spacer(modifier = Modifier.height(50.dp)) // Adjust this value

        Text(
            text = "Welcome to Fitpro",
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Your personal fitness companion that helps you track workouts, monitor progress, and achieve your fitness goals with AI-powered insights.",
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            textAlign = TextAlign.Center
        )

        // This spacer pushes button to bottom
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp), // Adjust height as needed
            colors = ButtonDefaults.buttonColors(
                backgroundColor = holoGreen
            ),
            shape = RoundedCornerShape(50) // This makes it fully rounded
        ) {
            Text(
                "Get Started",
                color = Color.White,
                fontSize = 18.sp, // Larger text
                fontWeight = FontWeight.Bold
            )
        }
    }
}