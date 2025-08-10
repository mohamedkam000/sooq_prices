package com.sooq.price.ui.settings

import androidx.compose.material3.Icon
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.Person
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ProfileIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Icon(
        imageVector = Icons.Filled.Person,
        contentDescription = "Profile",
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
        tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
}