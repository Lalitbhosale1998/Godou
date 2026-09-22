package com.personal.godou.ui.settings

import androidx.activity.ComponentActivity
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.godou.data.preferences.*
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import com.personal.godou.ui.theme.ExpressivePhysics
import com.personal.godou.ui.theme.LocalThemeSettings
import com.personal.godou.ui.theme.expressiveBackground
import com.personal.godou.ui.about.ExpressiveAboutSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: ThemeViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
) {
    val themeSettings = LocalThemeSettings.current
    val context = LocalContext.current
    val studyPrefs by viewModel.studyPreferences.collectAsStateWithLifecycle()
    val systemDark = isSystemInDarkTheme()
    val isDark = themeSettings.darkThemePreference.isDark(systemDark)
    val isPrimaryContainer = themeSettings.topAppBarBackground == TopAppBarBackground.PRIMARY_CONTAINER
    val haptic = LocalHapticFeedback.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showResetDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }
    var showAboutSheet by remember { mutableStateOf(false) }
    var showImportSuccessSnackbar by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(showImportSuccessSnackbar) {
        showImportSuccessSnackbar?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            showImportSuccessSnackbar = null
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { _ ->
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
                )
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = topInset + 16.dp,
                    bottom = bottomInset + 110.dp,
                    start = 18.dp,
                    end = 18.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // ── Header Title ──
                item {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                modifier = Modifier.size(48.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Outlined.Settings,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                        modifier = Modifier.size(26.dp)
                                    )
                                }
                            }
                            Column {
                                Text(
                                    text = "設定",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Black,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "学習環境・表示・データの管理",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // ── 1. 外観・パーソナライズ (Appearance & Personalization) ──
                item {
                    SettingsSectionCard(
                        title = "外観・パーソナライズ",
                        icon = Icons.Outlined.Palette,
                        accentColor = MaterialTheme.colorScheme.primary
                    ) {
                        // App Theme Mode
                        Text(
                            text = "アプリテーマ",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        ExpressiveThemeSegmentedControl(
                            selectedPref = themeSettings.darkThemePreference,
                            onPrefSelected = { pref -> viewModel.setDarkThemePreference(pref) }
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                        )

                        // Japanese & App Font Selector
                        Text(
                            text = "日本語・アプリ書体フォント (${themeSettings.appFont.label})",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            AppFont.entries.forEach { font ->
                                val isSelected = themeSettings.appFont == font
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.setAppFont(font)
                                    },
                                    label = { Text(font.label, fontSize = 12.sp) },
                                    leadingIcon = if (isSelected) {
                                        { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                                    } else null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = Color.Transparent,
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    shape = CircleShape
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                        )

                        // Glow Intensity Selector
                        Text(
                            text = "ネオングローエフェクト強弱",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            GlowIntensity.entries.forEach { intensity ->
                                val isSelected = themeSettings.glowIntensity == intensity
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.setGlowIntensity(intensity)
                                    },
                                    label = { Text(intensity.label, fontSize = 12.sp) },
                                    leadingIcon = if (isSelected) {
                                        { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                                    } else null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = Color.Transparent,
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    shape = CircleShape
                                )
                            }
                        }
                    }
                }

                // ── 2. 日本語表示・ルビ (Japanese & Reading Rules) ──
                item {
                    SettingsSectionCard(
                        title = "日本語表示・ルビ",
                        icon = Icons.Outlined.Translate,
                        accentColor = MaterialTheme.colorScheme.secondary
                    ) {
                        // Furigana Display Mode
                        Text(
                            text = "ルビ（ふりがな）表示モード",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FuriganaMode.entries.forEach { mode ->
                                val isSelected = studyPrefs.furiganaMode == mode
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.setFuriganaMode(mode)
                                    },
                                    label = { Text(mode.label, fontSize = 12.sp) },
                                    leadingIcon = if (isSelected) {
                                        { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                                    } else null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = Color.Transparent,
                                        selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                    ),
                                    shape = CircleShape
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                        )

                        // Romaji Helper Switch
                        SettingsSwitchRow(
                            title = "ローマ字ガイド",
                            subtitle = "発音の補助用ローマ字を表示します",
                            checked = studyPrefs.showRomaji,
                            onCheckedChange = { enabled ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.setShowRomaji(enabled)
                            }
                        )
                    }
                }

                // ── 3. 学習・SRS設定 (Study & Spaced Repetition) ──
                item {
                    SettingsSectionCard(
                        title = "学習・SRSアルゴリズム",
                        icon = Icons.Outlined.School,
                        accentColor = MaterialTheme.colorScheme.tertiary
                    ) {
                        // Daily Goal Counter
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "1日の目標学習単語数",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "1日あたりの新規＆復習目標",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            ) {
                                Text(
                                    text = "${studyPrefs.dailyGoalWords} 単語/日",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                )
                            }
                        }
                        Slider(
                            value = studyPrefs.dailyGoalWords.toFloat(),
                            onValueChange = { newValue ->
                                viewModel.setDailyGoalWords(newValue.toInt())
                            },
                            valueRange = 1f..20f,
                            steps = 18,
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.tertiary,
                                activeTrackColor = MaterialTheme.colorScheme.tertiary
                            ),
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                        )

                        // SRS Algorithm Choice
                        Text(
                            text = "間隔反復 (SRS) アルゴリズム",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SrsAlgorithm.entries.forEach { algo ->
                                val isSelected = studyPrefs.srsAlgorithm == algo
                                OutlinedButton(
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.setSrsAlgorithm(algo)
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(16.dp),
                                    border = BorderStroke(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outlineVariant
                                    ),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (isSelected) MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f) else Color.Transparent,
                                        contentColor = if (isSelected) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                ) {
                                    Text(algo.label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                }
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                        )

                        // Deck Order Selection
                        Text(
                            text = "単語帳デッキ並び替え",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            DeckOrder.entries.forEach { order ->
                                val isSelected = studyPrefs.deckOrder == order
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.setDeckOrder(order)
                                    },
                                    label = { Text(order.label, fontSize = 12.sp) },
                                    leadingIcon = if (isSelected) {
                                        { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                                    } else null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = Color.Transparent,
                                        selectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onTertiaryContainer
                                    ),
                                    shape = CircleShape
                                )
                            }
                        }
                    }
                }

                // ── 4. リマインダー・触覚設定 (Notifications & Haptics) ──
                item {
                    SettingsSectionCard(
                        title = "リマインダー・触覚フィードバック",
                        icon = Icons.Outlined.NotificationsActive,
                        accentColor = MaterialTheme.colorScheme.surfaceTint
                    ) {
                        SettingsSwitchRow(
                            title = "毎日の学習リマインダー",
                            subtitle = "設定時刻に学習通知を送信（連続日数保護機能付き）",
                            checked = studyPrefs.dailyReminderEnabled,
                            onCheckedChange = { enabled ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.setDailyReminderEnabled(enabled, context)
                            }
                        )

                        if (studyPrefs.dailyReminderEnabled) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "通知時刻",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.clickable {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        showTimePickerDialog = true
                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Schedule,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp),
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                        Text(
                                            text = studyPrefs.dailyReminderTime,
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
                        )

                        // Touch Synesthesia / Haptic Level
                        Text(
                            text = "Pixel 触覚フィードバック (Haptics)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            TouchSynesthesia.entries.forEach { synesthesia ->
                                val isSelected = themeSettings.touchSynesthesia == synesthesia
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.setTouchSynesthesia(synesthesia)
                                    },
                                    label = { Text(synesthesia.label, fontSize = 12.sp) },
                                    leadingIcon = if (isSelected) {
                                        { Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                                    } else null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = Color.Transparent,
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    shape = CircleShape
                                )
                            }
                        }
                    }
                }

                // ── 5. データ・アプリ情報 (Data, Storage & About) ──
                item {
                    SettingsSectionCard(
                        title = "データ・アプリ情報",
                        icon = Icons.Outlined.Storage,
                        accentColor = MaterialTheme.colorScheme.error
                    ) {
                        // Import / Export Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    showImportSuccessSnackbar = "Anki / CSV デッキインポート機能を準備中..."
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Outlined.FileUpload, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Text("デッキ取り込み", fontSize = 12.sp)
                                }
                            }

                            OutlinedButton(
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    showImportSuccessSnackbar = "学習データのバックアップを出力しました"
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(Icons.Outlined.FileDownload, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Text("データ書き出し", fontSize = 12.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Reset Settings Danger Action
                        FilledTonalButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                showResetDialog = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                contentColor = MaterialTheme.colorScheme.onErrorContainer
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Outlined.RestartAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                                Text("設定のリセット・キャッシュ消去", fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // About Godou App Info Card
                        OutlinedButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                showAboutSheet = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Outlined.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                                Text("アプリについて (About Godou 語道)", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }
    }

    // ── Expressive About Bottom Sheet ──
    if (showAboutSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { showAboutSheet = false },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            containerColor = if (isDark) MaterialTheme.colorScheme.surfaceContainerHigh else MaterialTheme.colorScheme.surfaceContainerHigh,
            dragHandle = {
                Surface(
                    modifier = Modifier.padding(top = 10.dp, bottom = 4.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                ) {
                    Box(modifier = Modifier.size(width = 36.dp, height = 5.dp))
                }
            }
        ) {
            ExpressiveAboutSheet(onDismiss = { showAboutSheet = false })
        }
    }

    // ── Expressive Confirmation Dialog for Reset ──
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(32.dp)
                )
            },
            title = {
                Text(
                    text = "設定をリセットしますか？",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "学習設定やキャッシュが初期状態に戻ります。この操作は取り消せません。",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        viewModel.resetAllSettings()
                        showResetDialog = false
                        scope.launch {
                            snackbarHostState.showSnackbar("設定が初期状態にリセットされました")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = CircleShape
                ) {
                    Text("リセットする", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showResetDialog = false },
                    shape = CircleShape
                ) {
                    Text("キャンセル")
                }
            },
            shape = RoundedCornerShape(28.dp),
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    }

    if (showTimePickerDialog) {
        val initialHour = studyPrefs.dailyReminderTime.split(":").getOrNull(0)?.toIntOrNull() ?: 20
        val initialMinute = studyPrefs.dailyReminderTime.split(":").getOrNull(1)?.toIntOrNull() ?: 0
        val timePickerState = rememberTimePickerState(
            initialHour = initialHour,
            initialMinute = initialMinute,
            is24Hour = true
        )

        AlertDialog(
            onDismissRequest = { showTimePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        val formattedHour = timePickerState.hour.toString().padStart(2, '0')
                        val formattedMinute = timePickerState.minute.toString().padStart(2, '0')
                        val selectedTime = "$formattedHour:$formattedMinute"
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        viewModel.setDailyReminderTime(selectedTime, context)
                        showTimePickerDialog = false
                    },
                    shape = CircleShape
                ) {
                    Text("設定する", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showTimePickerDialog = false },
                    shape = CircleShape
                ) {
                    Text("キャンセル")
                }
            },
            title = {
                Text(
                    text = "通知時刻を選択",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TimePicker(state = timePickerState)
                }
            },
            shape = RoundedCornerShape(28.dp),
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    }
}

