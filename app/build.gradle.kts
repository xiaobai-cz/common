@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "vip.oicp.xiaobaicz.lib"
    compileSdk = 34

    defaultConfig {
        applicationId = "vip.oicp.xiaobaicz.lib"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.bundles.common)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test.android)

//    implementation(project(path = ":common"))
    implementation(libs.common)
}