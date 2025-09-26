package ken.projects.infit.data.models

import java.util.*

sealed class ChatMessage {
    abstract val id: String
    abstract val timestamp: Date
    abstract val content: String

    data class UserMessage(
        override val id: String,
        override val content: String,
        override val timestamp: Date = Date()
    ) : ChatMessage()

    data class AiMessage(
        override val id: String,
        override val content: String,
        override val timestamp: Date = Date()
    ) : ChatMessage()
}
