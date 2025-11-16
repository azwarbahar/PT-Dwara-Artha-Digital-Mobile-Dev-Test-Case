package com.azwar.myticket.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetDefaults.DragHandle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.azwar.myticket.domain.model.TicketStatus
import com.azwar.myticket.domain.model.TicketStatus.DONE
import com.azwar.myticket.domain.model.TicketStatus.IN_PROGRESS
import com.azwar.myticket.domain.model.TicketStatus.OPEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusBottomSheet(
    isVisible: Boolean,
    currentStatus: TicketStatus,
    onDismiss: () -> Unit,
    onStatusSelected: (TicketStatus) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(true)

    if (isVisible) {
        LaunchedEffect(Unit) { sheetState.expand() }

        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            dragHandle = { DragHandle() },
            containerColor = colorScheme.surfaceContainer
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Change Status",
                    style = typography.titleLarge,
                    fontWeight = Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                TicketStatus.entries.forEach { status ->
                    StatusOptionItem(
                        status = status,
                        isSelected = status == currentStatus,
                        onClick = {
                            onStatusSelected(status)
                            onDismiss()
                        }
                    )
                    if (status != TicketStatus.entries.last()) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun StatusOptionItem(
    status: TicketStatus,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val (backgroundColor, textColor) = when (status) {
        OPEN -> colorScheme.errorContainer to colorScheme.onErrorContainer
        IN_PROGRESS -> colorScheme.primaryContainer to colorScheme.onPrimaryContainer
        DONE -> colorScheme.tertiaryContainer to colorScheme.onTertiaryContainer
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = CenterVertically,
        horizontalArrangement = SpaceBetween
    ) {
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(backgroundColor),
                contentAlignment = Center
            ) {
                Text(
                    text = getStatusText(status).take(1),
                    style = typography.titleMedium,
                    fontWeight = Bold,
                    color = textColor
                )
            }
            Column {
                Text(
                    text = getStatusText(status),
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = colorScheme.onSurface
                )
                Text(
                    text = getStatusDescription(status),
                    style = typography.bodySmall,
                    color = colorScheme.onSurfaceVariant
                )
            }
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(colorScheme.primary),
                contentAlignment = Center
            ) {
                Text(
                    text = "✓",
                    style = typography.bodySmall,
                    color = colorScheme.onPrimary,
                    fontWeight = Bold
                )
            }
        }
    }
}

fun getStatusDescription(status: TicketStatus): String {
    return when (status) {
        OPEN -> "Ticket is open and waiting"
        IN_PROGRESS -> "Ticket is being worked on"
        DONE -> "Ticket has been completed"
    }
}

