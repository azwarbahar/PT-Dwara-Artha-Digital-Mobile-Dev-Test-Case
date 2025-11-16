package com.azwar.myticket.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomSheetDefaults.DragHandle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.azwar.myticket.domain.model.TicketCategory
import com.azwar.myticket.domain.model.TicketCategory.FACILITIES
import com.azwar.myticket.domain.model.TicketCategory.FINANCE
import com.azwar.myticket.domain.model.TicketCategory.GENERAL
import com.azwar.myticket.domain.model.TicketCategory.HR
import com.azwar.myticket.domain.model.TicketCategory.IT_SUPPORT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    isVisible: Boolean,
    currentCategory: TicketCategory,
    onDismiss: () -> Unit,
    onCategorySelected: (TicketCategory) -> Unit
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
                    text = "Select Category",
                    style = typography.titleLarge,
                    fontWeight = Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                TicketCategory.entries.forEach { category ->
                    CategoryOptionItem(
                        category = category,
                        isSelected = category == currentCategory,
                        onClick = {
                            onCategorySelected(category)
                            onDismiss()
                        }
                    )
                    if (category != TicketCategory.entries.last()) {
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CategoryOptionItem(
    category: TicketCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val icon = getCategoryIcon(category)
    val backgroundColor = colorScheme.secondaryContainer
    val textColor = colorScheme.onSecondaryContainer

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
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(backgroundColor),
                contentAlignment = Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column {
                Text(
                    text = getCategoryText(category),
                    style = typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = colorScheme.onSurface
                )
                Text(
                    text = getCategoryDescription(category),
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

fun getCategoryIcon(category: TicketCategory): ImageVector {
    return when (category) {
        IT_SUPPORT -> Default.Computer
        HR -> Default.BusinessCenter
        FACILITIES -> Default.Build
        FINANCE -> Default.AccountBalance
        GENERAL -> Default.Info
    }
}

fun getCategoryDescription(category: TicketCategory): String {
    return when (category) {
        IT_SUPPORT -> "Technical support and IT issues"
        HR -> "Human resources and personnel"
        FACILITIES -> "Building and facility maintenance"
        FINANCE -> "Financial and accounting matters"
        GENERAL -> "General inquiries and requests"
    }
}

