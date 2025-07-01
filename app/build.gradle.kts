plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinSymbolProcessing)
    alias(libs.plugins.compose.compiler) // Kotlin compiler needed from Kotlin version 2.0
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.socialdeal"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.socialdeal"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false

            buildConfigField(
                type = "String", name = "BASE_URL", value = "\"https://media.socialdeal.nl/demo/\""
            )
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
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
        compose = true
        buildConfig = true // This is needed in order to get the BASE_URL as BuildConfig field
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Android core bundle ->
    implementation(libs.bundles.androidx.core)

    // Compose bundle ->
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    // Compose navigation ->
    implementation(libs.navigation.compose)

    // Hilt (DI) ->
    // ksp = Kotlin Symbol Processing
    // Kotlin versions below 1.9.0 still use kapt (Kotlin annotation processing tool)
    // kapt allows us to use Java annotation processors for processors that don't
    // support Kotlin. This is done by generating Java stubs that the processors can read.
    // This operation is expensive. KSP solves that problem because it analyzes the Kotlin code
    // right away without generating Java stubs.
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    // Timber for logging ->
    implementation(libs.timber)

    // Unit tests bundle ->
    implementation(libs.bundles.unit.test)

    // Instrumented tests bundle ->
    implementation(libs.bundles.android.test)

    // Network bundle ->
    implementation(libs.bundles.network)

    // Core module
    implementation(project(":core"))
}