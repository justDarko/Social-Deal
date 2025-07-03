plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinSymbolProcessing)
}

android {
    namespace = "com.example.socialdealdetails"
    compileSdk = 35

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
}

dependencies {
// Android core bundle ->
    implementation(libs.bundles.androidx.core)

    // Hilt (DI) ->
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    // Compose bundle ->
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    // UI utils bundle ->
    implementation(libs.bundles.media)

    // Unit tests bundle ->
    implementation(libs.bundles.unit.test)

    // Instrumented tests bundle ->
    implementation(libs.bundles.android.test)

    // Timber for logging ->
    implementation(libs.timber)

    // Core module
    implementation(project(":core"))

    // Components module
    implementation(project(":components"))
}