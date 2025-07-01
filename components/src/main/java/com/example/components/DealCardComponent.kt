package com.example.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun DealCardComponent(
    modifier: Modifier = Modifier,
    imageCover: @Composable () -> Unit,
    title: String,
    company: String,
    city: String,
    sold: String,
    oldPrice: String,
    newPrice: String,
    currencySign: String = "$",
    isFavorite: Boolean,
    showDescription: Boolean = false,
    description: String = "",
    onDealCardClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    val context = LocalContext.current

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable { onDealCardClick() }) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(230.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            imageCover()

            IconButton(
                onClick = onFavoriteClick,
                modifier = modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .size(36.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else Color.White
                )
            }
        }

        Spacer(modifier = modifier.height(8.dp))
        Text(
            text = title, style = MaterialTheme.typography.titleMedium, color = Color.Black
        )
        Spacer(modifier = modifier.height(12.dp))
        Text(
            text = company, style = MaterialTheme.typography.bodyMedium, color = Color.Gray
        )
        Text(
            text = city, style = MaterialTheme.typography.bodyMedium, color = Color.Gray
        )
        Spacer(modifier = modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sold,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f)) // pushes next composables to the right

            val oldPriceFormatted =
                context.getString(R.string.formatted_price, currencySign, oldPrice)
            val newPriceFormatted =
                context.getString(R.string.formatted_price, currencySign, newPrice)

            Text(
                text = oldPriceFormatted, style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray, textDecoration = TextDecoration.LineThrough
                )
            )

            Text(
                text = newPriceFormatted, style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold, color = Color(
                        0xFF98D5A4
                    )
                )
            )

        }

        if (showDescription) {
            Spacer(modifier = modifier.height(12.dp))
            Text(
                text = description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray
            )
        }
    }
}