@Composable
private fun SettingsSectionCard(
    title: String,
    icon: ImageVector,
    accentColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.78f),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)),
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(bottom = 14.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = accentColor.copy(alpha = 0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            content()
        }
    }
}

@Composable
private fun SettingsSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
private fun ExpressiveThemeSegmentedControl(
    selectedPref: DarkThemePreference,
    onPrefSelected: (DarkThemePreference) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val entries = DarkThemePreference.entries
    val selectedIndex = entries.indexOf(selectedPref)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)
        ) {
            val segmentWidth = maxWidth / entries.size
            val indicatorOffset by animateDpAsState(
                targetValue = segmentWidth * selectedIndex,
                animationSpec = ExpressivePhysics.fluidBouncy(),
                label = "theme_indicator_offset"
            )

            // Continuous Sliding Active Pill
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(segmentWidth)
                    .fillMaxHeight()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )

            // Interactive Row
            Row(modifier = Modifier.fillMaxSize()) {
                entries.forEach { pref ->
                    val isSelected = selectedPref == pref
                    val contentColor by animateColorAsState(
                        targetValue = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = ExpressivePhysics.fluidBouncy(),
                        label = "theme_text_color"
                    )
                    val icon = when (pref) {
                        DarkThemePreference.SYSTEM -> Icons.Outlined.Smartphone
                        DarkThemePreference.LIGHT -> Icons.Outlined.LightMode
                        DarkThemePreference.DARK -> Icons.Outlined.DarkMode
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .clickable {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                onPrefSelected(pref)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = contentColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = pref.label,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = contentColor
                            )
                        }
                    }
                }
            }
        }
    }
}
