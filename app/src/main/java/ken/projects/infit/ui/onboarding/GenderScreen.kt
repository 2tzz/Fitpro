package ken.projects.infit.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GenderScreen(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val genders = listOf("Male", "Female", "Other")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Select Your Gender",
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Column {
            genders.forEach { gender ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .selectable(
                            selected = (gender == selectedGender),
                            onClick = { onGenderSelected(gender) },
                            role = Role.RadioButton
                        ),
                    backgroundColor = if (gender == selectedGender) {
                        Color(0xFF72A5F7) // Your blue color for selected
                    } else {
                        Color(0xFFF5F5F5) // Light gray for unselected
                    },
                    elevation = 4.dp
                ) {
                    Text(
                        text = gender,
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 18.sp,
                            color = if (gender == selectedGender) Color.White else Color.Black
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNext,
            enabled = selectedGender.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF72A5F7) // Your blue color
            ),
            shape = RoundedCornerShape(50) // Rounded corners
        ) {
            Text(
                "Next",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}