package com.personal.godou.ui

import androidx.activity.ComponentActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.godou.ui.home.HomeScreen
import com.personal.godou.ui.settings.SettingsScreen
import com.personal.godou.ui.settings.ThemeViewModel
import com.personal.godou.ui.theme.ExpressivePhysics
import com.personal.godou.ui.theme.ThemeSettingsProvider
import com.personal.godou.ui.vocab.GodouScreen

enum class ScreenRoute(
    val routeKey: String,
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    HOME("home", "ホーム", Icons.Outlined.Home, Icons.Filled.Home),
    VOCAB("vocab", "単語帳", Icons.Outlined.Translate, Icons.Filled.Translate),
    SETTINGS("settings", "設定", Icons.Outlined.Settings, Icons.Filled.Settings)
}

@Composable
fun GodouAppRoot(
    viewModel: ThemeViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val themeSettings by viewModel.themeSettings.collectAsStateWithLifecycle()
    var currentRoute by remember { mutableStateOf(ScreenRoute.VOCAB) }
    val haptic = LocalHapticFeedback.current

    ThemeSettingsProvider(themeSettings = themeSettings) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // ── Screen Content with M3 Expressive Shared-Axis Transition ──
                AnimatedContent(
                    targetState = currentRoute,
                    transitionSpec = {
                        val isMovingRight = targetState.ordinal > initialState.ordinal
                        (slideInHorizontally(ExpressivePhysics.fluidBouncy()) { fullWidth ->
                            if (isMovingRight) fullWidth else -fullWidth
                        } + scaleIn(
                            animationSpec = ExpressivePhysics.fluidBouncy(),
                            initialScale = 0.88f
                        ) + fadeIn(ExpressivePhysics.fluidBouncy()))
                            .togetherWith(
                                slideOutHorizontally(ExpressivePhysics.fluidBouncy()) { fullWidth ->
                                    if (isMovingRight) -fullWidth / 3 else fullWidth / 3
                                } + scaleOut(
                                    animationSpec = ExpressivePhysics.fluidBouncy(),
                                    targetScale = 0.88f
                                ) + fadeOut(ExpressivePhysics.fluidBouncy())
                            )
                    },
                    label = "screen_route_transition",
                    modifier = Modifier.fillMaxSize()
                ) { targetScreen ->
                    when (targetScreen) {
                        ScreenRoute.HOME -> HomeScreen()
                        ScreenRoute.VOCAB -> GodouScreen()
                        ScreenRoute.SETTINGS -> SettingsScreen()
                    }
                }

                // ── M3 Expressive Floating Continuous Sliding Dock ──
                val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                val density = LocalDensity.current

                var itemBounds by remember { mutableStateOf(mapOf<Int, Pair<Dp, Dp>>()) }

                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = bottomInset + 16.dp)
                        .height(48.dp)
                        .wrapContentWidth(),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.95f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
                    shadowElevation = 10.dp
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 3.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        // Continuous Sliding Active Pill Indicator
                        val activeBounds = itemBounds[currentRoute.ordinal]
                        if (activeBounds != null) {
                            val indicatorX by animateDpAsState(
                                targetValue = activeBounds.first,
                                animationSpec = ExpressivePhysics.fluidBouncy(),
                                label = "pill_x"
                            )
                            val indicatorWidth by animateDpAsState(
                                targetValue = activeBounds.second,
                                animationSpec = ExpressivePhysics.fluidBouncy(),
                                label = "pill_w"
                            )

                            Box(
                                modifier = Modifier
                                    .offset(x = indicatorX)
                                    .width(indicatorWidth)
                                    .fillMaxHeight()
                                    .background(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = CircleShape
                                    )
                            )
                        }

                        // Navigation Items Row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            ScreenRoute.entries.forEach { route ->
                                val isSelected = currentRoute == route
                                val contentColor by animateColorAsState(
                                    targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                    animationSpec = ExpressivePhysics.fluidBouncy(),
                                    label = "nav_item_content"
                                )
                                val iconScale by animateFloatAsState(
                                    targetValue = if (isSelected) 1.1f else 1.0f,
                                    animationSpec = ExpressivePhysics.fluidBouncy(),
                                    label = "nav_item_icon_scale"
                                )

                                Box(
                                    modifier = Modifier
                                        .onGloballyPositioned { coords ->
                                            val xDp = with(density) { coords.positionInParent().x.toDp() }
                                            val wDp = with(density) { coords.size.width.toDp() }
                                            val bounds = xDp to wDp
                                            if (itemBounds[route.ordinal] != bounds) {
                                                itemBounds = itemBounds + (route.ordinal to bounds)
                                            }
                                        }
                                        .clickable {
                                            if (currentRoute != route) {
                                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                                currentRoute = route
                                            }
                                        }
                                        .padding(horizontal = 12.dp, vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isSelected) route.selectedIcon else route.unselectedIcon,
                                            contentDescription = route.title,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .graphicsLayer {
                                                    scaleX = iconScale
                                                    scaleY = iconScale
                                                },
                                            tint = contentColor
                                        )
                                        if (isSelected) {
                                            Text(
                                                text = route.title,
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = contentColor
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
