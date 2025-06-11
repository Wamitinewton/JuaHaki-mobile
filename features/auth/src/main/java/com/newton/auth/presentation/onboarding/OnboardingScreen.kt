package com.newton.auth.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.newton.auth.presentation.onboarding.components.OnboardingNavigationButtons
import com.newton.auth.presentation.onboarding.components.OnboardingPageContent
import com.newton.commonui.theme.backgroundGradient
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onOnboardingComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pages = OnboardingData.pages
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundGradient())
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    OnboardingPageContent(
                        page = pages[page],
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            OnboardingNavigationButtons(
                currentPage = pagerState.currentPage,
                totalPages = pages.size,
                onSkip = onOnboardingComplete,
                onNext = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                onGetStarted = onOnboardingComplete,
                modifier = Modifier.padding(bottom = 55.dp)
            )
        }
    }
}