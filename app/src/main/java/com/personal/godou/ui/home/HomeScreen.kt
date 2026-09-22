package com.personal.godou.ui.home

import androidx.activity.ComponentActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.personal.godou.data.preferences.TopAppBarBackground
import com.personal.godou.ui.settings.ThemeViewModel
import com.personal.godou.ui.theme.ExpressivePhysics
import com.personal.godou.ui.theme.LocalStudyPreferences
import com.personal.godou.ui.theme.LocalThemeSettings
import com.personal.godou.ui.theme.expressiveBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: ThemeViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val themeSettings = LocalThemeSettings.current
    val studyPrefs = LocalStudyPreferences.current
    val systemDark = isSystemInDarkTheme()
    val isDark = themeSettings.darkThemePreference.isDark(systemDark)
    val isPrimaryContainer = themeSettings.topAppBarBackground == TopAppBarBackground.PRIMARY_CONTAINER

    val bottomInset = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val topInset = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .expressiveBackground(
                isDark = isDark,
                isPrimaryContainer = isPrimaryContainer,
                primaryColor = MaterialTheme.colorScheme.primary,
                containerColor = Color.Unspecified,
                pattern = themeSettings.backdropPattern
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    top = topInset + 16.dp,
                    bottom = bottomInset + 110.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                modifier = Modifier.size(72.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Text(
                text = "ホーム",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onSurface,
                letterSpacing = 2.sp
            )

            Text(
                text = "JLPT N1 単語学習ダッシュボード",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            // ── 🔥 Expressive Daily Study Streak & 7-Day Heatmap Card ──
            ExpressiveStreakCard(
                streakDays = studyPrefs.streakDays,
                weeklyMask = studyPrefs.weeklyActivityMask,
                onToggleDay = { dayIndex -> viewModel.toggleWeeklyDay(dayIndex) }
            )

            // ── Active Settings Summary Cards ──
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.78f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Analytics,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "現在の学習設定状況",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("1日目標", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("${studyPrefs.dailyGoalWords} 単語/日", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                        Column {
                            Text("ふりがな表示", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(studyPrefs.furiganaMode.label, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.secondary)
                        }
                        Column {
                            Text("ローマ字ガイド", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(if (studyPrefs.showRomaji) "有効" else "無効", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("SRSアルゴリズム", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(studyPrefs.srsAlgorithm.label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        }
                        Column {
                            Text("並び替え順", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(studyPrefs.deckOrder.label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExpressiveStreakCard(
    streakDays: Int,
    weeklyMask: Int,
    onToggleDay: (Int) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val coroutineScope = rememberCoroutineScope()
    val daysOfWeek = listOf("月", "火", "水", "木", "金", "土", "日")
    val todayIndex = remember { (java.time.LocalDate.now().dayOfWeek.value - 1).coerceIn(0, 6) }

    // M3 Expressive Continuous Breathing & Sway Physics
    val infiniteTransition = rememberInfiniteTransition(label = "expressive_flame_idle")
    val idleFlamePulse by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idle_flame_pulse"
    )
    val idleFlameRotation by infiniteTransition.animateFloat(
        initialValue = -3.5f,
        targetValue = 3.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idle_flame_rotation"
    )

    // Option 3: Synchronized Flame Badge Scale Target
    var flameScaleTarget by remember { mutableFloatStateOf(1.0f) }
    val animatedFlameScale by animateFloatAsState(
        targetValue = flameScaleTarget,
        animationSpec = ExpressivePhysics.fluidBouncy(),
        label = "flame_badge_pulse"
    )

    // Option 1: Per-day Pill Spring Pulse Target map
    var dayPulseIndex by remember { mutableIntStateOf(-1) }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.78f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Header Row: 🔥 Official M3 Vector Flame Hero Badge & Count (Option 2)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // M3 Expressive Fire Badge Container with Dynamic Monet Radial Glow & Breathing Physics
                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.85f),
                        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .size(60.dp)
                            .graphicsLayer {
                                scaleX = animatedFlameScale * idleFlamePulse
                                scaleY = animatedFlameScale * idleFlamePulse
                                rotationZ = idleFlameRotation
                            }
                            .clickable {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                coroutineScope.launch {
                                    flameScaleTarget = 1.30f
                                    delay(180)
                                    flameScaleTarget = 1.0f
                                }
                            }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Filled.LocalFireDepartment,
                                contentDescription = "Streak Fire",
                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "連続学習ストリーク",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "素晴らしい集中力です！",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // M3 Expressive Streak Count Pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.90f),
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
                    modifier = Modifier.graphicsLayer {
                        scaleX = animatedFlameScale
                        scaleY = animatedFlameScale
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocalFireDepartment,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "${streakDays}日連続",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            // 7-Day Heatmap Capsules Row with Bouncy Spring Pulse & Scale Bounce
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                daysOfWeek.forEachIndexed { index, dayName ->
                    val isActive = (weeklyMask and (1 shl index)) != 0
                    val isToday = index == todayIndex
                    val isPulsing = dayPulseIndex == index

                    val containerColor by animateColorAsState(
                        targetValue = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerHighest,
                        animationSpec = ExpressivePhysics.fluidBouncy(),
                        label = "heatmap_container_color"
                    )

                    val contentColor by animateColorAsState(
                        targetValue = if (isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = ExpressivePhysics.fluidBouncy(),
                        label = "heatmap_content_color"
                    )

                    val baseScale = if (isToday) 1.08f else 1.0f
                    val pulseMultiplier = if (isPulsing) 1.25f else 1.0f

                    val scale by animateFloatAsState(
                        targetValue = baseScale * pulseMultiplier,
                        animationSpec = ExpressivePhysics.fluidBouncy(),
                        label = "heatmap_scale"
                    )

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 3.dp)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
                            .clip(RoundedCornerShape(20.dp))
                            .clickable {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                coroutineScope.launch {
                                    dayPulseIndex = index
                                    flameScaleTarget = 1.30f
                                    delay(160)
                                    dayPulseIndex = -1
                                    flameScaleTarget = 1.0f
                                }
                                onToggleDay(index)
                            },
                        shape = RoundedCornerShape(20.dp),
                        color = containerColor,
                        border = if (isToday) BorderStroke(2.5.dp, MaterialTheme.colorScheme.tertiary) else null
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = dayName,
                                fontSize = 18.sp,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = if (isToday) FontWeight.Black else FontWeight.ExtraBold,
                                color = contentColor
                            )

                            AnimatedContent(
                                targetState = isActive,
                                transitionSpec = {
                                    (scaleIn(ExpressivePhysics.fluidBouncy()) + fadeIn())
                                        .togetherWith(scaleOut(ExpressivePhysics.fluidBouncy()) + fadeOut())
                                },
                                label = "day_check_anim"
                            ) { active ->
                                if (active) {
                                    Icon(
                                        imageVector = Icons.Outlined.Check,
                                        contentDescription = "Completed",
                                        tint = contentColor,
                                        modifier = Modifier.size(22.dp)
                                    )
                                } else {
                                    Surface(
                                        shape = CircleShape,
                                        color = contentColor.copy(alpha = 0.35f),
                                        modifier = Modifier.size(10.dp)
                                    ) {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
