package com.personal.godou.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.personal.godou.data.preferences.GlowIntensity
import com.personal.godou.data.preferences.TouchSynesthesia
import com.personal.godou.ui.theme.ExpressivePhysics
import com.personal.godou.ui.theme.LocalGlowIntensity
import com.personal.godou.ui.theme.LocalTouchSynesthesia
import com.personal.godou.ui.theme.SynthClickGenerator
import com.personal.godou.ui.theme.glow

fun Modifier.elasticClick(
    enabled: Boolean = true,
    hapticType: HapticFeedbackType = HapticFeedbackType.LongPress,
    interactionSource: MutableInteractionSource? = null,
    onClick: () -> Unit
): Modifier = composed {
    val haptic = LocalHapticFeedback.current
    val touchSynesthesia = LocalTouchSynesthesia.current
    val actualInteractionSource = interactionSource ?: remember { MutableInteractionSource() }
    val isPressed by actualInteractionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) 0.96f else 1.0f,
        animationSpec = ExpressivePhysics.fluidBouncy(),
        label = "elastic_scale"
    )
    
    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = actualInteractionSource,
            indication = LocalIndication.current,
            enabled = enabled
        ) {
            haptic.performHapticFeedback(hapticType)
            when (touchSynesthesia) {
                TouchSynesthesia.OFF -> {}
                TouchSynesthesia.SUBTLE -> {
                    SynthClickGenerator.playClick(frequency = 800f, durationMs = 30)
                }
                TouchSynesthesia.CASSETTE_CLICK -> {
                    SynthClickGenerator.playClick(frequency = 150f, durationMs = 40)
                }
                TouchSynesthesia.MECHANICAL -> {
                    SynthClickGenerator.playClick(frequency = 1800f, durationMs = 25)
                }
            }
            onClick()
        }
}

@Composable
fun ExpressiveSnackbarHost(hostState: SnackbarHostState) {
    val haptic = LocalHapticFeedback.current

    SnackbarHost(hostState) { data ->
        LaunchedEffect(data) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }

        val isError = data.visuals.message.contains("Error", ignoreCase = true) || 
                      data.visuals.message.contains("Failed", ignoreCase = true)
        val isDelete = data.visuals.message.contains("Delete", ignoreCase = true) || 
                       data.visuals.message.contains("Removed", ignoreCase = true)

        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(
                containerColor = when {
                    isError -> MaterialTheme.colorScheme.errorContainer
                    isDelete -> MaterialTheme.colorScheme.surfaceVariant
                    else -> MaterialTheme.colorScheme.primaryContainer
                },
                contentColor = when {
                    isError -> MaterialTheme.colorScheme.onErrorContainer
                    isDelete -> MaterialTheme.colorScheme.onSurfaceVariant
                    else -> MaterialTheme.colorScheme.onPrimaryContainer
                }
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = when {
                        isError -> Icons.Outlined.ErrorOutline
                        isDelete -> Icons.Outlined.Delete
                        else -> Icons.Outlined.CheckCircle
                    },
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = when {
                        isError -> MaterialTheme.colorScheme.error
                        isDelete -> MaterialTheme.colorScheme.onSurfaceVariant
                        else -> MaterialTheme.colorScheme.primary
                    }
                )
                Text(
                    text = data.visuals.message,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                data.visuals.actionLabel?.let { action ->
                    TextButton(onClick = { data.performAction() }) {
                        Text(
                            text = action,
                            fontWeight = FontWeight.Black,
                            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExpressiveOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    shape: Shape = MaterialTheme.shapes.large,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh
) {
    val haptic = LocalHapticFeedback.current
    var isFocused by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isFocused) 1.02f else 1f,
        animationSpec = ExpressivePhysics.fluidBouncy(),
        label = "field_scale"
    )

    Surface(
        shape = shape,
        modifier = modifier.graphicsLayer {
            scaleX = scale
            scaleY = scale
        },
        color = Color.Transparent
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            shape = shape,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { 
                    if (it.isFocused != isFocused) {
                        isFocused = it.isFocused 
                        if (it.isFocused) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                    }
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = containerColor,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Transparent
            )
        )
    }
}

@Composable
fun ExpressiveChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    selectedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    unselectedColor: Color = Color.Unspecified,
    unselectedTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    leadingIcon: ImageVector? = null
) {
    val resolvedUnselectedColor = if (unselectedColor == Color.Transparent || unselectedColor == Color.Unspecified) {
        MaterialTheme.colorScheme.surfaceContainerHigh
    } else {
        unselectedColor
    }
    
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) selectedColor else resolvedUnselectedColor,
        animationSpec = ExpressivePhysics.fluidBouncy(),
        label = "chip_bg_color"
    )
    val txtColor by animateColorAsState(
        targetValue = if (isSelected) selectedTextColor else unselectedTextColor,
        animationSpec = ExpressivePhysics.fluidBouncy(),
        label = "chip_txt_color"
    )
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.04f else 1.0f,
        animationSpec = ExpressivePhysics.fluidBouncy(),
        label = "chip_scale"
    )

    val shape = CircleShape

    Surface(
        modifier = modifier
            .height(38.dp)
            .elasticClick(
                hapticType = HapticFeedbackType.TextHandleMove,
                onClick = onClick
            )
            .graphicsLayer(scaleX = scale, scaleY = scale),
        shape = shape,
        color = bgColor,
        contentColor = txtColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp).padding(end = 6.dp),
                    tint = txtColor
                )
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun ExpressiveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        enabled = enabled,
        shape = CircleShape,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        content = content
    )
}

