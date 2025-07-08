package com.newton.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavBackStackEntry
import com.newton.core.enums.TransitionType

/**
 * Navigation transition helper that works reliably across module boundaries
 */
class NavigationTransitions {
    /**
     * Gets enter transition based on transition type
     */
    fun getEnterTransition(
        type: TransitionType,
        durationMs: Int,
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        {
            when (type) {
                TransitionType.SLIDE_HORIZONTAL ->
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseIn),
                    )

                TransitionType.SLIDE_VERTICAL ->
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseIn),
                    )

                TransitionType.FADE ->
                    fadeIn(
                        animationSpec = tween(durationMillis = durationMs, easing = LinearEasing),
                    )

                TransitionType.ZOOM ->
                    scaleIn(
                        initialScale = 0.85f,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseIn),
                    ) +
                            fadeIn(
                                animationSpec = tween(
                                    durationMillis = durationMs,
                                    easing = LinearEasing
                                ),
                            )

                TransitionType.NONE -> EnterTransition.None
            }
        }

    /**
     * Gets exit transition based on transition type
     */
    fun getExitTransition(
        type: TransitionType,
        durationMs: Int,
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        {
            when (type) {
                TransitionType.SLIDE_HORIZONTAL ->
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseOut),
                    )

                TransitionType.SLIDE_VERTICAL ->
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseOut),
                    )

                TransitionType.FADE ->
                    fadeOut(
                        animationSpec = tween(durationMillis = durationMs, easing = LinearEasing),
                    )

                TransitionType.ZOOM ->
                    scaleOut(
                        targetScale = 1.1f,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseOut),
                    ) +
                            fadeOut(
                                animationSpec = tween(
                                    durationMillis = durationMs,
                                    easing = LinearEasing
                                ),
                            )

                TransitionType.NONE -> ExitTransition.None
            }
        }

    /**
     * Gets pop enter transition based on transition type
     */
    fun getPopEnterTransition(
        type: TransitionType,
        durationMs: Int,
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        {
            when (type) {
                TransitionType.SLIDE_HORIZONTAL ->
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseIn),
                    )

                TransitionType.SLIDE_VERTICAL ->
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseIn),
                    )

                TransitionType.FADE ->
                    fadeIn(
                        animationSpec = tween(durationMillis = durationMs, easing = LinearEasing),
                    )

                TransitionType.ZOOM ->
                    scaleIn(
                        initialScale = 1.1f,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseIn),
                    ) +
                            fadeIn(
                                animationSpec = tween(
                                    durationMillis = durationMs,
                                    easing = LinearEasing
                                ),
                            )

                TransitionType.NONE -> EnterTransition.None
            }
        }

    /**
     * Gets pop exit transition based on transition type
     */
    fun getPopExitTransition(
        type: TransitionType,
        durationMs: Int,
    ): (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        {
            when (type) {
                TransitionType.SLIDE_HORIZONTAL ->
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseOut),
                    )

                TransitionType.SLIDE_VERTICAL ->
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseOut),
                    )

                TransitionType.FADE ->
                    fadeOut(
                        animationSpec = tween(durationMillis = durationMs, easing = LinearEasing),
                    )

                TransitionType.ZOOM ->
                    scaleOut(
                        targetScale = 0.85f,
                        animationSpec = tween(durationMillis = durationMs, easing = EaseOut),
                    ) +
                            fadeOut(
                                animationSpec = tween(
                                    durationMillis = durationMs,
                                    easing = LinearEasing
                                ),
                            )

                TransitionType.NONE -> ExitTransition.None
            }
        }
}
