package ken.projects.infit.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
fun HeightWeightScreen(
    height: Int,
    weight: Int,
    bmi: Double,
    onHeightChanged: (Int) -> Unit,
    onWeightChanged: (Int) -> Unit,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Your Body Metrics",
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Height and Weight Pickers side by side
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Height Picker
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Height (cm)", style = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                ))
                NumberPicker(
                    value = height,
                    onValueChange = onHeightChanged,
                    range = 100..250,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Weight Picker
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Weight (kg)", style = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                ))
                NumberPicker(
                    value = weight,
                    onValueChange = onWeightChanged,
                    range = 30..200,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // BMI Display
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xFFF5F5F5),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Your BMI", style = androidx.compose.ui.text.TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                ))
                Text(
                    text = "%.1f".format(bmi),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 32.sp,
                        color = Color(0xFF72A5F7),
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = getBMICategory(bmi),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onNext,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF72A5F7)
            ),
            shape = RoundedCornerShape(50)
        ) {
            Text(
                "Continue to Login",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun getBMICategory(bmi: Double): String {
    return when {
        bmi < 18.5 -> "Underweight"
        bmi < 25 -> "Normal weight"
        bmi < 30 -> "Overweight"
        else -> "Obese"
    }
}