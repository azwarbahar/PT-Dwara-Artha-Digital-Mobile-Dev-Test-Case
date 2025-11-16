package com.azwar.myticket.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.azwar.myticket.domain.model.TicketCategory

@Composable
fun CategoryField(
    selectedCategory: TicketCategory,
    onCategoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val text = remember(selectedCategory) { getCategoryText(selectedCategory) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCategoryClick() }
    ) {
        Text(
            text = "Category *",
            style = typography.bodySmall,
            color = colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorScheme.outline,
                    shape = shapes.small
                )
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = CenterVertically
        ) {
            Text(
                text = text.ifBlank { "Select category" },
                style = typography.bodyMedium,
                color = if (text.isBlank()) colorScheme.onSurfaceVariant else colorScheme.onSurface
            )

            Spacer(Modifier.weight(1f))

            Icon(
                imageVector = Default.KeyboardArrowDown,
                contentDescription = null
            )
        }
    }
}