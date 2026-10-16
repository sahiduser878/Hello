package com.jann.ai.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jann.ai.core.ui.theme.JannColors
import kotlinx.coroutines.delay
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private data class QuickAction(val label: String, val icon: ImageVector, val phase: Int)

private val quickActions = listOf(
    QuickAction("YouTube", Icons.Default.PlayCircle, 4),
    QuickAction("Apps", Icons.Default.Apps, 4),
    QuickAction("Instagram", Icons.Default.PhotoCamera, 5),
    QuickAction("Notes", Icons.Default.EditNote, 6),
    QuickAction("Tools", Icons.Default.AutoAwesome, 6),
)

private fun nowText(): String = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"))

@Composable
fun HomeScreen(
    onOpenHistory: () -> Unit,
    onOpenSettings: () -> Unit,
    vm: HomeViewModel = viewModel(),
) {
    val ui by vm.ui.collectAsStateWithLifecycle()
    val time by produceState(initialValue = nowText()) {
        while (true) { value = nowText(); delay(30_000) }
    }
    val snackbar = remember { SnackbarHostState() }
    LaunchedEffect(ui.notice) {
        ui.notice?.let { snackbar.showSnackbar(it); vm.clearNotice() }
    }

    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = { SnackbarHost(snackbar) },
        modifier = Modifier.background(
            Brush.verticalGradient(listOf(JannColors.Black, JannColors.Navy, JannColors.Black))
        ),
    ) { pad ->
        Column(
            Modifier.fillMaxSize().padding(pad).padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(Modifier.fillMaxWidth().padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("JANN AI", fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 4.sp, color = JannColors.Electric)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onOpenHistory) { Icon(Icons.Default.History, "Conversation history") }
                IconButton(onClick = onOpenSettings) { Icon(Icons.Default.Settings, "Settings") }
            }

            Spacer(Modifier.height(16.dp))
            Text("Hey, I'm Jann", fontSize = 28.sp, fontWeight = FontWeight.SemiBold)
            Text(time, fontSize = 16.sp, color = Color.White.copy(alpha = 0.6f))
            Spacer(Modifier.height(8.dp))
            StatusChip(ui.orb)

            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Box(
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { vm.onNotYetAvailable("Voice input", 3) }
                ) { AiOrb(ui.orb) }
            }

            Text("Orb preview (dev only)", fontSize = 11.sp, color = Color.White.copy(alpha = 0.4f))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(vertical = 6.dp)) {
                items(OrbState.entries.toList()) { s ->
                    FilterChip(selected = ui.orb == s, onClick = { vm.previewOrb(s) }, label = { Text(s.name) })
                }
            }

            Spacer(Modifier.height(8.dp))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RoundButton(Icons.Default.Keyboard, "Type", 52) { vm.onNotYetAvailable("Chat input", 2) }
                RoundButton(Icons.Default.Mic, "Microphone", 72, accent = true) { vm.onNotYetAvailable("Voice input", 3) }
                RoundButton(Icons.Default.Stop, "Stop speaking", 52) { vm.onNotYetAvailable("Speech playback", 3) }
            }

            Spacer(Modifier.height(16.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(quickActions) { qa ->
                    GlassCard(Modifier.clickable { vm.onNotYetAvailable(qa.label, qa.phase) }) {
                        Column(Modifier.padding(16.dp).width(84.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(qa.icon, qa.label, tint = JannColors.Electric)
                            Spacer(Modifier.height(6.dp))
                            Text(qa.label, fontSize = 13.sp)
                            Text("Phase ${qa.phase}", fontSize = 10.sp, color = Color.White.copy(alpha = 0.4f))
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun StatusChip(state: OrbState) {
    val shape = RoundedCornerShape(50)
    Row(
        Modifier.clip(shape).background(JannColors.Glass)
            .border(1.dp, JannColors.GlassBorder, shape)
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(state.color))
        Spacer(Modifier.width(8.dp))
        Text(state.label, fontSize = 13.sp)
    }
}

@Composable
private fun RoundButton(icon: ImageVector, desc: String, size: Int, accent: Boolean = false, onClick: () -> Unit) {
    Box(
        Modifier.size(size.dp).clip(CircleShape)
            .background(if (accent) JannColors.Electric.copy(alpha = 0.25f) else JannColors.Glass)
            .border(1.dp, if (accent) JannColors.Electric else JannColors.GlassBorder, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) { Icon(icon, desc, tint = if (accent) JannColors.Electric else Color.White) }
}

@Composable
private fun GlassCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val shape = RoundedCornerShape(20.dp)
    Box(modifier.clip(shape).background(JannColors.Glass).border(1.dp, JannColors.GlassBorder, shape)) { content() }
}