@Composable
fun ContainedLoadingIndicator(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    indicatorColor: Color = MaterialTheme.colorScheme.primary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "contained_loading")

    val morphProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "morph_progress"
    )

    val polygonOrganicA = remember {
        RoundedPolygon(numVertices = 4, rounding = CornerRounding(radius = 0.65f))
    }
    val polygonOrganicB = remember {
        RoundedPolygon(numVertices = 3, rounding = CornerRounding(radius = 0.75f))
    }
    val morph = remember { Morph(polygonOrganicA, polygonOrganicB) }

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(containerColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(32.dp)) {
            val matrix = android.graphics.Matrix()
            matrix.setScale(size.width * 0.42f, size.height * 0.42f)
            matrix.postTranslate(size.width / 2f, size.height / 2f)

            val path = morph.toPath(progress = morphProgress).apply {
                transform(matrix)
            }

            drawPath(
                path = path.asComposePath(),
                color = indicatorColor
            )
        }
    }
}

@Composable
fun ExpressiveElasticToggle(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (scale: Float, rotation: Float) -> Unit
) {
    val haptic = LocalHapticFeedback.current

    val scale by animateFloatAsState(
        targetValue = if (checked) 1.25f else 1.0f,
        animationSpec = ExpressivePhysics.fluidBouncy(),
        label = "elastic_toggle_scale"
    )
    val rotation by animateFloatAsState(
        targetValue = if (checked) 15f else 0f,
        animationSpec = ExpressivePhysics.fluidBouncy(),
        label = "elastic_toggle_rotation"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                rotationZ = rotation
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onCheckedChange()
            },
        contentAlignment = Alignment.Center
    ) {
        content(scale, rotation)
    }
}

@Composable
fun rememberExpressiveCardShape(
    isPressed: Boolean,
    defaultCorner: Dp = 22.dp,
    pressedCorner: Dp = 34.dp
): Shape {
    val topStart by animateDpAsState(
        targetValue = if (isPressed) pressedCorner else defaultCorner,
        animationSpec = ExpressivePhysics.fluidSnappy(),
        label = "corner_top_start"
    )
    val bottomEnd by animateDpAsState(
        targetValue = if (isPressed) (pressedCorner / 2) else defaultCorner,
        animationSpec = ExpressivePhysics.fluidSnappy(),
        label = "corner_bottom_end"
    )
    return RoundedCornerShape(
        topStart = topStart,
        topEnd = defaultCorner,
        bottomStart = defaultCorner,
        bottomEnd = bottomEnd
    )
}

@Composable
fun ExpressiveSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val haptic = LocalHapticFeedback.current
    Switch(
        checked = checked,
        onCheckedChange = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onCheckedChange?.invoke(it)
        },
        modifier = modifier,
        enabled = enabled,
        thumbContent = {
            if (checked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.8f),
                            CircleShape
                        )
                )
            }
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
            checkedTrackColor = MaterialTheme.colorScheme.primary,
            checkedIconColor = MaterialTheme.colorScheme.primary,
            uncheckedThumbColor = MaterialTheme.colorScheme.outline,
            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceContainerHighest
        )
    )
}

@Composable
fun ExpressiveScrollableFab(
    extended: Boolean,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    val haptic = LocalHapticFeedback.current
    val fabWidth by animateDpAsState(
        targetValue = if (extended) 140.dp else 56.dp,
        animationSpec = ExpressivePhysics.fluidBouncy(),
        label = "fab_width"
    )

    val shape = CircleShape

    Surface(
        modifier = modifier
            .height(56.dp)
            .width(fabWidth)
            .clip(shape)
            .clickable {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            },
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.size(24.dp)
            )
            AnimatedVisibility(
                visible = extended,
                enter = fadeIn(ExpressivePhysics.fluidBouncy()) + expandHorizontally(ExpressivePhysics.fluidBouncy()),
                exit = fadeOut(ExpressivePhysics.fluidBouncy()) + shrinkHorizontally(ExpressivePhysics.fluidBouncy())
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

object ExpressiveHaptics {
    fun tabSwap(haptic: HapticFeedback) {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }
    fun cardExpand(haptic: HapticFeedback) {
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
    }
    fun snap(haptic: HapticFeedback) {
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }
}

@Composable
fun <T> ExpressiveSegmentedControl(
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    labelProvider: (T) -> String,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colorScheme.primaryContainer,
    onActiveColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    shape: Shape = CircleShape,
    height: Dp = 48.dp
) {
    val haptic = LocalHapticFeedback.current
    val selectedIndex = options.indexOf(selectedOption).coerceAtLeast(0)

    Surface(
        modifier = modifier.height(height),
        shape = shape,
        color = containerColor,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            val totalWidth = maxWidth
            val count = options.size.coerceAtLeast(1)
            val itemWidth = totalWidth / count

            val animatedOffset by animateDpAsState(
                targetValue = itemWidth * selectedIndex,
                animationSpec = ExpressivePhysics.fluidBouncy(),
                label = "segmented_pill_offset"
            )

            Box(
                modifier = Modifier
                    .offset(x = animatedOffset)
                    .width(itemWidth)
                    .fillMaxHeight()
                    .clip(shape)
                    .background(activeColor)
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                options.forEachIndexed { index, option ->
                    val isSelected = index == selectedIndex

                    val textColor by animateColorAsState(
                        targetValue = if (isSelected) onActiveColor else MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = ExpressivePhysics.fluidBouncy(),
                        label = "segmented_text_color"
                    )

                    val itemScale by animateFloatAsState(
                        targetValue = if (isSelected) 1.04f else 1.0f,
                        animationSpec = ExpressivePhysics.fluidBouncy(),
                        label = "segmented_item_scale"
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .graphicsLayer {
                                scaleX = itemScale
                                scaleY = itemScale
                            }
                            .clip(shape)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (!isSelected) {
                                    ExpressiveHaptics.cardExpand(haptic)
                                    onOptionSelected(option)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = labelProvider(option),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = textColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
