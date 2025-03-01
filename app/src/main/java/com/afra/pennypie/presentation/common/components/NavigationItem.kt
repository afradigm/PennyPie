package com.afra.pennypie.presentation.common.components

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val route: String,
    val icon: ImageVector,
    val labelResId: Int
) 