@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlin-kapt")
    `maven-publish`
}

android {
    namespace = "vip.oicp.xiaobaicz.lib.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.bundles.common)
    implementation(libs.auto.service.annotations)
    kapt(libs.auto.service)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test.android)
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "vip.oicp.xiaobaicz"
            artifactId = "common"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}