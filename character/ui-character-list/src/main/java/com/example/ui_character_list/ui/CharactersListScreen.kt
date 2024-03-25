package com.example.ui_character_list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.character_domain.PhotoModel
import com.example.components.DefaultScreenUI
import com.example.ui_character_list.components.CharacterListItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharactersListScreen(
    state: CharactersListState,
    navigateToDetailScreen: (characterId: Int) -> Unit,
    onTriggerEvent: (event: CharactersListEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val showScrollToTopFAB by remember { derivedStateOf { lazyListState.firstVisibleItemIndex > 3 } }

    DefaultScreenUI(
        isLoading = state.isLoading,
        errorQueue = state.errorQueue,
        onRemoveHeadFromQueue = { onTriggerEvent(CharactersListEvent.RemoveHeadFromQueue) },
        onErrorRetry = { onTriggerEvent(CharactersListEvent.GetAllCharacters) }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFF8F1))) {
            AnimatedVisibility(
                visible = state.characters.isNotEmpty(),
                enter = slideInVertically { 0 },
                exit = slideOutVertically { 0 },
                label = "CharactersListAnimation"
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(vertical = 0.dp)
                        .background(color = Color(0xFFFFF8F1)),
                    // verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = lazyListState
                ) {
                    items(
                        items = state.characters,
                        key = { character: PhotoModel ->
                            character.id ?: 0
                        }
                    ) { character ->
                        CharacterListItem(
                            character = character,
                            modifier = Modifier.animateItemPlacement(),
                            onCharacterSelected = { navigateToDetailScreen(it) }
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = showScrollToTopFAB,
                enter = scaleIn(),
                exit = scaleOut(),
                label = "FABAnimation",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch { lazyListState.animateScrollToItem(0) }
                    },
                    content = {
                        Icon(Icons.Filled.KeyboardArrowUp, "Scroll To Top")
                    }
                )
            }
        }
    }
}