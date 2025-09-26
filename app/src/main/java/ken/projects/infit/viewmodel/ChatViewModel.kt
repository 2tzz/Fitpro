package ken.projects.infit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ken.projects.infit.data.models.ChatMessage
import ken.projects.infit.data.service.GeminiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val geminiService: GeminiService
) : ViewModel() {

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun sendMessage(message: String) {
        if (message.isBlank()) return

        val userMessage = ChatMessage.UserMessage(
            id = UUID.randomUUID().toString(),  // Generate UUID here
            content = message
        )

        // Add user message to chat
        _chatMessages.value = _chatMessages.value + userMessage
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = geminiService.sendMessage(message)

                val aiMessage = ChatMessage.AiMessage(
                    id = UUID.randomUUID().toString(),
                    content = response
                )

                // Add AI response to chat
                _chatMessages.value = _chatMessages.value + aiMessage
            } catch (e: Exception) {
                val errorMessage = ChatMessage.AiMessage(
                    id = UUID.randomUUID().toString(),
                    content = "Sorry, I encountered an error: ${e.message}"
                )
                _chatMessages.value = _chatMessages.value + errorMessage
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearChat() {
        _chatMessages.value = emptyList()
    }
}