package com.jann.ai.feature.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HomeUiState(
    val orb: OrbState = OrbState.Idle,
    val notice: String? = null,
)

class HomeViewModel : ViewModel() {
    private val _ui = MutableStateFlow(HomeUiState())
    val ui: StateFlow<HomeUiState> = _ui.asStateFlow()

    /** Phase 1: features aren't built yet, so say so honestly instead of faking them. */
    fun onNotYetAvailable(feature: String, phase: Int) =
        _ui.update { it.copy(notice = "$feature isn't built yet. It arrives in Phase $phase.") }

    /** Dev-only helper to verify the orb animations for every state. */
    fun previewOrb(state: OrbState) = _ui.update { it.copy(orb = state) }

    fun clearNotice() = _ui.update { it.copy(notice = null) }
}
