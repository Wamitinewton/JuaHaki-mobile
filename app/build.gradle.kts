plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.newton.juahaki"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.newton.juahaki"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    addComposeDependencies()
    addTestDependencies()
    addHiltDependencies()
    addRetrofitDependencies()
    addWorkManagerDependencies()

    implementation(project(Modules.commonui))
    implementation(project(Modules.auth))
    implementation(project(Modules.home))
    implementation(project(Modules.quiz))
    implementation(project(Modules.navigation))
    implementation(project(Modules.core))
    implementation(project(Modules.database))
    implementation(project(Modules.domain))
}
