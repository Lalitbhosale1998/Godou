package com.personal.godou.ui.components

import android.graphics.RuntimeShader
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.toArgb

private const val AGSL_FLUID_MESH_CODE = """
    uniform vec2 uResolution;
    uniform float uTime;
    layout(color) uniform vec4 uColorPrimary;
    layout(color) uniform vec4 uColorSecondary;
    layout(color) uniform vec4 uColorTertiary;

    half4 main(in vec2 fragCoord) {
        vec2 st = fragCoord / uResolution.xy;
        st.x *= uResolution.x / max(uResolution.y, 1.0);

        // Fluid domain warping for organic liquid motion
        vec2 q = vec2(0.0);
        q.x = sin(st.x * 2.5 + uTime * 0.4);
        q.y = cos(st.y * 2.5 + uTime * 0.3);

        vec2 r = vec2(0.0);
        r.x = sin(st.x * 3.0 + 4.0 * q.x + uTime * 0.5);
        r.y = cos(st.y * 3.0 + 4.0 * q.y + uTime * 0.4);

        float f = sin(st.x * 2.0 + r.x + uTime * 0.2) * cos(st.y * 2.0 + r.y + uTime * 0.2);
        f = clamp(f * 0.5 + 0.5, 0.0, 1.0);

        // Dynamic 3-tone Monet aurora color blend
        vec4 mix1 = mix(uColorPrimary, uColorTertiary, smoothstep(0.1, 0.6, f));
        vec4 finalColor = mix(mix1, uColorSecondary, smoothstep(0.4, 0.9, f));

        return half4(finalColor);
    }
"""

@Composable
fun AGSLFluidMeshBackground(
    modifier: Modifier = Modifier,
    alpha: Float = 0.22f,
    content: @Composable () -> Unit = {}
) {
    val primaryColor = MaterialTheme.colorScheme.primaryContainer
    val secondaryColor = MaterialTheme.colorScheme.secondaryContainer
    val tertiaryColor = MaterialTheme.colorScheme.tertiaryContainer

    val infiniteTransition = rememberInfiniteTransition(label = "agsl_mesh_time")
    val animTime by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28318f,
        animationSpec = infiniteRepeatable(tween(12000, easing = LinearEasing), repeatMode = RepeatMode.Restart),
        label = "agsl_time"
    )

    val runtimeShader = remember {
        try {
            RuntimeShader(AGSL_FLUID_MESH_CODE)
        } catch (_: Exception) {
            null
        }
    }

    Box(modifier = modifier) {
        if (runtimeShader != null) {
            runtimeShader.setFloatUniform("uTime", animTime)
            runtimeShader.setColorUniform(
                "uColorPrimary",
                primaryColor.copy(alpha = alpha).toArgb()
            )
            runtimeShader.setColorUniform(
                "uColorSecondary",
                secondaryColor.copy(alpha = alpha * 0.8f).toArgb()
            )
            runtimeShader.setColorUniform(
                "uColorTertiary",
                tertiaryColor.copy(alpha = alpha * 0.9f).toArgb()
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                runtimeShader.setFloatUniform("uResolution", size.width, size.height)
                drawRect(brush = ShaderBrush(runtimeShader))
            }
        } else {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = alpha),
                            tertiaryColor.copy(alpha = alpha * 0.7f),
                            secondaryColor.copy(alpha = alpha * 0.4f),
                            Color.Transparent
                        )
                    )
                )
            }
        }
        content()
    }
}
