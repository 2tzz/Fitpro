package ken.projects.infit.data.service

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject



class GeminiService @Inject constructor() {

    private val generativeModel by lazy {
        val apiKey = "##########"
        GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = apiKey
        )
    }

    // Add system prompt for fitness coaching
    private val fitnessCoachPrompt = """
        You are FitPro AI, an expert fitness and nutrition coach. Your role is to provide:
        
        **Fitness Guidance:**
        - Personalized workout plans based on user goals (weight loss, muscle gain, endurance)
        - Exercise form corrections and alternatives
        - Progressive overload principles
        - Recovery and rest day advice
        
        **Nutrition Advice:**
        - Healthy meal planning and recipes
        - Macronutrient guidance (proteins, carbs, fats)
        - Calorie recommendations for goals
        - Hydration and supplement advice
        
        **General Wellness:**
        - Motivation and mindset strategies
        - Injury prevention tips
        - Sleep optimization
        - Stress management techniques
        
        Always be encouraging, professional, and provide evidence-based recommendations.
        Ask clarifying questions if you need more information about the user's goals, fitness level, or limitations.
    """.trimIndent()

    suspend fun sendMessage(userMessage: String): String {
        return try {
            withContext(Dispatchers.IO) {
                // Combine system prompt with user message
                val fullPrompt = "$fitnessCoachPrompt\n\nUser: $userMessage"
                generativeModel.generateContent(fullPrompt).text ?: "Sorry, I couldn't process your request."
            }
        } catch (e: Exception) {
            "Error: ${e.message ?: "Unknown error occurred"}"
        }
    }
}