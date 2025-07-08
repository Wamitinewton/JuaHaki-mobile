package com.newton.auth.presentation.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.newton.commonui.theme.backgroundGradient
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashComplete: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel,
) {
    var showFlag by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }
    var showSubtleGlow by remember { mutableStateOf(false) }
    var animationComplete by remember { mutableStateOf(false) }

    val flagScale = remember { Animatable(0f) }
    val flagAlpha = remember { Animatable(0f) }
    val textScale = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }

    val hasTokens by viewModel.hasTokens.collectAsStateWithLifecycle()

    val infiniteTransition = rememberInfiniteTransition(label = "flag_pulse")
    val flagPulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(2000, easing = EaseInOutCubic),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "flag_pulse",
    )

    LaunchedEffect(animationComplete) {
        if (animationComplete) {
            viewModel.checkTokens()
        }
    }

    LaunchedEffect(hasTokens) {
        if (animationComplete && hasTokens != null) {
            delay(300)
            onSplashComplete(hasTokens == true)
        }
    }

    LaunchedEffect(Unit) {
        delay(300)
        showFlag = true

        flagAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing),
        )
        flagScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(1000, easing = FastOutSlowInEasing),
        )

        delay(400)
        showText = true
        showSubtleGlow = true

        textAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(600, easing = FastOutSlowInEasing),
        )
        textScale.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = FastOutSlowInEasing),
        )

        delay(1500)
        animationComplete = true
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(backgroundGradient()),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(160.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = showFlag,
                        enter =
                            fadeIn(
                                animationSpec = tween(800, easing = FastOutSlowInEasing),
                            ) +
                                    scaleIn(
                                        animationSpec = tween(1000, easing = FastOutSlowInEasing),
                                        initialScale = 0.3f,
                                    ),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if (showSubtleGlow) {
                                Box(
                                    modifier =
                                        Modifier
                                            .size(140.dp)
                                            .scale(flagPulse * 1.1f)
                                            .alpha(0.1f)
                                            .background(
                                                color = MaterialTheme.colorScheme.primary,
                                                shape = CircleShape,
                                            ),
                                )
                            }

                            AsyncImage(
                                model =
                                    ImageRequest
                                        .Builder(LocalContext.current)
                                        .data(com.newton.commonui.R.drawable.kenyaflag)
                                        .crossfade(true)
                                        .build(),
                                contentDescription = "Kenyan Flag",
                                modifier =
                                    Modifier
                                        .size(120.dp)
                                        .scale(flagScale.value * flagPulse)
                                        .alpha(flagAlpha.value)
                                        .clip(CircleShape),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier.height(120.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = showText,
                        enter =
                            fadeIn(
                                animationSpec = tween(600, easing = FastOutSlowInEasing),
                            ) +
                                    scaleIn(
                                        animationSpec = tween(800, easing = FastOutSlowInEasing),
                                        initialScale = 0.5f,
                                    ),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Jua Haki Yako",
                                style =
                                    MaterialTheme.typography.headlineLarge.copy(
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.2.sp,
                                    ),
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier =
                                    Modifier
                                        .scale(textScale.value)
                                        .alpha(textAlpha.value),
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Know Your Rights",
                                style =
                                    MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        letterSpacing = 0.8.sp,
                                    ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                                modifier =
                                    Modifier
                                        .alpha(textAlpha.value * 0.8f),
                            )
                        }
                    }
                }
            }
        }
    }
}
