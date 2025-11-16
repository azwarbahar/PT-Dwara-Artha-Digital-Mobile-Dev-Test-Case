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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.model.TicketCategory
import com.azwar.myticket.domain.model.TicketCategory.FACILITIES
import com.azwar.myticket.domain.model.TicketCategory.FINANCE
import com.azwar.myticket.domain.model.TicketCategory.GENERAL
import com.azwar.myticket.domain.model.TicketCategory.HR
import com.azwar.myticket.domain.model.TicketCategory.IT_SUPPORT
import com.azwar.myticket.domain.model.TicketStatus
import com.azwar.myticket.domain.model.TicketStatus.DONE
import com.azwar.myticket.domain.model.TicketStatus.IN_PROGRESS
import com.azwar.myticket.domain.model.TicketStatus.OPEN
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TicketItemCard(
    ticket: Ticket,
    onClick: () -> Unit,
    onStatusClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = cardColors(containerColor = colorScheme.surfaceContainer),
        elevation = cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onClick() }
                ) {
                    Text(
                        text = ticket.title,
                        style = typography.titleMedium,
                        fontWeight = Bold,
                        color = colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = ticket.description,
                        style = typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }
                StatusChip(
                    status = ticket.status,
                    onClick = { onStatusClick() }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                CategoryChip(category = ticket.category)
                Text(
                    text = formatDate(ticket.createdAt),
                    style = typography.bodySmall,
                    color = colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun StatusChip(status: TicketStatus, onClick: (() -> Unit)? = null) {
    val (backgroundColor, textColor) = when (status) {
        OPEN -> colorScheme.errorContainer to colorScheme.onErrorContainer
        IN_PROGRESS -> colorScheme.primaryContainer to colorScheme.onPrimaryContainer
        DONE -> colorScheme.tertiaryContainer to colorScheme.onTertiaryContainer
    }

    val baseModifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .background(backgroundColor)
        .padding(horizontal = 12.dp, vertical = 6.dp)

    val modifier = if (onClick != null) {
        baseModifier.clickable { onClick() }
    } else baseModifier

    Row(
        modifier = modifier,
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = getStatusText(status),
            style = typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
        if (onClick != null) {
            Icon(
                imageVector = Default.ArrowDropDown,
                contentDescription = "Change status",
                modifier = Modifier.size(16.dp),
                tint = textColor
            )
        }
    }
}

@Composable
fun CategoryChip(category: TicketCategory) {
    val backgroundColor = colorScheme.secondaryContainer
    val textColor = colorScheme.onSecondaryContainer

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = getCategoryText(category),
            style = typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}

fun getStatusText(status: TicketStatus): String {
    return when (status) {
        OPEN -> "Open"
        IN_PROGRESS -> "In Progress"
        DONE -> "Done"
    }
}

fun getCategoryText(category: TicketCategory): String {
    return when (category) {
        IT_SUPPORT -> "IT Support"
        HR -> "HR"
        FACILITIES -> "Facilities"
        FINANCE -> "Finance"
        GENERAL -> "General"
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

