package com.newton.auth.presentation.onboarding

data class OnboardingPage(
    val imageRes: Int,
    val titleRes: Int,
    val descriptionRes: Int
)

object OnboardingData {
    val pages = listOf(
        OnboardingPage(
            imageRes = com.newton.commonui.R.drawable.kenyaflag,
            titleRes = com.newton.auth.R.string.onboarding_title_1,
            descriptionRes = com.newton.auth.R.string.onboarding_desc_1
        ),
        OnboardingPage(
            imageRes = com.newton.commonui.R.drawable.kenyaflag,
            titleRes = com.newton.auth.R.string.onboarding_title_2,
            descriptionRes = com.newton.auth.R.string.onboarding_desc_2
        ),
        OnboardingPage(
            imageRes = com.newton.commonui.R.drawable.kenyaflag,
            titleRes = com.newton.auth.R.string.onboarding_title_3,
            descriptionRes = com.newton.auth.R.string.onboarding_desc_3
        )
    )
}
