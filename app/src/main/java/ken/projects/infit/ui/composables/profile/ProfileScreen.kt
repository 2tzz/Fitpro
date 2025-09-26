// ui/composables/profile/ProfileScreen.kt
package ken.projects.infit.ui.composables.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.LaunchedEffect
import ken.projects.infit.data.models.ChatMessage
import ken.projects.infit.ui.theme.*
import ken.projects.infit.viewmodel.ChatViewModel

@Composable
fun ProfileScreen(
    chatViewModel: ChatViewModel = hiltViewModel()
) {

    val chatMessages by chatViewModel.chatMessages.collectAsState()
    val isLoading by chatViewModel.isLoading.collectAsState()
    val lazyListState = rememberLazyListState()

    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            lazyListState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = darkBlue) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "AI Fitness Assistant",
                color = white,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = outfit,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Chat Messages
            ChatMessagesList(
                messages = chatMessages,
                isLoading = isLoading,
                listState = lazyListState,
                modifier = Modifier.weight(1f)
            )

            // Message Input
            MessageInput(
                onSendMessage = { message ->
                    chatViewModel.sendMessage(message)
                },
                isLoading = isLoading,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun ChatMessagesList(
    messages: List<ChatMessage>,
    isLoading: Boolean,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(veryDarkBlue)
            .padding(8.dp)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            reverseLayout = false
        ) {
            items(messages) { message ->
                when (message) {
                    is ChatMessage.UserMessage -> UserMessageItem(message)
                    is ChatMessage.AiMessage -> AiMessageItem(message)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (isLoading) {
                item {
                    AiTypingIndicator()
                }
            }
        }
    }
}

@Composable
fun UserMessageItem(message: ChatMessage.UserMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Card(
            backgroundColor = holoGreen,
            modifier = Modifier
                .padding(start = 48.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Text(
                text = message.content,
                color = Color.Black,
                modifier = Modifier.padding(12.dp),
                fontFamily = outfit
            )
        }
    }
}

@Composable
fun AiMessageItem(message: ChatMessage.AiMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Card(
            backgroundColor = white,
            modifier = Modifier
                .padding(end = 48.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Text(
                text = message.content,
                color = Color.Black,
                modifier = Modifier.padding(12.dp),
                fontFamily = outfit
            )
        }
    }
}

@Composable
fun AiTypingIndicator() {
    Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(16.dp),
            strokeWidth = 2.dp,
            color = holoGreen
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "AI is thinking...",
            color = white,
            fontFamily = outfit,
            fontSize = 14.sp
        )
    }
}

@Composable
fun MessageInput(
    onSendMessage: (String) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    var messageText by remember { mutableStateOf("") }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = messageText,
            onValueChange = { messageText = it },
            placeholder = {
                Text(
                    text = "Ask about workouts, nutrition, or fitness...",
                    color = white.copy(alpha = 0.7f),
                    fontFamily = outfit
                )
            },
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(24.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = veryDarkBlue,
                textColor = white,
                cursorColor = holoGreen,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            enabled = !isLoading,
            singleLine = true
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Fix the IconButton - remove .clip(CircleShape) from the modifier chain
        IconButton(
            onClick = {
                if (messageText.isNotBlank() && !isLoading) {
                    onSendMessage(messageText)
                    messageText = ""
                }
            },
            enabled = messageText.isNotBlank() && !isLoading,
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = if (messageText.isNotBlank() && !isLoading) holoGreen else holoGreen.copy(alpha = 0.5f),
                    shape = CircleShape // Add shape here instead
                )
                .clip(CircleShape) // This should work now
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Send message",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
