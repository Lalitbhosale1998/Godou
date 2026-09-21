package com.personal.godou.ui

import androidx.activity.ComponentActivity
import androidx.compose.animation.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.godou.ui.components.SuperellipseShape
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
                // ── Screen Content with Spring Animated Transition ──
                AnimatedContent(
                    targetState = currentRoute,
                    transitionSpec = {
                        (slideInHorizontally(ExpressivePhysics.fluidBouncy()) { width ->
                            if (targetState.ordinal > initialState.ordinal) width / 3 else -width / 3
                        } + fadeIn(ExpressivePhysics.fluidBouncy()))
                            .togetherWith(
                                slideOutHorizontally(ExpressivePhysics.fluidBouncy()) { width ->
                                    if (targetState.ordinal > initialState.ordinal) -width / 3 else width / 3
                                } + fadeOut(ExpressivePhysics.fluidBouncy())
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

                // ── M3 Expressive Floating Bottom-Center Navigation Dock ──
                val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = bottomInset + 16.dp)
                        .height(56.dp)
                        .wrapContentWidth(),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.95f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
                    shadowElevation = 10.dp
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        ScreenRoute.entries.forEach { route ->
                            val isSelected = currentRoute == route
                            val pillBg by animateColorAsState(
                                targetValue = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                                animationSpec = ExpressivePhysics.fluidBouncy(),
                                label = "nav_pill_bg"
                            )
                            val pillContentColor by animateColorAsState(
                                targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                animationSpec = ExpressivePhysics.fluidBouncy(),
                                label = "nav_pill_content"
                            )
                            val scale by animateFloatAsState(
                                targetValue = if (isSelected) 1.05f else 0.95f,
                                animationSpec = ExpressivePhysics.fluidBouncy(),
                                label = "nav_pill_scale"
                            )

                            Surface(
                                onClick = {
                                    if (currentRoute != route) {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        currentRoute = route
                                    }
                                },
                                shape = CircleShape,
                                color = pillBg,
                                contentColor = pillContentColor,
                                modifier = Modifier.graphicsLayer {
                                    scaleX = scale
                                    scaleY = scale
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) route.selectedIcon else route.unselectedIcon,
                                        contentDescription = route.title,
                                        modifier = Modifier.size(20.dp),
                                        tint = pillContentColor
                                    )
                                    if (isSelected) {
                                        Text(
                                            text = route.title,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = pillContentColor
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
